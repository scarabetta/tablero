package ar.gob.buenosaires.service.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ar.gob.buenosaires.dao.jpa.comuna.ComunaJpaDao;
import ar.gob.buenosaires.dao.jpa.comuna.ComunaRepositoryImpl;
import ar.gob.buenosaires.domain.Comuna;

public class ComunaServiceImplTest {

	@Mock
	ComunaJpaDao jpaDao;
	
	@InjectMocks @Spy
	ComunaRepositoryImpl repositorio;
	
	@InjectMocks
	ComunaServiceImpl service;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
//		jpaDao = mock(ComunaJpaDao.class);
//		repositorio = mock(ComunaRepositoryImpl.class);
		repositorio.setComunaJpaDao(jpaDao);
		service = new ComunaServiceImpl();
		service.setComunaRepository(repositorio);
	}

	@Test
	public void getComunaDAO() {
		ComunaJpaDao jpaDao = service.getComunaDAO();
		assertThat(jpaDao).isNotNull();
	}

	@Test
	public void getComunas() {
		List<Comuna> fakeComunas = new ArrayList<Comuna>();
		Comuna fakeComuna = new Comuna();
		fakeComunas.add(fakeComuna);

		when(repositorio.getComunaJpaDao().findAll()).thenReturn(fakeComunas);

		List<Comuna> response = service.getComunas();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}
}
