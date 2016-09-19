package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface CompromisoPublicoService {

	CompromisoPublico createCompromisoPublico(CompromisoPublico compromisoPublico) throws ESBException;

	CompromisoPublico updateCompromisoPublico(CompromisoPublico compromisoPublico) throws ESBException;

	void deleteCompromisoPublico(Long id) throws ESBException;

	CompromisoPublico getCompromisoPublicoPorId(Long id);

	List<CompromisoPublico> getCompromisosPublicos();
}
