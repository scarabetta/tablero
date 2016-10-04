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

import ar.gob.buenosaires.dao.jpa.usuario.UsuarioJpaDao;
import ar.gob.buenosaires.dao.jpa.usuario.UsuarioRepositoryImpl;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.domain.UsuarioResumen;

public class UsuarioServiceImplTest {

	private static final String FAKE_NOMBRE = "fakeNombre";

	@Mock
	UsuarioJpaDao jpaDao;

	@InjectMocks
	@Spy
	UsuarioRepositoryImpl repositorio;

	@InjectMocks
	UsuarioServiceImpl service;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		repositorio.setUsuarioJpaDao(jpaDao);
		service = new UsuarioServiceImpl();
		service.setUsuarioRepository(repositorio);

	}

	private Usuario createFakeUsuario() {
		Usuario fakeUsuario = new Usuario();
		fakeUsuario.setNombre(FAKE_NOMBRE);

		return fakeUsuario;
	}

	@Test
	public void createUsuario() {

		Usuario fakeUsuario = createFakeUsuario();

		when(repositorio.getUsuarioJpaDao().save(any(Usuario.class))).thenReturn(fakeUsuario);

		Usuario response = service.createUsuario(fakeUsuario);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}

	// @Test
	// public void deleteUsuario() {
	// throw new RuntimeException("Test not implemented");
	// }

	@Test
	public void getUsuarioDAO() {
		UsuarioJpaDao jpaDao = service.getUsuarioDAO();
		assertThat(jpaDao).isNotNull();
	}

	@Test
	public void getUsuarioPorEmail() {
		Usuario fakeUsuario = createFakeUsuario();

		when(repositorio.getUsuarioJpaDao().findByEmail(anyString())).thenReturn(fakeUsuario);
		Usuario response = service.getUsuarioPorEmail("test");
		assertThat(response).isNotNull();
	}

	@Test
	public void getUsuarioPorId() {
		Usuario fakeUsuario = createFakeUsuario();

		when(repositorio.getUsuarioJpaDao().findOne(anyLong())).thenReturn(fakeUsuario);

		Usuario response = service.getUsuarioPorId(new Long(1));
		assertThat(response).isNotNull();
	}

	@Test
	public void getUsuarios() {
		List<Usuario> fakeUsuarios = new ArrayList<Usuario>();
		Usuario fakeUsuario = createFakeUsuario();
		fakeUsuarios.add(fakeUsuario);

		when(repositorio.getUsuarioJpaDao().findAll()).thenReturn(fakeUsuarios);

		List<Usuario> response = service.getUsuarios();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}

	@Test
	public void updateUsuario() {
		Usuario fakeUsuario = createFakeUsuario();

		when(repositorio.getUsuarioJpaDao().save(any(Usuario.class))).thenReturn(fakeUsuario);

		Usuario response = service.updateUsuario(fakeUsuario);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
		assertThat(response.getNombre()).isEqualTo(FAKE_NOMBRE);
	}
	
	@Test
	public void getUsuarioResumen() {
		List<UsuarioResumen> fakeResumenUsuarios = new ArrayList<UsuarioResumen>();
		UsuarioResumen fakeUsuarioResumen = new UsuarioResumen();
		fakeResumenUsuarios.add(fakeUsuarioResumen);
		
		when(repositorio.getUsuarioJpaDao().findAllResumen()).thenReturn(fakeResumenUsuarios);
		
		List<UsuarioResumen> response = service.getAllUsuariosResumen();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}
	@Test
	public void getUsuarioResumenPorEmail() {
		UsuarioResumen fakeUsuario = createFakeUsuarioResumen();

		when(repositorio.getUsuarioJpaDao().findResumenByEmail(anyString())).thenReturn(fakeUsuario);
		UsuarioResumen response = service.getUsuarioResumenPorEmail("test");
		assertThat(response).isNotNull();
	}
	
	private UsuarioResumen createFakeUsuarioResumen() {
		UsuarioResumen fakeUsuario = new UsuarioResumen();
		fakeUsuario.setNombre(FAKE_NOMBRE);

		return fakeUsuario;
	}
}
