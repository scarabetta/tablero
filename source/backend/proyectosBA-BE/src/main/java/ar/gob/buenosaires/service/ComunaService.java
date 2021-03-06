package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Comuna;

public interface ComunaService {

	List<Comuna> getComunas();

	Comuna getComunaPorNombre(String nombre);

	Comuna getComunaPorId(Long id);
	
	Comuna createComuna(Comuna comuna);
	
	Comuna updateComuna (Comuna comuna);
	
	void deleteComuna (Comuna comuna);
}
