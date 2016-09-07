package ar.gob.buenosaires.dao.jpa.area;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaRepositoryImpl implements AreaRepository {
	
	@Autowired
	AreaJpaDao areaJpaDao;
	
	@Override
	public AreaJpaDao getAreaJpaDao() {
		return this.areaJpaDao;
	}
	
	@VisibleForTesting
	public void setAreaJpaDao(AreaJpaDao jpaDao){
		this.areaJpaDao = jpaDao;
	}
}
