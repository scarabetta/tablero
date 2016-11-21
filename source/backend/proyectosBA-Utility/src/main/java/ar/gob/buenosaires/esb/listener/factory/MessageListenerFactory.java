package ar.gob.buenosaires.esb.listener.factory;

import java.util.concurrent.atomic.AtomicLong;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;

import ar.gob.buenosaires.esb.consumer.EsbConsumer;
import ar.gob.buenosaires.esb.listener.impl.SyncMessageListener;

public class MessageListenerFactory {

	private String subscriptionId;
	private volatile static AtomicLong id = new AtomicLong();

	public SyncMessageListener newInstance(EsbConsumer consumer, Connection jmsConnection, Destination destination)
			throws JMSException {
		subscriptionId = jmsConnection.getClientID() + id.incrementAndGet();
		SyncMessageListener retval = new SyncMessageListener();
		// retval.setDurableSubscription(isDurableSubscription);
		retval.init(consumer, jmsConnection, destination, subscriptionId);

		return retval;
	}
}
