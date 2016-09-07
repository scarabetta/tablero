package ar.gob.buenosaires.dao.jpa.temaTransversal;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TemaTransversalRepositoryImpl implements TemaTransversalRepository { 

	@Autowired
	TemaTransversalJpaDao temaTransversalJpaDao;
	
	@Override
	public TemaTransversalJpaDao getTemaTransversalJpaDao() {
		return this.temaTransversalJpaDao;
	}

	@VisibleForTesting
	public void setTemaTransversalJpaDao(TemaTransversalJpaDao jpaDao) {
		this.temaTransversalJpaDao = jpaDao;
	}
}
