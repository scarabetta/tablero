package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.compromisoPublico.CompromisoPublicoJpaDao;
import ar.gob.buenosaires.dao.jpa.compromisoPublico.CompromisoPublicoRepository;
import ar.gob.buenosaires.dao.jpa.compromisoPublico.CompromisoPublicoRepositoryImpl;
import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.CompromisoPublicoService;

@Service
public class CompromisoPublicoServiceImpl implements CompromisoPublicoService {
	
	@Autowired
	private CompromisoPublicoRepository repository;

	@Override
	public CompromisoPublico createCompromisoPublico(CompromisoPublico compromisoPublico) throws ESBException {
		CompromisoPublico compromisoPublicoNombre = getCompromisoPublicoJpaDao().findByCompromisoPublico(compromisoPublico.getCompromisoPublico());
		if (compromisoPublicoNombre == null) {
			compromisoPublico.setActivo(true);
			return getCompromisoPublicoJpaDao().save(compromisoPublico);
		} else {
			throw new ESBException("Ya existe un Compromiso Publico con nombre: " + compromisoPublico.getCompromisoPublico());
		}				
	}

	@Override
	public CompromisoPublico updateCompromisoPublico(CompromisoPublico compromisoPublico) throws ESBException {
		CompromisoPublico compromisoPublicoNombre = getCompromisoPublicoJpaDao().findByCompromisoPublico(compromisoPublico.getCompromisoPublico());
		if (compromisoPublicoNombre != null && compromisoPublicoNombre.getIdCompromisoPublico() != compromisoPublico.getIdCompromisoPublico()) {
			throw new ESBException("Ya existe un Compromiso Publico con nombre: " + compromisoPublico.getCompromisoPublico());
		} else {
			return getCompromisoPublicoJpaDao().save(compromisoPublico);
		}
	}

	@Override
	public void deleteCompromisoPublico(Long id) throws ESBException {
		CompromisoPublico compromisoPublico = getCompromisoPublicoJpaDao().findOne(id);
		if (compromisoPublico != null) {
			getCompromisoPublicoJpaDao().delete(compromisoPublico);
		} else {
			throw new ESBException("No se encontro Compromiso Publico con id: " + id);
		}		
	}

	@Override
	public CompromisoPublico getCompromisoPublicoPorId(Long id) {
		return getCompromisoPublicoJpaDao().findOne(id);
	}

	@Override
	public List<CompromisoPublico> getCompromisosPublicos() {
		return getCompromisoPublicoJpaDao().findAll();
	}
	
	CompromisoPublicoJpaDao getCompromisoPublicoJpaDao(){
		return repository.getCompromisoPublicoJpaDao();
	}
	
	@VisibleForTesting
	public void setCompromisoPublicoRepository(CompromisoPublicoRepositoryImpl repo) {
		this.repository = repo;
	}
}
