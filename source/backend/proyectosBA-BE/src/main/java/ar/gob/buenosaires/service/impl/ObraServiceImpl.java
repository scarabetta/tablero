package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.obra.ObraJpaDao;
import ar.gob.buenosaires.dao.jpa.obra.ObraRepository;
import ar.gob.buenosaires.dao.jpa.obra.ObraRepositoryImpl;
import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ObraService;

@Service
public class ObraServiceImpl implements ObraService {
	
	@Autowired
	private ObraRepository repository;

	@Override
	public Obra createObra(Obra obra) {
		return getObraJpaDao().save(obra);
	}

	@Override
	public Obra updateObra(Obra obra) {
		return getObraJpaDao().save(obra);
	}

	@Override
	public void deleteObra(Long id) throws ESBException {
		Obra rol = getObraJpaDao().findOne(id);
		if (rol != null) {
			getObraJpaDao().delete(rol);
		} else {
			throw new ESBException(CodigoError.OBRA_INEXISTENTE.getCodigo(), "No se encontro la Obra con id: " + id);
		}	
	}

	@Override
	public Obra getObraPorId(Long id) {
		return getObraJpaDao().findOne(id);
	}

	@Override
	public List<Obra> getObras() {
		return getObraJpaDao().findAll();
	}

	ObraJpaDao getObraJpaDao(){
		return repository.getObraJpaDao();
	}
	
	@VisibleForTesting
	public void setObraRepository(ObraRepositoryImpl repo) {
		this.repository = repo;
	}
}
