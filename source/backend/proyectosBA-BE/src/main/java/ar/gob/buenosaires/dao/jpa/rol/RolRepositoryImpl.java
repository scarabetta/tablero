package ar.gob.buenosaires.dao.jpa.rol;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RolRepositoryImpl implements RolRepository {
	
	@Autowired
	RolJpaDao rolJpaDao;

	@Override
	public RolJpaDao getRolJpaDao() {
		return rolJpaDao;
	}

	@VisibleForTesting
	public void setRolJpaDao(RolJpaDao jpaDao) {
		this.rolJpaDao = jpaDao;
	}

}
