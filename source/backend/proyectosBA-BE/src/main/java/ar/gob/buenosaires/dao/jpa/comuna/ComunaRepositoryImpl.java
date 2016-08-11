package ar.gob.buenosaires.dao.jpa.comuna;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComunaRepositoryImpl implements ComunaRepository {
	
	@Autowired
	ComunaJpaDao comunaJpaDao;
	
	@Override
	public ComunaJpaDao getComunaJpaDao() {
		return this.comunaJpaDao;
	}
	
	@VisibleForTesting
	public void setComunaJpaDao(ComunaJpaDao jpaDao){
		this.comunaJpaDao = jpaDao;
	}
}
