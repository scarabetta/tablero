package ar.gob.buenosaires.dao.jpa.hitoProyecto;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class HitoProyectoRepositoryImpl implements HitoProyectoRepository {

	@Autowired
	HitoProyectoJpaDao hitoProyectoJpaDao;

	@Override
	public HitoProyectoJpaDao getHitoProyectoJpaDao() {
		return hitoProyectoJpaDao;
	}

	@VisibleForTesting
	public void setOHitoProyectoJpaDao(HitoProyectoJpaDao jpaDao) {
		this.hitoProyectoJpaDao = jpaDao;
	}

}
