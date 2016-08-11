package ar.gob.buenosaires.service.impl;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyLong;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import ar.gob.buenosaires.dao.jpa.rol.RolJpaDao;
import ar.gob.buenosaires.dao.jpa.rol.RolRepositoryImpl;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.Rol;

public class RolServiceImplTest {
	private static final String FAKE_NOMBRE = "fakeNombre";

	@Mock
	RolJpaDao jpaDao;

	@InjectMocks
	@Spy
	RolRepositoryImpl repositorio;

	@InjectMocks
	RolServiceImpl service;

	@BeforeMethod
	public void beforeMethod() {
		MockitoAnnotations.initMocks(this);
		repositorio.setRolJpaDao(jpaDao);
		service = new RolServiceImpl();
		service.setRolRepository(repositorio);
	}

	private Rol createFakeRol() {
		Rol fakeRol = new Rol();
		fakeRol.setNombre(FAKE_NOMBRE);
		return fakeRol;
	}

	@Test
	public void createRol() {
		Rol fakeRol = createFakeRol();

		when(repositorio.getRolJpaDao().save(any(Rol.class))).thenReturn(
				fakeRol);

		Rol response = service.createRol(fakeRol);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
	}

	//
	// @Test
	// public void deleteRol() {
	// throw new RuntimeException("Test not implemented");
	// }

	@Test
	public void getRolJpaDao() {
		RolJpaDao jpaDao = service.getRolJpaDao();
		assertThat(jpaDao).isNotNull();
	}

	@Test
	public void getRolPorId() {
		Rol fakeRol = createFakeRol();

		when(repositorio.getRolJpaDao().getOne(anyLong())).thenReturn(fakeRol);

		Rol response = service.getRolPorId(new Long(1));
		assertThat(response).isNotNull();
	}

	@Test
	public void getRoles() {
		List<Rol> fakeRoles = new ArrayList<Rol>();
		Rol fakeRol = new Rol();
		fakeRoles.add(fakeRol);

		when(repositorio.getRolJpaDao().findAll()).thenReturn(fakeRoles);

		List<Rol> response = service.getRoles();
		assertThat(response).isNotNull();
		assertThat(response).isNotEmpty();
	}

	@Test
	public void updateRol() {
		Rol fakeRol = createFakeRol();

		when(repositorio.getRolJpaDao().save(any(Rol.class))).thenReturn(fakeRol);

		Rol response = service.updateRol(fakeRol);
		assertThat(response).isNotNull();
		assertThat(response.getNombre()).isNotNull();
	}
}
