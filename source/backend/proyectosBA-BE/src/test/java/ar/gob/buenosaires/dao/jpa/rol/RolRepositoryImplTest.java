package ar.gob.buenosaires.dao.jpa.rol;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class RolRepositoryImplTest {

	RolJpaDao rolJpaDao;
	RolRepositoryImpl repository;

	@BeforeMethod
	public void beforeMethod() {
		rolJpaDao = mock(RolJpaDao.class);
		repository = new RolRepositoryImpl();
		repository.setRolJpaDao(rolJpaDao);
	}

	@Test
	public void getRolJpaDao() {
		RolJpaDao jpaDao = repository.getRolJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
