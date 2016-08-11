package ar.gob.buenosaires.dao.jpa.poblacionMeta;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class PoblacionMetaRepositoryImplTest {

	PoblacionMetaJpaDao poblacionMetaJpaDao;
	PoblacionMetaRepositoryImpl repository;

	@BeforeMethod
	public void beforeMethod() {
		poblacionMetaJpaDao = mock(PoblacionMetaJpaDao.class);
		repository = new PoblacionMetaRepositoryImpl();
		repository.setPoblacionMetaJpaDao(poblacionMetaJpaDao);
	}

	@Test
	public void getPoblacionMetaJpaDao() {
		PoblacionMetaJpaDao jpaDao = repository.getPoblacionMetaJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
