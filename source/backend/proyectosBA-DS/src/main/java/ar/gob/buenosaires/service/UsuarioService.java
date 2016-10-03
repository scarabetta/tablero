package ar.gob.buenosaires.service;

import java.text.ParseException;
import java.util.List;

import javax.jms.JMSException;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.domain.UsuarioResumen;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;

public interface UsuarioService {

	Usuario getUsuarioByEmail(String email) throws ESBException, JMSException;

	List<Usuario> getUsuarios() throws ESBException, JMSException;

	Usuario createUsuario(Usuario usuario, String email) throws ESBException, JMSException;

	Usuario updateUsuario(Usuario usuario, String email) throws ESBException, JMSException;

	void deleteUsuario(String id, String email) throws ESBException, JMSException;

	Usuario getUsuarioPorId(Long id) throws ESBException, JMSException;
	
	Usuario getUsuarioPorToken(String token) throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException;

	Usuario validarUsuario(String email);

	List<UsuarioResumen> getUsuariosResumen() throws ESBException, JMSException;

}
