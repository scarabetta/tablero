package ar.gob.buenosaires.esb.producer;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.ESBException;

public class EsbAsyncProducer extends AbstractProducer implements EsbProducer{
	
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EsbAsyncProducer.class);

    @Override
	public void sendMessage(ESBEvent event) throws JMSException, ESBException {
		this.send(event);
	}
	
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
