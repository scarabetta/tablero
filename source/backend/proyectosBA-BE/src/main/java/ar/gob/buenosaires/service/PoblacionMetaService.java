package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.PoblacionMeta;

public interface PoblacionMetaService {

	List<PoblacionMeta> getPoblacionesMeta();

	PoblacionMeta getPoblacionMetaPorNombre(String nombre);

	PoblacionMeta getPoblacionMetaPorId(Long id);
	
	PoblacionMeta createPoblacionMeta(PoblacionMeta poblacionMeta);
	
	PoblacionMeta updatePoblacionMeta (PoblacionMeta poblacionMeta);
	
	void deletePoblacionMeta (PoblacionMeta poblacionMeta);
}
