package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.JurisdiccionResumen;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface JurisdiccionService {

	List<Jurisdiccion> getJurisdicciones();
	
	List<JurisdiccionResumen> getJurisdiccionesResumen(Usuario user);

	Jurisdiccion getJurisdiccionPorNombre(String nombre);

	Jurisdiccion getJurisdiccionPorId(Long id);
	
	Jurisdiccion getJurisdiccionPorCodigo(String codigo);
	
	Jurisdiccion createJurisdiccion(Jurisdiccion jurisdiccion);
	
	Jurisdiccion updateJurisdiccion (Jurisdiccion jurisdiccion);
	
	void deleteJurisdiccion (Long id) throws ESBException;

	void presentarProyectosCompletos(Long id) throws ESBException;

	Jurisdiccion getJurisdiccionPorIdParaSecretaria(Long id);

	List<Jurisdiccion> getJurisdiccionesParaSecretaria();
}
