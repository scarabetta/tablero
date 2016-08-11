package ar.gob.buenosaires.dao.jpa.objetivoOperativo;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ObjetivoOperativoRepositoryImplTest {
	
	ObjetivoOperativoJpaDao objetivoOperativoJpaDao;
	ObjetivoOperativoRepositoryImpl repository;

	@BeforeMethod
	public void beforeMethod() {
		objetivoOperativoJpaDao = mock(ObjetivoOperativoJpaDao.class);
		repository = new ObjetivoOperativoRepositoryImpl();
		repository.setObjetivoOperativoJpaDao(objetivoOperativoJpaDao);
	}

	@Test
	public void getObjetivoOperativoJpaDao() {
		ObjetivoOperativoJpaDao jpaDao = repository.getObjetivoOperativoJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
