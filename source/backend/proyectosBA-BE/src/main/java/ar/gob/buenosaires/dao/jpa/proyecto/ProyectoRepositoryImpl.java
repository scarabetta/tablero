package ar.gob.buenosaires.dao.jpa.proyecto;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProyectoRepositoryImpl implements ProyectoRepository { 

	@Autowired
	ProyectoJpaDao proyectoJpaDao;
	
	@Override
	public ProyectoJpaDao getProyectoJpaDao() {
		return this.proyectoJpaDao;
	}

	@VisibleForTesting
	public void setProyectoJpaDao(ProyectoJpaDao jpaDao) {
		this.proyectoJpaDao = jpaDao;
	}
}
