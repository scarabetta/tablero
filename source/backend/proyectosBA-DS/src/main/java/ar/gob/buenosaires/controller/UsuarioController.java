package ar.gob.buenosaires.controller;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.domain.UsuarioResumen;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.UsuarioService;
import ar.gob.buenosaires.util.DSUtils;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Usuario> getUsuarios() throws ESBException, JMSException {
		return service.getUsuarios();
	}
	
	@RequestMapping(path = "/resumen", method = RequestMethod.GET)
	public @ResponseBody List<UsuarioResumen> getUsuariosResumen() throws ESBException, JMSException {
		return service.getUsuariosResumen();
	}

	@RequestMapping(path = "/porToken", method = RequestMethod.GET)
	public @ResponseBody Usuario getUsuario(final HttpServletRequest request)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		final Usuario user = service.getUsuarioPorToken(request.getHeader(HttpHeaders.AUTHORIZATION));
		user.setJurisdicciones(null);
		return user;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Usuario getUsuarioPorId(@PathVariable final Long id) throws ESBException, JMSException {
		return service.getUsuarioPorId(id);
	}
	
	@RequestMapping(path = "/inhabilitar", method = RequestMethod.PUT)
	public @ResponseBody void inhabilitarUsuario(@RequestBody Map<String, String> mapParams,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		service.inhabilitarUsuario(Long.parseLong(mapParams.get("idUsuario")), DSUtils.getMailDelUsuarioDelToken(token));
	}
	
	@RequestMapping(path = "/habilitar", method = RequestMethod.PUT)
	public @ResponseBody void habilitarUsuario(@RequestBody Map<String, String> mapParams,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		service.habilitarUsuario(Long.parseLong(mapParams.get("idUsuario")), DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/email/{email}", method = RequestMethod.GET)
	public @ResponseBody Usuario getUsuarioPorMail(@PathVariable final String email) throws ESBException, JMSException {
		return service.getUsuarioByEmail(email);
	}

	@RequestMapping(path = "/validar/{email}", method = RequestMethod.GET)
	public @ResponseBody Usuario validarUsuario(@PathVariable final String email) throws ESBException, JMSException {
		return service.validarUsuario(email);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Usuario createUsuario(@RequestBody final Usuario usuario,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return service.createUsuario(usuario, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Usuario updateUsuario(@RequestBody final Usuario usuario,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return service.updateUsuario(usuario, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteUsuario(@PathVariable final String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		service.deleteUsuario(id, DSUtils.getMailDelUsuarioDelToken(token));
	}
}
