/*
 * Producer.java
 * 
 * Created on Jul 13, 2015
 * 
 */
package ar.gob.buenosaires.esb.producer;

import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;

public abstract class AbstractProducer {
	
    @Autowired
    private JmsTemplate jmsTemplate;

    @Autowired
    private Jaxb2Marshaller marshaller;
    
    @Value("${esb.producer.timeout}")
    private Long responseTimeout;
    
    @Value("${esb.producer.replyToDestination}")
    private String replyToDestination;
    
    private ActiveMQQueue activeMQQueue;
    
    @Value("${esb.producer.destination}")
    private String destination;

    public void send(final ESBEvent event) {
    	getLogger().debug(event.toString());
        MessageCreator mc = new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                String messageAsString = JMSUtil.marshal(event.getObj(), marshaller);
                TextMessage message = session.createTextMessage(messageAsString);
                //reply to destination, en el topic/queue donde va a volver la respuesta.
                event.setReplyToDestination(getActiveMQQueue());
                JMSUtil.constructJmsMsg(message, event);
                return message;
            }
        };
        this.jmsTemplate.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        getLogger().debug("enviando mensaje a un/a {} destino: {}",
                jmsTemplate.isPubSubDomain() ? "topic" : "queue",
                		getDestination());
        this.jmsTemplate.send(destination, mc);
    }
    
    public void receiveSelected(final ESBEvent event) throws JMSException, ESBException{
        // Note: by jms convention, server is expected to perform
        // a messageId/correlation Id switch in our response message.
        final String messageSelector = "JMSCorrelationID = '"
                + event.getOutputMsg().getJMSMessageID() + "'";
        jmsTemplate.setReceiveTimeout(responseTimeout);
        final TextMessage replyMessage = (TextMessage) jmsTemplate
                .receiveSelected(replyToDestination, messageSelector);
        if (null == replyMessage) {
            throw new ESBException(String.format("se supero el tiempo de espera por la respuesta del BUS " +
            		"(timeout=%d milisegundos)", responseTimeout));
        }
        createResponseEvent(event, replyMessage);
    }

	private void createResponseEvent(final ESBEvent event, final TextMessage replyMessage) throws JMSException {
		event.setInputMsg(replyMessage);
        event.setType(replyMessage.getStringProperty(ESBEvent.TYPE_TAG));
        event.setAction(replyMessage.getStringProperty(ESBEvent.ACTION_TAG));
        event.setRequestStatus(replyMessage.getStringProperty(ESBEvent.STATUS_TAG));
        event.setStatusDescription(replyMessage.getStringProperty(ESBEvent.STATUS_DESC_TAG));
        final String text = replyMessage.getText();
        getLogger().debug("\"{}\" se recibio la respuesta para el mensaje "
                        + "{} "
                        + "(Thread: {}): {} ",new Object[]{
                        "displayName",
                        text,
                        Thread.currentThread().getName()});
        final Object message = StringUtils.isBlank(text) ? ""
                : JMSUtil.unmarshal(replyMessage.getText(), marshaller);
        event.setObj(message);
	}
    
    protected abstract Logger getLogger();

	public Long getResponseTimeout() {
		return responseTimeout;
	}

	public String getReplyToDestination() {
		return replyToDestination;
	}

	public String getDestination() {
		return destination;
	}

	public void setMarshaller(Jaxb2Marshaller marshaller) {
		this.marshaller = marshaller;
	}

	public ActiveMQQueue getActiveMQQueue() {
		if(activeMQQueue == null){
			activeMQQueue = new ActiveMQQueue(replyToDestination);
		}
		return activeMQQueue;
	}
}
