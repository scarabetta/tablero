package ar.gob.buenosaires.esb.producer;

import javax.jms.JMSException;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface EsbProducer {

	void sendMessage(ESBEvent event) throws JMSException, ESBException;

}
