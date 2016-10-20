package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.usuario.UsuarioJpaDao;
import ar.gob.buenosaires.dao.jpa.usuario.UsuarioRepository;
import ar.gob.buenosaires.dao.jpa.usuario.UsuarioRepositoryImpl;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.domain.UsuarioResumen;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private static final int POSICION_ACTIVO = 5;
	private static final int POSICION_DESCRIPCION = 4;
	private static final int POSICION_EMAIL = 3;
	private static final int POSICION_APELLIDO = 2;
	private static final int POSICION_NOMBRE = 1;
	private static final int POSICION_ID = 0;
	
	@Autowired
	private UsuarioRepository repository;

	@Override
	public Usuario getUsuarioPorId(Long id) {
		return getUsuarioDAO().findOne(id);
	}

	@Override
	public Usuario getUsuarioPorEmail(String email) {
		return getUsuarioDAO().findByEmail(email);
	}

	@Override
	public List<Usuario> getUsuarios() {
		return getUsuarioDAO().findAll();
	}

	@Override
	public Usuario createUsuario(Usuario usuario) throws ESBException {
		Usuario existe = getUsuarioPorEmail(usuario.getEmail());
		if(existe == null){
			existe = getUsuarioDAO().save(usuario);
		} else {
			throw new ESBException(CodigoError.USUARIO_DUPLICADO.getCodigo(), "Ya existe registrado un usuario con el E-Mail: " + usuario.getEmail());
		}
		return existe;
	}

	@Override
	public Usuario updateUsuario(Usuario usuario) {
		return getUsuarioDAO().save(usuario);
	}

	@Override
	public void deleteUsuario(Long id) throws ESBException {
		Usuario usuario = getUsuarioDAO().findOne(id);
		if (usuario != null) {
			getUsuarioDAO().delete(usuario);
		} else {
			throw new ESBException(CodigoError.USUARIO_INEXISTENTE.getCodigo(), "No se encontro usuario con id: " + id);
		}
	}

	UsuarioJpaDao getUsuarioDAO() {
		return repository.getUsuarioJpaDao();
	}
	
	@VisibleForTesting
	public void setUsuarioRepository(UsuarioRepositoryImpl repo) {
		this.repository = repo;
	}

//	@Override
//	public List<UsuarioResumen> getUsuariosResumen() {
//		List<Object[]> findResumenParaUsuario = getUsuarioDAO().findUsuariosResumen();
//		List<UsuarioResumen> result = new ArrayList<UsuarioResumen>();
//		
//		for (Object[] object : findResumenParaUsuario) {
//			result.add(construirUsuarioResumen(object));
//		}
//		return result;
//	}
//	
//	private UsuarioResumen construirUsuarioResumen(Object[] object) {
//		UsuarioResumen resumen = new UsuarioResumen();
//		int id = object[POSICION_ID] != null?(int)object[POSICION_ID] : 0;
//		resumen.setIdUsuario(Long.valueOf(id));
//		String nombre = object[POSICION_NOMBRE] != null?(String)object[POSICION_NOMBRE]:"";
//		resumen.setNombre(nombre);
//		String apellido = object[POSICION_APELLIDO] != null? (String)object[POSICION_APELLIDO]:"";
//		resumen.setApellido(apellido);
//		String email = object[POSICION_EMAIL] != null?(String)object[POSICION_EMAIL]:"";
//		resumen.setEmail(email);
//		String descripcion = object[POSICION_DESCRIPCION] != null?(String)object[POSICION_DESCRIPCION]:"";
//		resumen.setDescripcion(descripcion);
//		Boolean activo = object[POSICION_ACTIVO] != null && ((int)object[POSICION_ACTIVO]) == 1?Boolean.TRUE: Boolean.FALSE;
//		resumen.setActivo(activo);
//		return resumen;
//	}

	@Override
	public List<UsuarioResumen> getAllUsuariosResumen() {
		return getUsuarioDAO().findAllResumen();
	}

	@Override
	public UsuarioResumen getUsuarioResumenPorEmail(String email) {
		return getUsuarioDAO().findResumenByEmail(email);
	}
}
