package ar.gob.buenosaires.esb.consumer;

import javax.annotation.PostConstruct;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;

import org.apache.activemq.command.ActiveMQQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXParseException;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.listener.factory.MessageListenerFactory;

public class EsbServer extends AbstractConsumer implements EsbConsumer{
	
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EsbServer.class);
    
	@PostConstruct
	@Override
	public void init() throws JMSException {
		
		//create connection
		Connection connection = ((ConnectionFactory)getPooledConnectionFactory()).createConnection();

		//create queue
		ActiveMQQueue queue = new ActiveMQQueue(getJmsDestination());
		
		//creamos el Factory que va a ayudarnos a crear los listeners.
		this.setMessageListenerFactory(new MessageListenerFactory());

		crearMultiplesListener(connection, queue);

		//arrancamos la conexion
		connection.start();
	}

	private void crearMultiplesListener(Connection connection, Destination queue) throws JMSException {
		for (int i = 0; i < getCantidadDeListeners(); i++) {
			getMessageListeners().add(getMessageListenerFactory().newInstance(this, connection, queue));
		}
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
