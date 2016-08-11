package ar.gob.buenosaires.esb.consumer;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXParseException;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.ESBException;

public class EsbServer extends AbstractConsumer implements EsbConsumer{
	
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EsbServer.class);
    
	@PostConstruct
	@Override
	public void init() throws JMSException {
		
		//create connection
		Connection connection = ((ConnectionFactory)getPooledConnectionFactory()).createConnection();
		connection.start();
		
		//create session
		Session session = connection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
		
		//create queue
		Queue sessionQueue = session.createQueue(getJmsDestination());
		
		//create message consumer
		MessageConsumer msgConsumer = session.createConsumer(sessionQueue);
		
		//create Jms Server
		msgConsumer.setMessageListener(this);
		
	}

	@Override
	public void onMessage(Message jmsMsg) {
		try{
			ESBEvent event = processMSG(jmsMsg);
			
			if(event != null){
				responseBack(jmsMsg, event);
			}
		} catch (SAXParseException e) {
			getLogger().debug("No se pudo parsear el mensaje.", e);
		} catch (ESBException e) {
			getLogger().error("Error inesperado. ", e);
		} catch (JMSException e) {
			throw new RuntimeException();
		}
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
