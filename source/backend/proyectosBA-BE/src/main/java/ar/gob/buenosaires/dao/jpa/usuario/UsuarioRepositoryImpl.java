package ar.gob.buenosaires.dao.jpa.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

	@Autowired
	UsuarioJpaDao usuarioJpaDao;
	
	@Override
	public UsuarioJpaDao getUsuarioJpaDao() {
		return usuarioJpaDao;
	}

	public void setUsuarioJpaDao(UsuarioJpaDao jpaDao) {
		this.usuarioJpaDao = jpaDao;
	}

}
