package ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ObjetivoJurisdiccionalRepositoryImpl implements ObjetivoJurisdiccionalRepository {

	@Autowired
	ObjetivoJurisdiccionalJpaDao objetivoJurisdiccionalJpaDao;

	@Override
	public ObjetivoJurisdiccionalJpaDao getObjetivoJurisdiccionalJpaDao() {
		return this.objetivoJurisdiccionalJpaDao;
	}

	@VisibleForTesting
	public void setObjetivoJurisdiccionalJpaDao(ObjetivoJurisdiccionalJpaDao jpaDao) {
		this.objetivoJurisdiccionalJpaDao = jpaDao;
	}
}
