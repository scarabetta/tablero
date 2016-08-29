package ar.gob.buenosaires.service.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
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

import ar.gob.buenosaires.dao.jpa.indicadorEstrategico.IndicadorEstrategicoJpaDao;
import ar.gob.buenosaires.dao.jpa.indicadorEstrategico.IndicadorEstrategicoRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionJpaDao;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalJpaDao;
import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalRepositoryImpl;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.esb.exception.ESBException;

public class ObjetivoJurisdiccionalServiceImplTest {

	private static final String FAKE_CODIGO = "T.1";

	private static final String FAKE_NOMBRE = "fakeNombre";

	@Mock
	ObjetivoJurisdiccionalJpaDao jpaDao;
	
	@Mock
	IndicadorEstrategicoJpaDao jpaDaoIndicadorEstrategico;

	@Mock
	JurisdiccionJpaDao jpaDaoJurisdiccion;

	@InjectMocks
	@Spy
	ObjetivoJurisdiccionalRepositoryImpl repositorio;

	@InjectMocks
	@Spy
	JurisdiccionRepositoryImpl repositorioJurisdiccion;
	
	@InjectMocks
	@Spy
	IndicadorEstrategicoRepositoryImpl repositorioIndicadorEstrategico;

	@InjectMocks
	ObjetivoJurisdiccionalServiceImpl service;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		repositorio.setObjetivoJurisdiccionalJpaDao(jpaDao);
		service = new ObjetivoJurisdiccionalServiceImpl();
		service.setObjetivoJurisdiccionalRepository(repositorio);

		repositorioJurisdiccion.setJurisdiccionJpaDao(jpaDaoJurisdiccion);
		service.setRepositorioJurisdiccion(repositorioJurisdiccion);
		
		repositorioIndicadorEstrategico.setIndicadorEstrategicoJpaDao(jpaDaoIndicadorEstrategico);
		service.setRepositorioIndicadorEstrategico(repositorioIndicadorEstrategico);
	}

	private ObjetivoJurisdiccional createFakeObjetivoJurisdiccional() {
		ObjetivoJurisdiccional fakeObjetivoJurisdiccional = new ObjetivoJurisdiccional();
		fakeObjetivoJurisdiccional.setNombre(FAKE_NOMBRE);
		fakeObjetivoJurisdiccional.setIdJurisdiccionAux(new Long(1));
		return fakeObjetivoJurisdiccional;
	}

	private Jurisdiccion createFakeJurisdiccion() {
		Jurisdiccion fakeJuris = new Jurisdiccion();
		fakeJuris.setNombre(FAKE_NOMBRE);
		fakeJuris.setCodigo(FAKE_CODIGO);
		return fakeJuris;
	}

	@Test
	public void createObjetivoJurisdiccional() throws ESBException {
		ObjetivoJurisdiccional fakeObjetivoJurisdiccional = createFakeObjetivoJurisdiccional();

		when(
				repositorio.getObjetivoJurisdiccionalJpaDao().save(
						any(ObjetivoJurisdiccional.class))).thenReturn(
				fakeObjetivoJurisdiccional);

		when(repositorioJurisdiccion.getJurisdiccionJpaDao().findOne(anyLong()))
				.thenReturn(createFakeJurisdiccion());

		ObjetivoJurisdiccional response = service
				.createObjetivoJurisdiccional(fakeObjetivoJurisdiccional);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}

	// @Test
	// public void deleteObjetivoJurisdiccional() {
	// throw new RuntimeException("Test not implemented");
	// }

	@Test
	public void getObjetivoJurisdiccionalDAO() {
		ObjetivoJurisdiccionalJpaDao jpaDao = service.getObjetivoJurisdiccionalDAO();
		assertThat(jpaDao).isNotNull();
	}

	@Test
	public void getObjetivoJurisdiccionalPorCodigo() {
		ObjetivoJurisdiccional fakeObjetivoJurisdiccional = createFakeObjetivoJurisdiccional();

		when(repositorio.getObjetivoJurisdiccionalJpaDao().findByCodigo(anyString())).thenReturn(fakeObjetivoJurisdiccional);
		ObjetivoJurisdiccional response = service.getObjetivoJurisdiccionalPorCodigo("1");
		assertThat(response).isNotNull();
	}

	@Test
	public void getObjetivoJurisdiccionalPorId() {
		ObjetivoJurisdiccional fakeObjetivoJurisdiccional = createFakeObjetivoJurisdiccional();

		when(repositorio.getObjetivoJurisdiccionalJpaDao().findOne(anyLong())).thenReturn(fakeObjetivoJurisdiccional);

		ObjetivoJurisdiccional response = service.getObjetivoJurisdiccionalPorId(new Long(1));
		assertThat(response).isNotNull();
	}

	@Test
	public void getObjetivoJurisdiccionalPorNombre() {
		ObjetivoJurisdiccional fakeObjetivoJurisdiccional = createFakeObjetivoJurisdiccional();

		when(repositorio.getObjetivoJurisdiccionalJpaDao().findByNombre(anyString())).thenReturn(fakeObjetivoJurisdiccional);

		ObjetivoJurisdiccional response = service.getObjetivoJurisdiccionalPorNombre("1");
		assertThat(response).isNotNull();
	}

	@Test
	public void getObjetivosJurisdiccionales() {
		List<ObjetivoJurisdiccional> fakeObjetivosJurisdiccionales = new ArrayList<ObjetivoJurisdiccional>();
		ObjetivoJurisdiccional fakeObjetivoJurisdiccional = createFakeObjetivoJurisdiccional();
		fakeObjetivosJurisdiccionales.add(fakeObjetivoJurisdiccional);

		when(repositorio.getObjetivoJurisdiccionalJpaDao().findAll()).thenReturn(fakeObjetivosJurisdiccionales);

		List<ObjetivoJurisdiccional> response = service.getObjetivosJurisdiccionales();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}

	@Test
	public void updateObjetivoJurisdiccional() throws ESBException {
		ObjetivoJurisdiccional fakeObjetivoJurisdiccional = createFakeObjetivoJurisdiccional();

		when(repositorio.getObjetivoJurisdiccionalJpaDao().save(any(ObjetivoJurisdiccional.class))).thenReturn(
				fakeObjetivoJurisdiccional);

		when(repositorioJurisdiccion.getJurisdiccionJpaDao().findOne(anyLong())).thenReturn(createFakeJurisdiccion());

		ObjetivoJurisdiccional response = service.updateObjetivoJurisdiccional(fakeObjetivoJurisdiccional);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}
}
