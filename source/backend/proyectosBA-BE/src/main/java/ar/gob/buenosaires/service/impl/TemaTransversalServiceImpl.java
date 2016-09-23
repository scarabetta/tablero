package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.temaTransversal.TemaTransversalJpaDao;
import ar.gob.buenosaires.dao.jpa.temaTransversal.TemaTransversalRepository;
import ar.gob.buenosaires.dao.jpa.temaTransversal.TemaTransversalRepositoryImpl;
import ar.gob.buenosaires.domain.EstadoTemaTransversal;
import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.TemaTransversalService;

@Service
public class TemaTransversalServiceImpl implements TemaTransversalService {
	
	@Autowired
	private TemaTransversalRepository repository;

	@Override
	public TemaTransversal createTemaTransversal(TemaTransversal temaTransversal) throws ESBException {
		TemaTransversal temaTransversalNombre = getTemaTransversalJpaDao().findByTemaTransversal(temaTransversal.getTemaTransversal());
		if (temaTransversalNombre == null) {
			temaTransversal.setActivo(true);
			return getTemaTransversalJpaDao().save(temaTransversal);
		} else {
			throw new ESBException(CodigoError.TEMA_TRANSVERSAL_REPETIDO.getCodigo(), "Ya existe un tema transversal con nombre: " + temaTransversal.getTemaTransversal());
		}				
	}

	@Override
	public TemaTransversal updateTemaTransversal(TemaTransversal temaTransversal) throws ESBException {
		TemaTransversal temaTransversalNombre = getTemaTransversalJpaDao().findByTemaTransversal(temaTransversal.getTemaTransversal());
		if (temaTransversalNombre != null && temaTransversalNombre.getIdTemaTransversal() != temaTransversal.getIdTemaTransversal()) {
			throw new ESBException(CodigoError.TEMA_TRANSVERSAL_REPETIDO.getCodigo(), "Ya existe un tema transversal con nombre: " + temaTransversal.getTemaTransversal());
		} else {
			return getTemaTransversalJpaDao().save(temaTransversal);
		}
	}

	@Override
	public void deleteTemaTransversal(Long id) throws ESBException {
		TemaTransversal temaTransversal = getTemaTransversalJpaDao().findOne(id);
		if (temaTransversal != null) {
			getTemaTransversalJpaDao().delete(temaTransversal);
		} else {
			throw new ESBException(CodigoError.TEMA_TRANSVERSAL_INEXISTENTE.getCodigo(), "No se encontro Tema Transversal con id: " + id);
		}		
	}

	@Override
	public TemaTransversal getTemaTransversalPorId(Long id) {
		return getTemaTransversalJpaDao().findOne(id);
	}

	@Override
	public List<TemaTransversal> getTemasTransversales() {
		return getTemaTransversalJpaDao().findAll();
	}
	
	TemaTransversalJpaDao getTemaTransversalJpaDao(){
		return repository.getTemaTransversalJpaDao();
	}
	
	@VisibleForTesting
	public void setTemaTransversalRepository(TemaTransversalRepositoryImpl repo) {
		this.repository = repo;
	}
}
