package ar.gob.buenosaires.dao.jpa.subtipoObra;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class SubtipoObraRepositoryImpl implements SubtipoObraRepository {

	@Autowired
	SubtipoObraJpaDao subtipoObraJpaDao;

	@Override
	public SubtipoObraJpaDao getSubtipoObraJpaDao() {
		return subtipoObraJpaDao;
	}

	@VisibleForTesting
	public void setSubtipoObraJpaDao(SubtipoObraJpaDao jpaDao) {
		this.subtipoObraJpaDao = jpaDao;
	}

}
