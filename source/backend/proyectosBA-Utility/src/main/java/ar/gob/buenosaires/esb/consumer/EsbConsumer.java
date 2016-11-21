package ar.gob.buenosaires.esb.consumer;

import javax.jms.JMSException;
import javax.jms.Message;

public interface EsbConsumer {

	void init() throws JMSException;
	
	public void onMessage(Message msg) throws JMSException;
}
