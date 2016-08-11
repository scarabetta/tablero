package ar.gob.buenosaires.service.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ar.gob.buenosaires.dao.jpa.ejeDeGobierno.EjeDeGobiernoJpaDao;
import ar.gob.buenosaires.dao.jpa.ejeDeGobierno.EjeDeGobiernoRepositoryImpl;
import ar.gob.buenosaires.domain.EjeDeGobierno;

public class EjeDeGobiernoServiceImplTest {

	@Mock
	EjeDeGobiernoJpaDao jpaDao;
	
	@InjectMocks @Spy
	EjeDeGobiernoRepositoryImpl repositorio;
	
	@InjectMocks
	EjeDeGobiernoServiceImpl service;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
//		jpaDao = mock(EjeDeGobiernoJpaDao.class);
//		repositorio = mock(EjeDeGobiernoRepositoryImpl.class);
		repositorio.setEjeDeGobiernoJpaDao(jpaDao);
		service = new EjeDeGobiernoServiceImpl();
		service.setEjeDeGobiernoRepository(repositorio);
	}

	@Test
	public void getEjeDeGobiernoDAO() {
		EjeDeGobiernoJpaDao jpaDao = service.getEjeDeGobiernoDAO();
		assertThat(jpaDao).isNotNull();
	}

	@Test
	public void getEjesDeGobierno() {
		List<EjeDeGobierno> fakeEjesDeGobierno = new ArrayList<EjeDeGobierno>();
		EjeDeGobierno fakeEjeDeGobierno = new EjeDeGobierno();
		fakeEjesDeGobierno.add(fakeEjeDeGobierno);

		when(repositorio.getEjeDeGobiernoJpaDao().findAll()).thenReturn(fakeEjesDeGobierno);

		List<EjeDeGobierno> response = service.getEjesDeGobierno();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}

	@Test
	public void getEjeDeGobiernoPorId() {
		EjeDeGobierno fakeEjeDeGobierno = new EjeDeGobierno();

		when(repositorio.getEjeDeGobiernoJpaDao().findOne(anyLong())).thenReturn(fakeEjeDeGobierno);

		EjeDeGobierno response = service.getEjeDeGobiernoPorId(new Long(1));
		assertThat(response).isNotNull();
	}

	@Test
	public void getEjeDeGobiernoPorNombre() {
		EjeDeGobierno fakeEjeDeGobierno = new EjeDeGobierno();

		when(repositorio.getEjeDeGobiernoJpaDao().findByNombre(anyString())).thenReturn(fakeEjeDeGobierno);

		EjeDeGobierno response = service.getEjeDeGobiernoPorNombre("nombre");
		assertThat(response).isNotNull();
	}
}
