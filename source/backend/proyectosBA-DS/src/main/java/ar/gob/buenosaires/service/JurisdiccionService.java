package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface JurisdiccionService {

	List<Jurisdiccion> getJurisdicciones(Usuario user) throws ESBException, JMSException;

	Jurisdiccion getJurisdiccionPorId(String id, Usuario usuario) throws ESBException, JMSException;

	Jurisdiccion getJurisdiccionesPorCodigo(String codigo) throws ESBException, JMSException;
	
	Jurisdiccion getJurisdiccionesByName(String nombre) throws ESBException, JMSException;

	Jurisdiccion createJurisdicciones(Jurisdiccion jurisdiccion) throws ESBException, JMSException;

	Jurisdiccion updateJurisdicciones(Jurisdiccion jurisdiccion) throws ESBException, JMSException;
	
	void deleteJurisdiccion(String id) throws ESBException, JMSException;

	void presentarProyectosCompletos(String id) throws ESBException, JMSException;		

}

