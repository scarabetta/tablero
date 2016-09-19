package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface CompromisoPublicoService {

	List<CompromisoPublico> getCompromisosPublicos() throws ESBException, JMSException;

	CompromisoPublico getCompromisoPublicoPorId(Long id) throws ESBException, JMSException;

	CompromisoPublico createCompromisoPublico(CompromisoPublico compromisoPublico) throws ESBException, JMSException;

	CompromisoPublico updateCompromisoPublico(CompromisoPublico compromisoPublico) throws ESBException, JMSException;

	void deleteCompromisoPublico(String id) throws ESBException, JMSException;

}
