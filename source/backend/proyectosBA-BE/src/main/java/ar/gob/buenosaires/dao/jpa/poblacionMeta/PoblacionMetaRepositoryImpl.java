package ar.gob.buenosaires.dao.jpa.poblacionMeta;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PoblacionMetaRepositoryImpl implements PoblacionMetaRepository { 

	@Autowired
	PoblacionMetaJpaDao poblacionMetaJpaDao;

	@Override
	public PoblacionMetaJpaDao getPoblacionMetaJpaDao() {
		return this.poblacionMetaJpaDao;
	}

	@VisibleForTesting
	public void setPoblacionMetaJpaDao(PoblacionMetaJpaDao jpaDao) {
		this.poblacionMetaJpaDao = jpaDao;
	}
}