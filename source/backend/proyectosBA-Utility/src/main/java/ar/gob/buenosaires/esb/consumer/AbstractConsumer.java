/*
 * Producer.java
 * 
 * Created on Jul 13, 2015
 * 
 */
package ar.gob.buenosaires.esb.consumer;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.pool.PooledConnectionFactory;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.xml.sax.SAXParseException;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.handler.ESBProcess;
import ar.gob.buenosaires.esb.util.JMSUtil;

public abstract class AbstractConsumer implements MessageListener {

	@Autowired
	private PooledConnectionFactory pooledConnectionFactory;

	@Autowired
	private JmsTemplate jmsTemplate;

	@Autowired
	private Jaxb2Marshaller marshaller;

	@Value("${esb.producer.destination}")
	private String JmsDestination;

	private List<ESBProcess> handlersChain;

	/**
	 * Procesa el mensaje por todos los handlers.
	 * 
	 * @param jmsMsg
	 * @return
	 * @throws JMSException
	 * @throws SAXParseException
	 * @throws ESBException
	 */
	protected ESBEvent processMSG(Message jmsMsg) throws JMSException, SAXParseException, ESBException {
		String s = ((TextMessage) jmsMsg).getText();
		//para que el mensaje pase a dequeued.
		jmsMsg.acknowledge();
		final Object message = (StringUtils.isBlank(s)) ? "" : JMSUtil.unmarshal(s, marshaller);
		ESBEvent event = JMSUtil.constructEvent(jmsMsg, s, message);
		getLogger().debug(" mensaje recibido " + "{} " + "(Thread: {}): {} ",
				new Object[] { event.toString(), Thread.currentThread().getName(), s });
		passDownHandlers(event);
		return event;
	}

	/**
	 * Pass an event down the process chain.
	 * <p>
	 * If any process returns "false" the chain will be broken. If any process
	 * throws an exception, processing will continue down the exception process
	 * chain.
	 *
	 */
	private void passDownHandlers(ESBEvent event) throws ESBException {
		try {
			for (ESBProcess proc : handlersChain) {
				// Processes will return false to interrupt chain
				if (!proc.onMessage(event)) {
					break;
				}
			}
		} catch (Exception e) {
			getLogger().error(String.format("Unexpected exception in process chain : "), e);
			event.getExceptions().add(new ESBException(e));
		}
	}

	protected void responseBack(Message jmsMsg, ESBEvent event) throws JMSException {
		String outputText = JMSUtil.marshal(event.getObj(), marshaller);

		// Note: jms messageId/correlationId switch below - requesting client is
		// assumed
		// by JMS convention to be waiting with a correlationId selector of this
		// jms message
		// messageId.
		final EsbMessageCreator mc = new EsbMessageCreator(outputText, jmsMsg.getJMSMessageID(), jmsMsg, event);

		// reply the message to the original destination.
		getLogger().debug("sending response to replyToDestination \"" + "{}\" " + "(Thread: {}): {} ",
				new Object[] { event.getReplyToDestination(), Thread.currentThread().getName(), outputText });

		if (event.getReplyToDestination() != null) {
			jmsTemplate.send(event.getReplyToDestination(), mc);
		}
	}

	protected abstract Logger getLogger();

	protected void logTextMessage(String textMessage) {
		getLogger().info(" ----------- Message received ----------- ");
		getLogger().info(" Recieved at: " + new DateTime() + ". Message: " + textMessage);
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void setMarshaller(Jaxb2Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public List<ESBProcess> getHandlersChain() {
		return handlersChain;
	}

	/**
	 * Add a handler to the chain.
	 * 
	 * @param handlerChain
	 */
	public void addHandler(ESBProcess handler) {
		if (handlersChain == null) {
			this.handlersChain = new ArrayList<ESBProcess>();
		}
		this.handlersChain.add(handler);
	}

	public PooledConnectionFactory getPooledConnectionFactory() {
		return pooledConnectionFactory;
	}

	/**
	 * The queue where the consumer is going to listen.
	 * 
	 * @return
	 */
	public String getJmsDestination() {
		return JmsDestination;
	}

	private class EsbMessageCreator implements MessageCreator {

		String outputText;
		String correlId;
		Message originalMsg;
		ESBEvent event;

		public EsbMessageCreator(final String outputText, final String correlId, final Message originalMsg,
				final ESBEvent event2) {
			this.outputText = outputText;
			this.correlId = correlId;
			this.originalMsg = originalMsg;
			this.event = event2;
		}

		@Override
		public Message createMessage(final Session session) throws JMSException {
			final TextMessage m = session.createTextMessage(outputText);

			JMSUtil.constructJmsMsg(m, event);

			m.setJMSCorrelationID(correlId);
			m.setJMSDeliveryMode(originalMsg.getJMSDeliveryMode());
			m.setJMSExpiration(originalMsg.getJMSExpiration());
			m.setJMSPriority(originalMsg.getJMSPriority());
			m.setJMSType(originalMsg.getJMSType());

			return m;
		}
	}
}