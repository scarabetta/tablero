package ar.gob.buenosaires.dao.jpa.jurisdiccion;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class JurisdiccionRepositoryImplTest {
	
	JurisdiccionRepositoryImpl repository;
	JurisdiccionJpaDao jpaDao;

	@BeforeMethod
	public void beforeMethod() {
		jpaDao = mock(JurisdiccionJpaDao.class);
		repository = new JurisdiccionRepositoryImpl();
		repository.setJurisdiccionJpaDao(jpaDao);
	}

	@Test
	public void getJurisdiccionJpaDao() {
		JurisdiccionJpaDao jpaDao = repository.getJurisdiccionJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
