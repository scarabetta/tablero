package ar.gob.buenosaires.service;

import ar.gob.buenosaires.domain.EtiquetasMsg;

public interface EtiquetasService {

	EtiquetasMsg getEtiquetas();

	EtiquetasMsg getEtiquetasPorProyecto(Long id);

}
