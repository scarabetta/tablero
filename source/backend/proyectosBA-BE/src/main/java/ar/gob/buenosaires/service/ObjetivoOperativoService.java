package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ObjetivoOperativoService {

	List<ObjetivoOperativo> getObjetivosOperativos();

	ObjetivoOperativo getObjetivoOperativoPorNombre(String nombre);

	ObjetivoOperativo getObjetivoOperativoPorId(Long id);

	ObjetivoOperativo getObjetivoOperativoPorCodigo(String codigo);

	ObjetivoOperativo createObjetivoOperativo(ObjetivoOperativo objetivoOperativo) throws ESBException;

	ObjetivoOperativo updateObjetivoOperativo(ObjetivoOperativo objetivoOperativo) throws ESBException;

	void deleteObjetivoOperativo(Long id) throws ESBException;
}
