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

import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalJpaDao;
import ar.gob.buenosaires.dao.jpa.objetivoJurisdiccional.ObjetivoJurisdiccionalRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoJpaDao;
import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoRepositoryImpl;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.exception.ESBException;

public class ObjetivoOperativoServiceImplTest {

	private static final String FAKE_CODIGO = "T.1";

	private static final String FAKE_NOMBRE = "fakeNombre";

	@Mock
	ObjetivoOperativoJpaDao jpaDao;

	@Mock
	ObjetivoJurisdiccionalJpaDao jpaDaoObjetivoJurisdiccional;

	@InjectMocks
	@Spy
	ObjetivoOperativoRepositoryImpl repositorio;

	@InjectMocks
	@Spy
	ObjetivoJurisdiccionalRepositoryImpl repositorioObjetivoJurisdiccional;

	@InjectMocks
	ObjetivoOperativoServiceImpl service;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		repositorio.setObjetivoOperativoJpaDao(jpaDao);
		service = new ObjetivoOperativoServiceImpl();
		service.setRepositorioObjetivoOperativo(repositorio);

		repositorioObjetivoJurisdiccional
				.setObjetivoJurisdiccionalJpaDao(jpaDaoObjetivoJurisdiccional);
		service.setObjetivoJurisdiccionalRepository(repositorioObjetivoJurisdiccional);
	}

	private ObjetivoOperativo createFakeObjetivoOperativo() {
		ObjetivoOperativo fakeObjetivoOperativo = new ObjetivoOperativo();
		fakeObjetivoOperativo.setNombre(FAKE_NOMBRE);
		fakeObjetivoOperativo.setIdObjetivoJurisdiccionalAux(new Long(1));
		ObjetivoJurisdiccional fakeOJ = new ObjetivoJurisdiccional();
		fakeOJ.setCodigo("AGC.1");
		fakeObjetivoOperativo.setObjetivoJurisdiccional(fakeOJ);
		return fakeObjetivoOperativo;
	}

	private ObjetivoJurisdiccional createFakeObjetivoJurisdiccional() {
		ObjetivoJurisdiccional fakeObjetivoJurisdiccional = new ObjetivoJurisdiccional();
		fakeObjetivoJurisdiccional.setNombre(FAKE_NOMBRE);
		fakeObjetivoJurisdiccional.setIdJurisdiccionAux(new Long(1));
		fakeObjetivoJurisdiccional.setCodigo(FAKE_CODIGO);
		return fakeObjetivoJurisdiccional;
	}

	@Test
	public void createObjetivoOperativo() throws ESBException {

		ObjetivoOperativo fakeObjetivoOperativo = createFakeObjetivoOperativo();

		when(repositorio.getObjetivoOperativoJpaDao().save(any(ObjetivoOperativo.class))).thenReturn(
				fakeObjetivoOperativo);

		when(repositorioObjetivoJurisdiccional.getObjetivoJurisdiccionalJpaDao().findOne(anyLong()))
				.thenReturn(createFakeObjetivoJurisdiccional());

		ObjetivoOperativo response = service.createObjetivoOperativo(fakeObjetivoOperativo);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);

	}

	// @Test
	// public void deleteObjetivoOperativo() {
	// throw new RuntimeException("Test not implemented");
	// }
	//
	 @Test
	 public void getObjetivoOperativoDAO() {
		 ObjetivoOperativoJpaDao jpaDao = service.getObjetivoOperativoDAO();
		assertThat(jpaDao).isNotNull();
	 }
	
	@Test
	public void getObjetivoOperativoPorCodigo() {
		ObjetivoOperativo fakeObjetivoOperativo = createFakeObjetivoOperativo();

		when(repositorio.getObjetivoOperativoJpaDao().findByCodigo(anyString())).thenReturn(fakeObjetivoOperativo);
		ObjetivoOperativo response = service.getObjetivoOperativoPorCodigo("1");
		assertThat(response).isNotNull();
	}
	
	@Test
	public void getObjetivoOperativoPorId() {
		ObjetivoOperativo fakeObjetivoOperativo = createFakeObjetivoOperativo();

		when(repositorio.getObjetivoOperativoJpaDao().findOne(anyLong())).thenReturn(fakeObjetivoOperativo);

		ObjetivoOperativo response = service.getObjetivoOperativoPorId(new Long(1));
		assertThat(response).isNotNull();
	}
	
	@Test
	public void getObjetivoOperativoPorNombre() {
		ObjetivoOperativo fakeObjetivoOperativo = createFakeObjetivoOperativo();

		when(repositorio.getObjetivoOperativoJpaDao().findByNombre(anyString())).thenReturn(fakeObjetivoOperativo);

		ObjetivoOperativo response = service.getObjetivoOperativoPorNombre("1");
		assertThat(response).isNotNull();
	}
	
	@Test
	public void getObjetivosOperativos() {
		List<ObjetivoOperativo> fakeObjetivosOperativos = new ArrayList<ObjetivoOperativo>();
		ObjetivoOperativo fakeObjetivoOperativo = new ObjetivoOperativo();
		fakeObjetivosOperativos.add(fakeObjetivoOperativo);

		when(repositorio.getObjetivoOperativoJpaDao().findAll()).thenReturn(fakeObjetivosOperativos);

		List<ObjetivoOperativo> response = service.getObjetivosOperativos();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}
	
	@Test
	public void updateObjetivoOperativo() throws ESBException {
		ObjetivoOperativo fakeObjetivoOperativo = createFakeObjetivoOperativo();

		when(repositorio.getObjetivoOperativoJpaDao().save(any(ObjetivoOperativo.class))).thenReturn(
				fakeObjetivoOperativo);

		when(repositorioObjetivoJurisdiccional.getObjetivoJurisdiccionalJpaDao().findOne(anyLong()))
		.thenReturn(createFakeObjetivoJurisdiccional());

		ObjetivoOperativo response = service.updateObjetivoOperativo(fakeObjetivoOperativo);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}
}
