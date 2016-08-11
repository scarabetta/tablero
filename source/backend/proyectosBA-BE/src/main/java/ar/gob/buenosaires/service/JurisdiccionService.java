package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface JurisdiccionService {

	List<Jurisdiccion> getJurisdicciones();

	Jurisdiccion getJurisdiccionPorNombre(String nombre);

	Jurisdiccion getJurisdiccionPorId(Long id);
	
	Jurisdiccion getJurisdiccionPorCodigo(String codigo);
	
	Jurisdiccion createJurisdiccion(Jurisdiccion jurisdiccion);
	
	Jurisdiccion updateJurisdiccion (Jurisdiccion jurisdiccion);
	
	void deleteJurisdiccion (Long id) throws ESBException;
}
