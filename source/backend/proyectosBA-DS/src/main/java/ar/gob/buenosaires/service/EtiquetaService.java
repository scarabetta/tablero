package ar.gob.buenosaires.service;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.EtiquetasMsg;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface EtiquetaService {

	EtiquetasMsg getEtiquetas() throws ESBException, JMSException;
	
	EtiquetasMsg getEtiquetasPorProyecto(String id) throws ESBException, JMSException;	
}
