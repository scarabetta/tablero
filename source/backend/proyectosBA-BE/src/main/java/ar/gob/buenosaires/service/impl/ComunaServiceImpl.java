package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.comuna.ComunaJpaDao;
import ar.gob.buenosaires.dao.jpa.comuna.ComunaRepository;
import ar.gob.buenosaires.domain.Comuna;
import ar.gob.buenosaires.service.ComunaService;

@Service
public class ComunaServiceImpl implements ComunaService {

	@Autowired
	private ComunaRepository repositorio;

	@Override
	public List<Comuna> getComunas() {
		return getComunaDAO().findAll();
	}

	@Override
	public Comuna getComunaPorNombre(String nombre) {
		return getComunaDAO().findByNombre(nombre);
	}

	@Override
	public Comuna getComunaPorId(Long id) {
		return getComunaDAO().findOne(id);
	}
	
	@Override
	public Comuna createComuna(Comuna comuna) {
		return getComunaDAO().save(comuna);
	}
	
	@Override
	public Comuna updateComuna(Comuna comuna) {
		return getComunaDAO().save(comuna);
	}
	
	@Override
	public void deleteComuna(Comuna comuna) {
		getComunaDAO().delete(comuna);
	}

	public ComunaJpaDao getComunaDAO() {
		return repositorio.getComunaJpaDao();
	}

	@VisibleForTesting
	public void setComunaRepository(ComunaRepository repo) {
		this.repositorio = repo;
	}
}
