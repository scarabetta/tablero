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

import ar.gob.buenosaires.dao.jpa.area.AreaJpaDao;
import ar.gob.buenosaires.dao.jpa.area.AreaRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionJpaDao;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionRepositoryImpl;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.JurisdiccionResumen;
import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.domain.Usuario;

public class JurisdiccionServiceImplTest {

	private static final String FAKE_NOMBRE = "fakeNombre";
	private static final String SECRETARIA = "Secretaria";

	@Mock
	JurisdiccionJpaDao jpaDao;
	@Mock
	AreaJpaDao jpaDaoArea;

	@InjectMocks
	@Spy
	JurisdiccionRepositoryImpl repositorio;
	@InjectMocks
	@Spy
	AreaRepositoryImpl repositorioArea;

	@InjectMocks
	JurisdiccionServiceImpl service;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		// jpaDao = mock(JurisdiccionJpaDao.class);
		// repositorio = mock(JurisdiccionRepositoryImpl.class);
		repositorio.setJurisdiccionJpaDao(jpaDao);
		service = new JurisdiccionServiceImpl();
		service.setJurisdiccionRepository(repositorio);
		service.setAreaRepository(repositorioArea);
	}
	
	private Jurisdiccion createFakeJurisdiccion() {
		Jurisdiccion fakeJuris = new Jurisdiccion();
		fakeJuris.setNombre(FAKE_NOMBRE);
		return fakeJuris;
	}
	
	private Usuario createFakeUsuario() {
		Usuario user = new Usuario();
		user.setIdUsuario(new Long(1));
		List<Rol> roles = new ArrayList<>();
		Rol rol = new Rol();
		rol.setNombre(SECRETARIA);
		roles.add(rol);
		user.setRoles(roles);
		return user;
	}

	@Test
	public void createJurisdiccion() {
		Jurisdiccion fakeJuris = createFakeJurisdiccion();

		when(repositorio.getJurisdiccionJpaDao().save(any(Jurisdiccion.class)))
				.thenReturn(fakeJuris);

		Jurisdiccion response = service.createJurisdiccion(fakeJuris);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}

	// @Test
	// public void deleteJurisdiccion() {
	// Jurisdiccion fakeJurisdiccion = createFakeJurisdiccion();
	//
	// doNothing().when(repositorio.getJurisdiccionJpaDao().delete(any(Jurisdiccion.class));
	// service.deleteJurisdiccion(fakeJurisdiccion);
	// verify(repositorio.getJurisdiccionJpaDao(),
	// times(1)).delete(fakeJurisdiccion);
	// }

	@Test
	public void getJurisdiccionDAO() {
		JurisdiccionJpaDao jpaDao = service.getJurisdiccionDAO();
		assertThat(jpaDao).isNotNull();
	}

	@Test
	public void getJurisdiccionPorCodigo() {
		Jurisdiccion fakeJurisdiccion = createFakeJurisdiccion();

		when(repositorio.getJurisdiccionJpaDao().findByCodigo(anyString()))
				.thenReturn(fakeJurisdiccion);

		Jurisdiccion response = service.getJurisdiccionPorCodigo("1");
		assertThat(response).isNotNull();
	}

	@Test
	public void getJurisdiccionPorId() {
		Jurisdiccion fakeJurisdiccion = createFakeJurisdiccion();

		when(repositorio.getJurisdiccionJpaDao().findOne(anyLong()))
				.thenReturn(fakeJurisdiccion);

		Jurisdiccion response = service.getJurisdiccionPorId(new Long(1));
		assertThat(response).isNotNull();
	}

	@Test
	public void getJurisdiccionPorNombre() {
		Jurisdiccion fakeJurisdiccion = createFakeJurisdiccion();

		when(repositorio.getJurisdiccionJpaDao().findByNombre(anyString()))
				.thenReturn(fakeJurisdiccion);

		Jurisdiccion response = service.getJurisdiccionPorNombre("1");
		assertThat(response).isNotNull();
	}

	@Test
	public void getJurisdicciones() {
		List<Jurisdiccion> fakeJurisdicciones = new ArrayList<Jurisdiccion>();
		Jurisdiccion fakeJurisdiccion = new Jurisdiccion();
		fakeJurisdicciones.add(fakeJurisdiccion);

		when(repositorio.getJurisdiccionJpaDao().findAll()).thenReturn(fakeJurisdicciones);

		List<Jurisdiccion> response = service.getJurisdicciones();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}
	
	@Test
	public void getJurisdiccionesResumen() {
		List<JurisdiccionResumen> fakeResumenJurisdicciones = new ArrayList<JurisdiccionResumen>();
		JurisdiccionResumen fakeJurisdiccionResumen = new JurisdiccionResumen();
		fakeResumenJurisdicciones.add(fakeJurisdiccionResumen);
		Usuario user = createFakeUsuario();
		
		
		when(repositorio.getJurisdiccionJpaDao().findAllResumen()).thenReturn(fakeResumenJurisdicciones);
		
		List<JurisdiccionResumen> response = service.getJurisdiccionesResumen(user);
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}

	@Test
	public void updateJurisdiccion() {
		Jurisdiccion fakeJuris = createFakeJurisdiccion();

		when(repositorio.getJurisdiccionJpaDao().save(any(Jurisdiccion.class)))
				.thenReturn(fakeJuris);

		Jurisdiccion response = service.updateJurisdiccion(fakeJuris);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}
}
