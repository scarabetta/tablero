package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.ejeDeGobierno.EjeDeGobiernoJpaDao;
import ar.gob.buenosaires.dao.jpa.ejeDeGobierno.EjeDeGobiernoRepository;
import ar.gob.buenosaires.dao.jpa.ejeDeGobierno.EjeDeGobiernoRepositoryImpl;
import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.service.EjeDeGobiernoService;

@Service
public class EjeDeGobiernoServiceImpl implements EjeDeGobiernoService {

	@Autowired
	private EjeDeGobiernoRepository repositorio;

	@Override
	public List<EjeDeGobierno> getEjesDeGobierno() {
		return getEjeDeGobiernoDAO().findAll();
	}

	@Override
	public EjeDeGobierno getEjeDeGobiernoPorNombre(String nombre) {
		return getEjeDeGobiernoDAO().findByNombre(nombre);
	}

	@Override
	public EjeDeGobierno getEjeDeGobiernoPorId(Long id) {
		return getEjeDeGobiernoDAO().findOne(id);
	}
	
	@Override
	public EjeDeGobierno createEjeDeGobierno(EjeDeGobierno ejeDeGobierno) {
		return getEjeDeGobiernoDAO().save(ejeDeGobierno);
	}
	
	@Override
	public EjeDeGobierno updateEjeDeGobierno(EjeDeGobierno ejeDeGobierno) {
		return getEjeDeGobiernoDAO().save(ejeDeGobierno);
	}
	
	@Override
	public void deleteEjeDeGobierno(EjeDeGobierno ejeDeGobierno) {
		getEjeDeGobiernoDAO().delete(ejeDeGobierno);
	}

	public EjeDeGobiernoJpaDao getEjeDeGobiernoDAO() {
		return repositorio.getEjeDeGobiernoJpaDao();
	}

	@VisibleForTesting
	public void setEjeDeGobiernoRepository(EjeDeGobiernoRepositoryImpl repo) {
		this.repositorio = repo;
	}
}
