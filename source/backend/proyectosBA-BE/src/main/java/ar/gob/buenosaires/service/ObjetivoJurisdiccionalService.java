package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ObjetivoJurisdiccionalService {

	List<ObjetivoJurisdiccional> getObjetivosJurisdiccionales();

	ObjetivoJurisdiccional getObjetivoJurisdiccionalPorNombre(String nombre);

	ObjetivoJurisdiccional getObjetivoJurisdiccionalPorId(Long id);

	ObjetivoJurisdiccional getObjetivoJurisdiccionalPorCodigo(String codigo);

	ObjetivoJurisdiccional createObjetivoJurisdiccional(ObjetivoJurisdiccional objetivoJurisdiccional) throws ESBException;

	ObjetivoJurisdiccional updateObjetivoJurisdiccional(ObjetivoJurisdiccional objetivoJurisdiccional) throws ESBException;

	void deleteObjetivoJurisdiccional(Long id) throws ESBException;
}
