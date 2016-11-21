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
import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.geocoder.adapter.response.DireccionNormalizada;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.service.GeoCoderService;
import ar.gob.buenosaires.geocoder.service.mock.GeoCoderserviceMock;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasJpaDao;
import ar.gob.buenosaires.otrasEtiquetas.OtrasEtiquetasRepositoryImpl;

public class ProyectoServiceImplTest {

	private static final String FAKE_NOMBRE = "fakeNombre";
	private static final String SECRETARIA = "Secretaria";

	@Mock
	ProyectoJpaDao jpaDao;

	@Mock
	ObjetivoOperativoJpaDao jpaDaoObjetivoOperativo;
	
	@Mock
	PresupuestoPorAnioJpaDao presupuestoPorAnioJpaDao;

	@Mock
	 OtrasEtiquetasJpaDao OtrasEtiquetasJpaDao;

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
	@Spy
	OtrasEtiquetasRepositoryImpl repositorioOtrasEtiquetas;
	
	@InjectMocks
	ProyectoServiceImpl service;

//	@InjectMocks
	GeoCoderService geoCoderService;
	
//	@InjectMocks
//	@Spy
//	GeoCoderAdapterImpl geoCoderAdapter;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		repositorio.setProyectoJpaDao(jpaDao);
		service = new ProyectoServiceImpl();
		service.setProyectoRepository(repositorio);
		geoCoderService = new GeoCoderserviceMock();
//		geoCoderService.setGeoCoderAdapter(new GeoCoderAdapterImpl());

		repositorioObjetivoOperativo.setObjetivoOperativoJpaDao(jpaDaoObjetivoOperativo);
		repositorioPresupuestoPorAnio.setPresupuestoPorAnioJpaDao(presupuestoPorAnioJpaDao);
		repositorioOtrasEtiquetas.setOtrasEtiquetasJpaDao(OtrasEtiquetasJpaDao);
		
		service.setRepositorioObjetivoOperativo(repositorioObjetivoOperativo);
		service.setRepositorioPresupuestoPorAnio(repositorioPresupuestoPorAnio);
		service.setRepositorioOtrasEtiquetas(repositorioOtrasEtiquetas);
		service.setGeoCoderService(geoCoderService);
	}

	private Proyecto createFakeProyecto() {
		Proyecto fakeProyecto = new Proyecto();
		fakeProyecto.setNombre(FAKE_NOMBRE);
		fakeProyecto.setIdObjetivoOperativo2(new Long(1));
		return fakeProyecto;
	}
	
	private Usuario createFakeUsuario(boolean perfilSecretaria) {
		Usuario fakeUsuario = new Usuario();
		fakeUsuario.setNombre(FAKE_NOMBRE);
		if (perfilSecretaria) {
			List<Rol> roles = new ArrayList<>();
			Rol rol = new Rol();
			rol.setNombre(SECRETARIA);
			roles.add(rol);
			fakeUsuario.setRoles(roles);			
		} else {
			fakeUsuario.setRoles(new ArrayList<>());
		}
		
		return fakeUsuario;
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
		
//		when(geoCoderAdapter.normalizarYGeoCodificar(anyString())).thenReturn(createFakeGeoCoderResponse());

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
		Usuario fakeUsuario = createFakeUsuario(true);

		when(repositorio.getProyectoJpaDao().save(any(Proyecto.class))).thenReturn(fakeProyecto);

		when(repositorioObjetivoOperativo.getObjetivoOperativoJpaDao().findOne(anyLong())).thenReturn(
				createFakeObjetivoOperativo());

		Proyecto response = service.updateProyecto(fakeProyecto, fakeUsuario);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}
	
	@Test
	public void updateProyectoVerificadoConOperadorJurisdiccionVuelveApresentado() throws ESBException {
		Proyecto fakeProyecto = createFakeProyecto();
		fakeProyecto.setVerificado(true);
		fakeProyecto.setEstado(EstadoProyecto.VERIFICADO.getName());
		boolean perfilSecretaria = false;
		Usuario fakeUsuario = createFakeUsuario(perfilSecretaria);

		when(repositorio.getProyectoJpaDao().save(any(Proyecto.class))).thenReturn(fakeProyecto);

		when(repositorioObjetivoOperativo.getObjetivoOperativoJpaDao().findOne(anyLong())).thenReturn(
				createFakeObjetivoOperativo());

		Proyecto response = service.updateProyecto(fakeProyecto, fakeUsuario);
		assertThat(response.getEstado()).isEqualTo(EstadoProyecto.PRESENTADO.getName());
	}
	
	@Test
	public void updateProyectoVerificadoConSecretariaSigueVerificado() throws ESBException {
		Proyecto fakeProyecto = createFakeProyecto();
		boolean perfilSecretaria = true;
		fakeProyecto.setVerificado(perfilSecretaria);
		fakeProyecto.setEstado(EstadoProyecto.VERIFICADO.getName());
		Usuario fakeUsuario = createFakeUsuario(perfilSecretaria);
		
		when(repositorio.getProyectoJpaDao().save(any(Proyecto.class))).thenReturn(fakeProyecto);
		
		when(repositorioObjetivoOperativo.getObjetivoOperativoJpaDao().findOne(anyLong())).thenReturn(
				createFakeObjetivoOperativo());
		
		Proyecto response = service.updateProyecto(fakeProyecto, fakeUsuario);
		assertThat(response.getEstado()).isEqualTo(EstadoProyecto.VERIFICADO.getName());
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
