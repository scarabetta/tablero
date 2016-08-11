package ar.gob.buenosaires.esb.producer;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.ESBSyncEvent;
import ar.gob.buenosaires.esb.exception.ESBException;

//@Component
public class EsbSyncProducer extends AbstractProducer implements EsbProducer{
	
    private final static Logger LOGGER = LoggerFactory
            .getLogger(EsbSyncProducer.class);
	
	public void sendMessage(ESBEvent event) throws JMSException, ESBException {
		this.send(event);
        // If the event is a synchronous type, wait for the response
        // from some other service of the bus.
		if(event instanceof ESBSyncEvent){
			this.receiveSelected(event);
		}
	}
	
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
