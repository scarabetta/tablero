package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface TemaTransversalService {

	TemaTransversal createTemaTransversal(TemaTransversal temaTransversal) throws ESBException;

	TemaTransversal updateTemaTransversal(TemaTransversal temaTransversal) throws ESBException;

	void deleteTemaTransversal(Long id) throws ESBException;

	TemaTransversal getTemaTransversalPorId(Long id);

	List<TemaTransversal> getTemasTransversales();

}
