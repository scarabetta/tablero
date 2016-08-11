package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface EjeDeGobiernoService {

	List<EjeDeGobierno> getEjesDeGobierno() throws ESBException, JMSException;

	EjeDeGobierno getEjeDeGobiernoPorId(String id) throws ESBException, JMSException;
	
	EjeDeGobierno getEjeDeGobiernoByName(String nombre) throws ESBException, JMSException;

//	void createEjeDeGobierno(EjeDeGobierno ejeDeGobierno) throws ESBException, JMSException;
//
//	void updateEjeDeGobierno(EjeDeGobierno ejeDeGobierno) throws ESBException, JMSException;
//	
//	void deleteEjeDeGobierno(EjeDeGobierno ejeDeGobierno) throws ESBException, JMSException;		

}

