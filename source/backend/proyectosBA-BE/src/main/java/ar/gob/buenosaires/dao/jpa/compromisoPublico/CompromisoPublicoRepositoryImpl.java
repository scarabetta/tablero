package ar.gob.buenosaires.dao.jpa.compromisoPublico;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CompromisoPublicoRepositoryImpl implements CompromisoPublicoRepository { 

	@Autowired
	CompromisoPublicoJpaDao compromisoPublicoJpaDao;
	
	@Override
	public CompromisoPublicoJpaDao getCompromisoPublicoJpaDao() {
		return this.compromisoPublicoJpaDao;
	}

	@VisibleForTesting
	public void setCompromisoPublicoJpaDao(CompromisoPublicoJpaDao jpaDao) {
		this.compromisoPublicoJpaDao = jpaDao;
	}
}
