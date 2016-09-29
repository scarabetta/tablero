package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface CompromisoPublicoService {

	List<CompromisoPublico> getCompromisosPublicos() throws ESBException, JMSException;

	CompromisoPublico getCompromisoPublicoPorId(Long id) throws ESBException, JMSException;

	CompromisoPublico createCompromisoPublico(CompromisoPublico compromisoPublico, String email) throws ESBException, JMSException;

	CompromisoPublico updateCompromisoPublico(CompromisoPublico compromisoPublico, String email) throws ESBException, JMSException;

	void deleteCompromisoPublico(String id, String email) throws ESBException, JMSException;

}
