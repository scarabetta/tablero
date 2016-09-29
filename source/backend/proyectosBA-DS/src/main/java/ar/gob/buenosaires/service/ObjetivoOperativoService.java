package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ObjetivoOperativoService {
	
	List<ObjetivoOperativo> getObjetivosOperativos() throws ESBException, JMSException;

	ObjetivoOperativo getObjetivoOperativoPorId(String id) throws ESBException, JMSException;

	ObjetivoOperativo getObjetivoOperativoPorCodigo(String codigo) throws ESBException, JMSException;
	
	ObjetivoOperativo getObjetivoOperativoPorNombre(String nombre) throws ESBException, JMSException;

	ObjetivoOperativo createObjetivoOperativo(ObjetivoOperativo jurisdiccion, String email) throws ESBException, JMSException;

	ObjetivoOperativo updateObjetivoOperativo(ObjetivoOperativo jurisdiccion, String email) throws ESBException, JMSException;
	
	void deleteObjetivoOperativo(String id, String email) throws ESBException, JMSException;	

}