package ar.gob.buenosaires.dao.jpa.indicadorEstrategico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class IndicadorEstrategicoRepositoryImpl implements IndicadorEstrategicoRepository {

	@Autowired
	IndicadorEstrategicoJpaDao indicadorEstrategicoJpaDao;
	
	@Override
	public IndicadorEstrategicoJpaDao getIndicadorEstrategicoJpaDao() {
		return indicadorEstrategicoJpaDao;
	}

	public void setIndicadorEstrategicoJpaDao(IndicadorEstrategicoJpaDao jpaDao) {
		this.indicadorEstrategicoJpaDao = jpaDao;
	}

}
