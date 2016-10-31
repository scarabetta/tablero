package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.tipoObra.TipoObraJpaDao;
import ar.gob.buenosaires.dao.jpa.tipoObra.TipoObraRepository;
import ar.gob.buenosaires.dao.jpa.tipoObra.TipoObraRepositoryImpl;
import ar.gob.buenosaires.domain.TipoObra;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.TipoObraService;

@Service
public class TipoObraServiceImpl implements TipoObraService {
	
	@Autowired
	private TipoObraRepository repository;

	@Override
	public TipoObra createTipoObra(TipoObra tipoObra) {
		return getTipoObraJpaDao().save(tipoObra);
	}

	@Override
	public TipoObra updateTipoObra(TipoObra tipoObra) {
		return getTipoObraJpaDao().save(tipoObra);
	}

	@Override
	public void deleteTipoObra(Long id) throws ESBException {
		TipoObra tipoObra = getTipoObraJpaDao().findOne(id);
		if (tipoObra != null) {
			getTipoObraJpaDao().delete(tipoObra);
		} else {
			throw new ESBException(CodigoError.TIPO_OBRA_INEXISTENTE.getCodigo(), "No se encontro el Tipo de Obra con id: " + id);
		}	
	}

	@Override
	public TipoObra getTipoObraPorId(Long id) {
		return getTipoObraJpaDao().findOne(id);
	}

	@Override
	public List<TipoObra> getTipoObras() {
		return getTipoObraJpaDao().findAll();
	}

	TipoObraJpaDao getTipoObraJpaDao(){
		return repository.getTipoObraJpaDao();
	}
	
	@VisibleForTesting
	public void setTipoObraRepository(TipoObraRepositoryImpl repo) {
		this.repository = repo;
	}
}
