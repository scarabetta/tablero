package ar.gob.buenosaires.dao.jpa.objetivoOperativo;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ObjetivoOperativoRepositoryImpl implements ObjetivoOperativoRepository { 

	@Autowired
	ObjetivoOperativoJpaDao objetivoOperativoJpaDao;
	
	@Override
	public ObjetivoOperativoJpaDao getObjetivoOperativoJpaDao() {
		return this.objetivoOperativoJpaDao;
	}

	@VisibleForTesting
	public void setObjetivoOperativoJpaDao(ObjetivoOperativoJpaDao jpaDao) {
		this.objetivoOperativoJpaDao = jpaDao;
	}
}
