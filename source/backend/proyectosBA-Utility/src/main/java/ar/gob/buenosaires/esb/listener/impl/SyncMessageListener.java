package ar.gob.buenosaires.esb.listener.impl;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.gob.buenosaires.esb.consumer.EsbConsumer;
import ar.gob.buenosaires.esb.listener.IMessageListener;

public class SyncMessageListener implements IMessageListener {
	
    private final static Logger LOGGER = LoggerFactory
            .getLogger(SyncMessageListener.class);
    protected EsbConsumer consumer;
    protected Session session;
    protected MessageConsumer messageConsumer;
    protected Destination destination;
    protected String subscriptionId;
    protected Boolean durableSubscription;

	@Override
	public void onMessage(Message message) {
		try {
			consumer.onMessage(message);
//			session.commit();
		} catch (JMSException e) {
			LOGGER.error(e.getMessage());
		}
	}

	@Override
	public void init(EsbConsumer consumer, Connection jmsConnection, Destination destination, String subscriptionId) throws JMSException {
        this.consumer = consumer;
        this.destination = destination;
        this.subscriptionId = subscriptionId;

        session = jmsConnection.createSession(false, Session.CLIENT_ACKNOWLEDGE);
        messageConsumer = session.createConsumer(destination);
        messageConsumer.setMessageListener(this);
	}
}
