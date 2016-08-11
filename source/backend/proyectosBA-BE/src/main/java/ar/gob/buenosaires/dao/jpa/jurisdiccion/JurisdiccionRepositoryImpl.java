package ar.gob.buenosaires.dao.jpa.jurisdiccion;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class JurisdiccionRepositoryImpl implements JurisdiccionRepository { 

	@Autowired
	JurisdiccionJpaDao jurisdiccionJpaDao;
	
	@Override
	public JurisdiccionJpaDao getJurisdiccionJpaDao() {
		return this.jurisdiccionJpaDao;
	}

	@VisibleForTesting
	public void setJurisdiccionJpaDao(JurisdiccionJpaDao jpaDao) {
		this.jurisdiccionJpaDao = jpaDao;
	}
}
