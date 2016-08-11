package ar.gob.buenosaires.service.impl;

import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.usuario.UsuarioJpaDao;
import ar.gob.buenosaires.dao.jpa.usuario.UsuarioRepository;
import ar.gob.buenosaires.dao.jpa.usuario.UsuarioRepositoryImpl;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
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
	public Usuario createUsuario(Usuario usuario) {
		return getUsuarioDAO().save(usuario);
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
			throw new ESBException("No se encontro usuario con id: " + id);
		}
	}

	UsuarioJpaDao getUsuarioDAO() {
		return repository.getUsuarioJpaDao();
	}
	
	@VisibleForTesting
	public void setUsuarioRepository(UsuarioRepositoryImpl repo) {
		this.repository = repo;
	}
}
