package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.hitoProyecto.HitoProyectoJpaDao;
import ar.gob.buenosaires.dao.jpa.hitoProyecto.HitoProyectoRepository;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepository;
import ar.gob.buenosaires.domain.HitoProyecto;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.HitoProyectoService;

@Service
public class HitoProyectoServiceImpl implements HitoProyectoService {

	@Autowired
	private HitoProyectoRepository repository;
	
	@Autowired
	private ProyectoRepository repositorioProyectos;
	
	@Override
	public List<HitoProyecto> getHitosDeProyecto() {
		return getHitoProyectoJpaDao().findAll();
	}

	@Override
	public HitoProyecto getHitoProyectoPorId(Long id) {
		return getHitoProyectoJpaDao().getOne(id);
	}

	@Override
	public HitoProyecto createHitoProyecto(HitoProyecto hitoProyecto) throws ESBException {
		return getHitoProyectoJpaDao().save(hitoProyecto);
	}

	@Override
	public HitoProyecto updateHitoProyecto(HitoProyecto hitoProyecto) throws ESBException {
		
		HitoProyecto hitoProyectoResult;
		
		if(hitoProyecto.getIdHitoPadreAux() != null && hitoProyecto.getIdProyectoAux() == null) {
			HitoProyecto hitoProyectoPadre = this.getHitoProyectoJpaDao()
					.findOne(hitoProyecto.getIdHitoPadreAux());
			if(hitoProyectoPadre != null) {
				hitoProyecto.setHitoPadre(hitoProyectoPadre); 
			}
			hitoProyectoResult = getHitoProyectoJpaDao().save(hitoProyecto);
		} else if(hitoProyecto.getIdHitoPadreAux() == null && hitoProyecto.getIdProyectoAux() != null) {
			Proyecto proyecto = repositorioProyectos.getProyectoJpaDao().findOne(hitoProyecto.getIdProyectoAux());
			if(proyecto != null) {
				hitoProyecto.setProyecto(proyecto);
			}
			hitoProyectoResult = getHitoProyectoJpaDao().save(hitoProyecto);
		} else {
			throw new ESBException(CodigoError.PROYECTO_INEXISTENTE.getCodigo(), "El hito debe tener un proyecto o un hito padre asigando.");
		}
		return hitoProyectoResult;
	}
	
	@Override
	public void deleteHitoDeProyecto(Long id) throws ESBException {
		getHitoProyectoJpaDao().delete(id);
	}
	
	HitoProyectoJpaDao getHitoProyectoJpaDao() {
		return repository.getHitoProyectoJpaDao();
	}

	@VisibleForTesting
	public void setHitoProyectoRepository(HitoProyectoRepository repo) {
		this.repository = repo;
	}

}
