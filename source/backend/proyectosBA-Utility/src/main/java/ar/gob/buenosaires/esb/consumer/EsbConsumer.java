package ar.gob.buenosaires.esb.consumer;

import javax.jms.JMSException;

public interface EsbConsumer {

	void init() throws JMSException;
}
