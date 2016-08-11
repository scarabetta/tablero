package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionJpaDao;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionRepository;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionRepositoryImpl;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.JurisdiccionService;

@Service
public class JurisdiccionServiceImpl implements JurisdiccionService {

	@Autowired
	private JurisdiccionRepository repositorio;

	@Override
	public List<Jurisdiccion> getJurisdicciones() {
		return getJurisdiccionDAO().findAll();
	}

	@Override
	public Jurisdiccion getJurisdiccionPorNombre(String nombre) {
		return getJurisdiccionDAO().findByNombre(nombre);
	}

	@Override
	public Jurisdiccion getJurisdiccionPorId(Long id) {
		return getJurisdiccionDAO().findOne(id);
	}
	
	@Override
	public Jurisdiccion getJurisdiccionPorCodigo(String codigo) {
		return getJurisdiccionDAO().findByCodigo(codigo);
	}
	
	@Override
	public Jurisdiccion createJurisdiccion(Jurisdiccion jurisdiccion) {
		return getJurisdiccionDAO().save(jurisdiccion);
	}
	
	@Override
	public Jurisdiccion updateJurisdiccion(Jurisdiccion jurisdiccion) {
		return getJurisdiccionDAO().save(jurisdiccion);
	}
	
	@Override
	public void deleteJurisdiccion(Long id) throws ESBException {		
		Jurisdiccion jurisdiccion = getJurisdiccionDAO().findOne(id);
		if (jurisdiccion != null) {
			getJurisdiccionDAO().delete(jurisdiccion);
		} else {
			throw new ESBException("No se encontro Jurisdiccion con id: " + id);
		}
	}

	public JurisdiccionJpaDao getJurisdiccionDAO() {
		return repositorio.getJurisdiccionJpaDao();
	}

	@VisibleForTesting
	public void setJurisdiccionRepository(JurisdiccionRepositoryImpl repo) {
		this.repositorio = repo;
	}
}
