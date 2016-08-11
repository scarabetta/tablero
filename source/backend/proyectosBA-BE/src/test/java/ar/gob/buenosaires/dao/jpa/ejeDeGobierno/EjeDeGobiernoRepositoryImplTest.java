package ar.gob.buenosaires.dao.jpa.ejeDeGobierno;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class EjeDeGobiernoRepositoryImplTest {
	
	EjeDeGobiernoRepositoryImpl repository;
	EjeDeGobiernoJpaDao jpaDao;

	@BeforeMethod
	public void beforeMethod() {
		jpaDao = mock(EjeDeGobiernoJpaDao.class);
		repository = new EjeDeGobiernoRepositoryImpl();
		repository.setEjeDeGobiernoJpaDao(jpaDao);
	}

	@Test
	public void getEjeDeGobiernoJpaDao() {
		EjeDeGobiernoJpaDao jpaDao = repository.getEjeDeGobiernoJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
