package ar.gob.buenosaires.dao.jpa.obra;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class ObraRepositoryImpl implements ObraRepository {

	@Autowired
	ObraJpaDao obraJpaDao;

	@Override
	public ObraJpaDao getObraJpaDao() {
		return obraJpaDao;
	}

	@VisibleForTesting
	public void setObraJpaDao(ObraJpaDao jpaDao) {
		this.obraJpaDao = jpaDao;
	}

}
