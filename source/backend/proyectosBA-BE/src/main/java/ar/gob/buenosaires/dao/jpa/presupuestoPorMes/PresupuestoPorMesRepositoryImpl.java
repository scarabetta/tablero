package ar.gob.buenosaires.dao.jpa.presupuestoPorMes;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class PresupuestoPorMesRepositoryImpl implements PresupuestoPorMesRepository {

	@Autowired
	PresupuestoPorMesJpaDao presupuestoPorMesJpaDao;

	@Override
	public PresupuestoPorMesJpaDao getPresupuestoPorMesJpaDao() {
		return presupuestoPorMesJpaDao;
	}

	@VisibleForTesting
	public void setPresupuestoPorMesJpaDao(PresupuestoPorMesJpaDao jpaDao) {
		this.presupuestoPorMesJpaDao = jpaDao;
	}

}
