package ar.gob.buenosaires.otrasEtiquetas;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OtrasEtiquetasRepositoryImpl implements OtrasEtiquetasRepository { 

	@Autowired
	OtrasEtiquetasJpaDao otrasEtiquetasJpaDao;
	
	@Override
	public OtrasEtiquetasJpaDao getOtrasEtiquetasJpaDao() {
		return this.otrasEtiquetasJpaDao;
	}

	@VisibleForTesting
	public void setOtrasEtiquetasJpaDao(OtrasEtiquetasJpaDao jpaDao) {
		this.otrasEtiquetasJpaDao = jpaDao;
	}
}
