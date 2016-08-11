package ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ObjetivoJurisdiccionalRepositoryImplTest {
	
	ObjetivoJurisdiccionalJpaDao objetivoJurisdiccionalJpaDao;
	ObjetivoJurisdiccionalRepositoryImpl repository;

	@BeforeMethod
	public void beforeMethod() {
		objetivoJurisdiccionalJpaDao = mock(ObjetivoJurisdiccionalJpaDao.class);
		repository = new ObjetivoJurisdiccionalRepositoryImpl();
		repository.setObjetivoJurisdiccionalJpaDao(objetivoJurisdiccionalJpaDao);
	}

	@Test
	public void getObjetivoJurisdiccionalJpaDao() {
		ObjetivoJurisdiccionalJpaDao jpaDao = repository.getObjetivoJurisdiccionalJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
