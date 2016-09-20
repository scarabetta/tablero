package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.poblacionMeta.PoblacionMetaJpaDao;
import ar.gob.buenosaires.dao.jpa.poblacionMeta.PoblacionMetaRepository;
import ar.gob.buenosaires.dao.jpa.poblacionMeta.PoblacionMetaRepositoryImpl;
import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.service.PoblacionMetaService;

@Service
public class PoblacionMetaServiceImpl implements PoblacionMetaService {

	@Autowired
	private PoblacionMetaRepository repositorio;

	@Override
	public List<PoblacionMeta> getPoblacionesMeta() {
		return getPoblacionMetaDAO().findAll();
	}

	@Override
	public PoblacionMeta getPoblacionMetaPorNombre(String nombre) {
		return getPoblacionMetaDAO().findByNombre(nombre);
	}

	@Override
	public PoblacionMeta getPoblacionMetaPorId(Long id) {
		return getPoblacionMetaDAO().findOne(id);
	}
	
	@Override
	public PoblacionMeta createPoblacionMeta(PoblacionMeta PoblacionMeta) {
		return getPoblacionMetaDAO().save(PoblacionMeta);
	}
	
	@Override
	public PoblacionMeta updatePoblacionMeta(PoblacionMeta PoblacionMeta) {
		return getPoblacionMetaDAO().save(PoblacionMeta);
	}
	
	@Override
	public void deletePoblacionMeta(PoblacionMeta PoblacionMeta) {
		getPoblacionMetaDAO().delete(PoblacionMeta);
	}

	PoblacionMetaJpaDao getPoblacionMetaDAO() {
		return repositorio.getPoblacionMetaJpaDao();
	}
	
	@VisibleForTesting
	public void setPoblacionMetaRepository(PoblacionMetaRepositoryImpl repo) {
		this.repositorio = repo;
	}
}
