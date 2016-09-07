package ar.gob.buenosaires.service.impl;

import java.util.Arrays;
import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.area.AreaJpaDao;
import ar.gob.buenosaires.dao.jpa.area.AreaRepository;
import ar.gob.buenosaires.domain.Area;
import ar.gob.buenosaires.service.AreaService;

@Service
public class AreaServiceImpl implements AreaService {

	@Autowired
	private AreaRepository repositorio;

	@Override
	public List<Area> getAreas() {
		return getAreaDAO().findAll();
	}

	@Override
	public List<Area> getAreasPorNombre(String nombre) {
		return getAreaDAO().findByNombre(nombre);
	}

	@Override
	public Area getAreaPorId(Long id) {
		return getAreaDAO().findOne(id);
	}

	@Override
	public Area getAreaPorNombreYIdJurisdiccion(String nombre, Long idIdJurisdiccion) {
		return getAreaDAO().findByNombreAndIdJurisdiccion(nombre, idIdJurisdiccion);
	}

	public AreaJpaDao getAreaDAO() {
		return repositorio.getAreaJpaDao();
	}

	@VisibleForTesting
	public void setAreaRepository(AreaRepository repo) {
		this.repositorio = repo;
	}

}
