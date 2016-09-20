package ar.gob.buenosaires.service;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.EtiquetaResponse;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface EtiquetaService {

	EtiquetaResponse getEtiquetas() throws ESBException, JMSException;

}
