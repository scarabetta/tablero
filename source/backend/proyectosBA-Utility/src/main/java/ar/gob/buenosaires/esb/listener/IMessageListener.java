package ar.gob.buenosaires.esb.listener;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageListener;

import ar.gob.buenosaires.esb.consumer.EsbConsumer;

public interface IMessageListener extends MessageListener {

	public void init(EsbConsumer listener, Connection jmsConnection, Destination destination,
			final String subscriptionId) throws JMSException;

}
