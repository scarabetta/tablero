package ar.gob.buenosaires.dao.jpa.comuna;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ComunaRepositoryImplTest {

	ComunaJpaDao comunaJpaDao;
	ComunaRepositoryImpl repository;

	@BeforeMethod
	public void beforeMethod() {
		comunaJpaDao = mock(ComunaJpaDao.class);
		repository = new ComunaRepositoryImpl();
		repository.setComunaJpaDao(comunaJpaDao);
	}

	@Test
	public void getComunaJpaDao() {
		ComunaJpaDao jpaDao = repository.getComunaJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
