package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface OtrasEtiquetasService {

	OtraEtiqueta createOtrasEtiquetas(OtraEtiqueta otrasEtiquetas) throws ESBException;

	OtraEtiqueta updateOtrasEtiquetas(OtraEtiqueta OtrasEtiquetas) throws ESBException;

	void deleteOtrasEtiquetas(Long id) throws ESBException;

	OtraEtiqueta getOtrasEtiquetasPorId(Long id);

	List<OtraEtiqueta> getOtrasEtiquetas();

}
