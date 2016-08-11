package ar.gob.buenosaires.dao.jpa.ejeDeGobierno;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class EjeDeGobiernoRepositoryImpl implements EjeDeGobiernoRepository { 

	@Autowired
	EjeDeGobiernoJpaDao ejeDeGobiernoJpaDao;

	@Override
	public EjeDeGobiernoJpaDao getEjeDeGobiernoJpaDao() {
		return this.ejeDeGobiernoJpaDao;
	}

	@VisibleForTesting
	public void setEjeDeGobiernoJpaDao(EjeDeGobiernoJpaDao jpaDao) {
		this.ejeDeGobiernoJpaDao = jpaDao;
	}
}