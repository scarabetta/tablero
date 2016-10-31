package ar.gob.buenosaires.dao.jpa.tipoObra;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TipoObraRepositoryImpl implements TipoObraRepository {

	@Autowired
	TipoObraJpaDao tipoObraJpaDao;

	@Override
	public TipoObraJpaDao getTipoObraJpaDao() {
		return tipoObraJpaDao;
	}

	@VisibleForTesting
	public void setTipoObraJpaDao(TipoObraJpaDao jpaDao) {
		this.tipoObraJpaDao = jpaDao;
	}

}
