package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface OtrasEtiquetasService {

	List<OtraEtiqueta> getOtrasEtiquetas() throws ESBException, JMSException;

	OtraEtiqueta getOtraEtiquetaPorId(Long id) throws ESBException, JMSException;

	OtraEtiqueta createOtraEtiqueta(OtraEtiqueta OtraEtiqueta, String email) throws ESBException, JMSException;

	OtraEtiqueta updateOtraEtiqueta(OtraEtiqueta OtraEtiqueta, String email) throws ESBException, JMSException;

	void deleteOtraEtiqueta(String id, String email) throws ESBException, JMSException;

}
