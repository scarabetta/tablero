package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.JurisdiccionResumen;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface JurisdiccionService {

	List<Jurisdiccion> getJurisdicciones(String userMail) throws ESBException, JMSException;
	
	List<JurisdiccionResumen> getJurisdiccionesResumen(String userMail) throws ESBException, JMSException;

	Jurisdiccion getJurisdiccionPorId(String id, Usuario usuario) throws ESBException, JMSException;

	Jurisdiccion getJurisdiccionesPorCodigo(String codigo) throws ESBException, JMSException;
	
	Jurisdiccion getJurisdiccionesByName(String nombre) throws ESBException, JMSException;

	Jurisdiccion createJurisdicciones(Jurisdiccion jurisdiccion, String email) throws ESBException, JMSException;

	Jurisdiccion updateJurisdicciones(Jurisdiccion jurisdiccion, String email) throws ESBException, JMSException;
	
	void deleteJurisdiccion(String id, String email) throws ESBException, JMSException;

	void presentarProyectosCompletos(String id, String email) throws ESBException, JMSException;

	Jurisdiccion getJurisdiccionPorIdyEmail(String id, String mailDelUsuarioDelToken) throws ESBException, JMSException;

}

