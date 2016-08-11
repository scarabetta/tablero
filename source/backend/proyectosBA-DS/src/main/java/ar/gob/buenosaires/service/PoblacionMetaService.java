package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface PoblacionMetaService {

	List<PoblacionMeta> getPoblacionesMeta() throws ESBException, JMSException;

//	PoblacionMeta getPoblacionMetaPorId(String id) throws ESBException, JMSException;
//
//	PoblacionMeta getPoblacionMetaesPorCodigo(String codigo) throws ESBException, JMSException;
//	
//	PoblacionMeta getPoblacionMetaesByName(String nombre) throws ESBException, JMSException;
//
//	void createPoblacionMetaes(PoblacionMeta PoblacionMeta) throws ESBException, JMSException;
//
//	void updatePoblacionMetaes(PoblacionMeta PoblacionMeta) throws ESBException, JMSException;
//	
//	void deletePoblacionMeta(PoblacionMeta PoblacionMeta) throws ESBException, JMSException;		

}

