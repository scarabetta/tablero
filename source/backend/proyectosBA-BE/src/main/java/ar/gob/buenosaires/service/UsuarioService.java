package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.domain.UsuarioResumen;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface UsuarioService {

	Usuario getUsuarioPorId(Long id);

	Usuario getUsuarioPorEmail(String email);

	List<Usuario> getUsuarios();

	Usuario createUsuario(Usuario usuario);

	Usuario updateUsuario(Usuario usuario);

	void deleteUsuario(Long id) throws ESBException;

//	List<UsuarioResumen> getUsuariosResumen();

	List<UsuarioResumen> getAllUsuariosResumen();

}
