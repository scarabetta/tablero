package ar.gob.buenosaires.dao.jpa.presupuestoPorAnio;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class PresupuestoPorAnioRepositoryImpl implements PresupuestoPorAnioRepository { 

	@Autowired
	PresupuestoPorAnioJpaDao presupuestoPorAnioJpaDao;
	
	@Override
	public PresupuestoPorAnioJpaDao getPresupuestoPorAnioJpaDao() {
		return this.presupuestoPorAnioJpaDao;
	}

	@VisibleForTesting
	public void setPresupuestoPorAnioJpaDao(PresupuestoPorAnioJpaDao jpaDao) {
		this.presupuestoPorAnioJpaDao = jpaDao;
	}
}
