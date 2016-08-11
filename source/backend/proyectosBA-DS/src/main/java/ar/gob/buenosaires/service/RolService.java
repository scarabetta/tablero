package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface RolService {

	List<Rol> getRoles() throws ESBException, JMSException;

	Rol getRolPorId(Long id) throws ESBException, JMSException;

	Rol createRol(Rol rol) throws ESBException, JMSException;

	Rol updateRol(Rol rol) throws ESBException, JMSException;

	void deleteRol(String id) throws ESBException, JMSException;

}
