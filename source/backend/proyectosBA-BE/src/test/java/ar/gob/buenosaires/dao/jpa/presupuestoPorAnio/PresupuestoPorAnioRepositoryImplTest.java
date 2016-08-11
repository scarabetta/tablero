package ar.gob.buenosaires.dao.jpa.presupuestoPorAnio;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PresupuestoPorAnioRepositoryImplTest {

	PresupuestoPorAnioJpaDao presupuestoPorAnioJpaDao;
	PresupuestoPorAnioRepositoryImpl repository;

	@BeforeMethod
	public void beforeMethod() {
		presupuestoPorAnioJpaDao = mock(PresupuestoPorAnioJpaDao.class);
		repository = new PresupuestoPorAnioRepositoryImpl();
		repository.setPresupuestoPorAnioJpaDao(presupuestoPorAnioJpaDao);
	}

	@Test
	public void getPresupuestoPorAnioJpaDao() {
		PresupuestoPorAnioJpaDao jpaDao = repository.getPresupuestoPorAnioJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
