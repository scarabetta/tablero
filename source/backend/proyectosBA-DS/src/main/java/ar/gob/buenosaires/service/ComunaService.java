package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.Comuna;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ComunaService {

	List<Comuna> getComunas() throws ESBException, JMSException;

//	Comuna getComunaPorId(String id) throws ESBException, JMSException;
//
//	Comuna getComunaesPorCodigo(String codigo) throws ESBException, JMSException;
//	
//	Comuna getComunaesByName(String nombre) throws ESBException, JMSException;
//
//	void createComunaes(Comuna Comuna) throws ESBException, JMSException;
//
//	void updateComunaes(Comuna Comuna) throws ESBException, JMSException;
//	
//	void deleteComuna(Comuna Comuna) throws ESBException, JMSException;		

}

