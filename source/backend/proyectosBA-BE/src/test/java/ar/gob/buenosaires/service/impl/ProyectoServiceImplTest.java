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

import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoJpaDao;
import ar.gob.buenosaires.dao.jpa.objetivoOperativo.ObjetivoOperativoRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.presupuestoPorAnio.PresupuestoPorAnioJpaDao;
import ar.gob.buenosaires.dao.jpa.presupuestoPorAnio.PresupuestoPorAnioRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoJpaDao;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepositoryImpl;
import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.geocoder.adapter.impl.GeoCoderAdapterImpl;
import ar.gob.buenosaires.geocoder.adapter.response.DireccionNormalizada;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.service.impl.GeoCoderServiceImpl;

public class ProyectoServiceImplTest {

	private static final String FAKE_NOMBRE = "fakeNombre";

	@Mock
	ProyectoJpaDao jpaDao;

	@Mock
	ObjetivoOperativoJpaDao jpaDaoObjetivoOperativo;
	
	@Mock
	PresupuestoPorAnioJpaDao presupuestoPorAnioJpaDao;

	@InjectMocks
	@Spy
	ProyectoRepositoryImpl repositorio;

	@InjectMocks
	@Spy
	ObjetivoOperativoRepositoryImpl repositorioObjetivoOperativo;
	
	@InjectMocks
	@Spy
	PresupuestoPorAnioRepositoryImpl repositorioPresupuestoPorAnio;
	
	@InjectMocks
	ProyectoServiceImpl service;

	@InjectMocks
	GeoCoderServiceImpl geoCoderService;
	
	@InjectMocks
	@Spy
	GeoCoderAdapterImpl geoCoderAdapter;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		repositorio.setProyectoJpaDao(jpaDao);
		service = new ProyectoServiceImpl();
		service.setProyectoRepository(repositorio);
		geoCoderService = new GeoCoderServiceImpl();
		geoCoderService.setGeoCoderAdapter(new GeoCoderAdapterImpl());

		repositorioObjetivoOperativo.setObjetivoOperativoJpaDao(jpaDaoObjetivoOperativo);
		repositorioPresupuestoPorAnio.setPresupuestoPorAnioJpaDao(presupuestoPorAnioJpaDao);
		
		service.setRepositorioObjetivoOperativo(repositorioObjetivoOperativo);
		service.setRepositorioPresupuestoPorAnio(repositorioPresupuestoPorAnio);
		service.setGeoCoderService(geoCoderService);
	}

	private Proyecto createFakeProyecto() {
		Proyecto fakeProyecto = new Proyecto();
		fakeProyecto.setNombre(FAKE_NOMBRE);
		fakeProyecto.setIdObjetivoOperativo2(new Long(1));
		return fakeProyecto;
	}

	private ObjetivoOperativo createFakeObjetivoOperativo() {
		ObjetivoOperativo fakeObjetivoOperativo = new ObjetivoOperativo();
		fakeObjetivoOperativo.setNombre(FAKE_NOMBRE);
		return fakeObjetivoOperativo;
	}
	private GeoCoderResponse createFakeGeoCoderResponse() {
		GeoCoderResponse response = new GeoCoderResponse();
		response.setDireccionesNormalizadas(new ArrayList<DireccionNormalizada>());
		return response;
	}

	@Test
	public void createProyecto() throws ESBException {
		Proyecto fakeProyecto = createFakeProyecto();

		when(repositorio.getProyectoJpaDao().save(any(Proyecto.class))).thenReturn(fakeProyecto);

		when(repositorioObjetivoOperativo.getObjetivoOperativoJpaDao().findOne(anyLong())).thenReturn(
				createFakeObjetivoOperativo());
		
		when(geoCoderAdapter.normalizarYGeoCodificar(anyString())).thenReturn(createFakeGeoCoderResponse());

		Proyecto response = service.createProyecto(fakeProyecto);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}

	//
	// @Test
	// public void deleteProyecto() {
	// throw new RuntimeException("Test not implemented");
	// }
	//
	@Test
	public void getProyectoDAO() {
		ProyectoJpaDao jpaDao = service.getProyectoDAO();
		assertThat(jpaDao).isNotNull();
	}

	@Test
	public void getProyectoPorCodigo() {
		Proyecto fakeProyecto = createFakeProyecto();

		when(repositorio.getProyectoJpaDao().findByCodigo(anyString())).thenReturn(fakeProyecto);
		Proyecto response = service.getProyectoPorCodigo("1");
		assertThat(response).isNotNull();
	}

	@Test
	public void getProyectoPorId() {
		Proyecto fakeProyecto = createFakeProyecto();

		when(repositorio.getProyectoJpaDao().findOne(anyLong())).thenReturn(fakeProyecto);

		Proyecto response = service.getProyectoPorId(new Long(1));
		assertThat(response).isNotNull();
	}

	@Test
	public void getProyectoPorNombre() {
		Proyecto fakeProyecto = createFakeProyecto();

		when(repositorio.getProyectoJpaDao().findByNombre(anyString())).thenReturn(fakeProyecto);

		Proyecto response = service.getProyectoPorNombre("1");
		assertThat(response).isNotNull();
	}

	@Test
	public void getProyectos() {
		List<Proyecto> fakeProyectos = new ArrayList<Proyecto>();
		Proyecto fakeProyecto = new Proyecto();
		fakeProyecto.setIdProyecto(new Long(1));
		fakeProyectos.add(fakeProyecto);

		when(repositorio.getProyectoJpaDao().findAll()).thenReturn(fakeProyectos);

		List<Proyecto> response = service.getProyectos();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}

	// @Test
	// public void updatePresupuestosPorAnio() {
	// throw new RuntimeException("Test not implemented");
	// }

	@Test
	public void updateProyecto() throws ESBException {
		Proyecto fakeProyecto = createFakeProyecto();

		when(repositorio.getProyectoJpaDao().save(any(Proyecto.class))).thenReturn(fakeProyecto);

		when(repositorioObjetivoOperativo.getObjetivoOperativoJpaDao().findOne(anyLong())).thenReturn(
				createFakeObjetivoOperativo());

		Proyecto response = service.updateProyecto(fakeProyecto);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}
	
	@Test
	public void getProyectoPorEstado() {
		List<Proyecto> fakeProyectos = new ArrayList<Proyecto>();
		Proyecto fakeProyecto = new Proyecto();
		fakeProyecto.setIdProyecto(new Long(1));
		fakeProyectos.add(fakeProyecto);

		when(repositorio.getProyectoJpaDao().findByEstado(anyString())).thenReturn(fakeProyectos);

		List<Proyecto> response = service.getProyectosPorEstado(EstadoProyecto.VERIFICADO.getName());
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}
}
