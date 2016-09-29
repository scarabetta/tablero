package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ObjetivoJurisdiccionalService {
	
	List<ObjetivoJurisdiccional> getObjetivosJurisdiccionales() throws ESBException, JMSException;

	ObjetivoJurisdiccional getObjetivoJurisdiccionalPorId(String id) throws ESBException, JMSException;

	ObjetivoJurisdiccional getObjetivoJurisdiccionalPorCodigo(String codigo) throws ESBException, JMSException;
	
	ObjetivoJurisdiccional getObjetivoJurisdiccionalPorNombre(String nombre) throws ESBException, JMSException;

	ObjetivoJurisdiccional createObjetivoJurisdiccional(ObjetivoJurisdiccional jurisdiccion, String email) throws ESBException, JMSException;

	ObjetivoJurisdiccional updateObjetivoJurisdiccional(ObjetivoJurisdiccional jurisdiccion, String email) throws ESBException, JMSException;
	
	void deleteObjetivoJurisdiccional(String id, String email) throws ESBException, JMSException;	

}
