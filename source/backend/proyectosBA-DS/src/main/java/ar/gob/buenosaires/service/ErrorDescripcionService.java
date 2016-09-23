package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.ErrorDescripcion;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ErrorDescripcionService {
	
	List<ErrorDescripcion> getErrores() throws ESBException, JMSException;
}
