package ar.gob.buenosaires.dao.jpa.usuario;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class UsuarioRepositoryImplTest {

	UsuarioJpaDao usuarioJpaDao;
	UsuarioRepositoryImpl repository;

	@BeforeMethod
	public void beforeMethod() {
		usuarioJpaDao = mock(UsuarioJpaDao.class);
		repository = new UsuarioRepositoryImpl();
		repository.setUsuarioJpaDao(usuarioJpaDao);
	}

	@Test
	public void getUsuarioJpaDao() {
		UsuarioJpaDao jpaDao = repository.getUsuarioJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
