package ar.gob.buenosaires.esb.handler;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ESBProcess {

    /**
     * Esb event message handler in a process chain.
     * <p>
     * Returns "true" to continue down the chain or "false" to terminate
     * processing.
     *
     * @param service the esb service that sourced the event
     * @param msg the esb message
     * @return "true" to continue down chain, otherwise "false"
     * @throws EsbException
     */
    public boolean onMessage(ESBEvent event) throws ESBException;
    
}
