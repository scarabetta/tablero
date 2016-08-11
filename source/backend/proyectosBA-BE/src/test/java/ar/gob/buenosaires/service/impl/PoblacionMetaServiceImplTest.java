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

import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionJpaDao;
import ar.gob.buenosaires.dao.jpa.poblacionMeta.PoblacionMetaJpaDao;
import ar.gob.buenosaires.dao.jpa.poblacionMeta.PoblacionMetaRepositoryImpl;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.PoblacionMeta;

public class PoblacionMetaServiceImplTest {
	private static final String FAKE_NOMBRE = "fakeNombre";

	@Mock
	PoblacionMetaJpaDao jpaDao;

	@InjectMocks
	@Spy
	PoblacionMetaRepositoryImpl repositorio;

	@InjectMocks
	PoblacionMetaServiceImpl service;
	
	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		repositorio.setPoblacionMetaJpaDao(jpaDao);
		service = new PoblacionMetaServiceImpl();
		service.setPoblacionMetaRepository(repositorio);
	}
	
	private PoblacionMeta createFakePoblacionMeta() {
		PoblacionMeta fakePoblacionMeta = new PoblacionMeta();
		fakePoblacionMeta.setNombre(FAKE_NOMBRE);
		return fakePoblacionMeta;
	}

	@Test
	public void getPoblacionMetaDAO() {
		PoblacionMetaJpaDao jpaDao = service.getPoblacionMetaDAO();
		assertThat(jpaDao).isNotNull();
	}

	@Test
	public void getPoblacionesMeta() {
		List<PoblacionMeta> fakePoblacionesMeta = new ArrayList<PoblacionMeta>();
		PoblacionMeta fakePoblacionMeta = createFakePoblacionMeta();
		fakePoblacionesMeta.add(fakePoblacionMeta);

		when(repositorio.getPoblacionMetaJpaDao().findAll()).thenReturn(fakePoblacionesMeta);

		List<PoblacionMeta> response = service.getPoblacionesMeta();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}
}
