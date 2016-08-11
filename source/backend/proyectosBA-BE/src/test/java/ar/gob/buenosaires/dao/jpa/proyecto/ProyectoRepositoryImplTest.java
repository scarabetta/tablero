package ar.gob.buenosaires.dao.jpa.proyecto;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class ProyectoRepositoryImplTest {

	ProyectoJpaDao proyectoJpaDao;
	ProyectoRepositoryImpl repository;

	@BeforeMethod
	public void beforeMethod() {
		proyectoJpaDao = mock(ProyectoJpaDao.class);
		repository = new ProyectoRepositoryImpl();
		repository.setProyectoJpaDao(proyectoJpaDao);
	}

	@Test
	public void getProyectoJpaDao() {
		ProyectoJpaDao jpaDao = repository.getProyectoJpaDao();
		assertThat(jpaDao).isNotNull();
	}
}
