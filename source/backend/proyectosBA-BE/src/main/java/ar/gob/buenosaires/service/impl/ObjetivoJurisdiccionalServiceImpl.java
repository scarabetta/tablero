package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionRepository;
import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalJpaDao;
import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalRepository;
import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalRepositoryImpl;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ObjetivoJurisdiccionalService;

@Service
public class ObjetivoJurisdiccionalServiceImpl implements ObjetivoJurisdiccionalService {

	@Autowired
	private ObjetivoJurisdiccionalRepository repositorio;
	
	@Autowired
	private JurisdiccionRepository repositorioJurisdiccion;

	@Override
	public List<ObjetivoJurisdiccional> getObjetivosJurisdiccionales() {
		return getObjetivoJurisdiccionalDAO().findAll();
	}

	@Override
	public ObjetivoJurisdiccional getObjetivoJurisdiccionalPorNombre(String nombre) {
		return getObjetivoJurisdiccionalDAO().findByNombre(nombre);
	}

	@Override
	public ObjetivoJurisdiccional getObjetivoJurisdiccionalPorId(Long id) {
		return getObjetivoJurisdiccionalDAO().findOne(id);
	}
	
	@Override
	public ObjetivoJurisdiccional getObjetivoJurisdiccionalPorCodigo(String codigo) {
		return getObjetivoJurisdiccionalDAO().findByCodigo(codigo);
	}
	
	@Override
	public ObjetivoJurisdiccional createObjetivoJurisdiccional(ObjetivoJurisdiccional objetivoJurisdiccional) throws ESBException {
		Jurisdiccion jurisdiccion = repositorioJurisdiccion.getJurisdiccionJpaDao().findOne(objetivoJurisdiccional.getIdJurisdiccionAux());
		if (jurisdiccion != null) {						
			objetivoJurisdiccional.setJurisdiccion(jurisdiccion);			
			objetivoJurisdiccional.setCodigo(getProximoCodigoObjJurisdiccional(jurisdiccion));
			return getObjetivoJurisdiccionalDAO().save(objetivoJurisdiccional); 
		} else { 
			throw new ESBException("La Jurisdiccion con id: "
					+ objetivoJurisdiccional.getIdJurisdiccionAux()
					+ "no existe");
		}			
	}
	
	@Override
	public ObjetivoJurisdiccional updateObjetivoJurisdiccional(ObjetivoJurisdiccional objetivoJurisdiccional) throws ESBException {
		Jurisdiccion jurisdiccion = repositorioJurisdiccion.getJurisdiccionJpaDao().findOne(objetivoJurisdiccional.getIdJurisdiccionAux());
		if (jurisdiccion != null) {						
			objetivoJurisdiccional.setJurisdiccion(jurisdiccion);
			return getObjetivoJurisdiccionalDAO().save(objetivoJurisdiccional);
		} else { 
			throw new ESBException("La Jurisdiccion con id: "
					+ objetivoJurisdiccional.getIdJurisdiccionAux()
					+ "no existe");
		}	
	}
	
	@Override
	public void deleteObjetivoJurisdiccional(Long id) throws ESBException {
		ObjetivoJurisdiccional objetivoJurisdiccional = getObjetivoJurisdiccionalDAO().findOne(id);
		if (objetivoJurisdiccional != null) {
			getObjetivoJurisdiccionalDAO().delete(objetivoJurisdiccional);
		} else {
			throw new ESBException("No se encontro Objetivo Jurisdiccional con id: " + id);
		}
	}

	ObjetivoJurisdiccionalJpaDao getObjetivoJurisdiccionalDAO() {
		return repositorio.getObjetivoJurisdiccionalJpaDao();
	}
	
	private String getProximoCodigoObjJurisdiccional(Jurisdiccion jurisdiccion) {
		String codigoJurisdiccion = jurisdiccion.getCodigo();
		Integer codigoObjetivoJurisdiccional = 0;

		if (!jurisdiccion.getObjetivosJurisdiccionales().isEmpty()) {
			codigoObjetivoJurisdiccional = jurisdiccion.getObjetivosJurisdiccionales().parallelStream()
					.map(oj -> Integer.parseInt(oj.getCodigo().split("\\.")[1])).max(Integer::compare).get();
		}
		return codigoJurisdiccion.concat(".").concat(String.valueOf(codigoObjetivoJurisdiccional + 1));
	}
	
	@VisibleForTesting
	public void setObjetivoJurisdiccionalRepository(ObjetivoJurisdiccionalRepositoryImpl repo) {
		this.repositorio = repo;
	}

	@VisibleForTesting
	public void setRepositorioJurisdiccion(
			JurisdiccionRepository repositorioJurisdiccion) {
		this.repositorioJurisdiccion = repositorioJurisdiccion;
	}
}
