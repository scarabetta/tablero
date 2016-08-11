package ar.gob.buenosaires.esb.service;

import javax.jms.JMSException;

import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface EsbService {

	EsbBaseMsg sendToBus(EsbBaseMsg req, String origin, String action) throws ESBException, JMSException;

}
