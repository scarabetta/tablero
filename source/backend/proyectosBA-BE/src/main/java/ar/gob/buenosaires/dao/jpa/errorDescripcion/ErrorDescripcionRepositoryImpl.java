package ar.gob.buenosaires.dao.jpa.errorDescripcion;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ErrorDescripcionRepositoryImpl implements ErrorDescripcionRepository { 

	@Autowired
	ErrorDescripcionJpaDao errorDescripcionJpaDao;
	
	@Override
	public ErrorDescripcionJpaDao getErrorDescripcionJpaDao() {
		return this.errorDescripcionJpaDao;
	}

	@VisibleForTesting
	public void setErrorDescripcionJpaDao(ErrorDescripcionJpaDao jpaDao) {
		this.errorDescripcionJpaDao = jpaDao;
	}
}
