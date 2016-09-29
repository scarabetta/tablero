package ar.gob.buenosaires.controller;

import java.text.ParseException;
import java.util.List;

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

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.JurisdiccionResumen;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.JurisdiccionService;
import ar.gob.buenosaires.service.UsuarioService;
import ar.gob.buenosaires.util.DSUtils;

@RestController
@RequestMapping("/api/jurisdiccion")
public class JurisdiccionController {

	@Autowired
	private JurisdiccionService service;

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Jurisdiccion> getJurisdicciones(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return service.getJurisdicciones(DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.GET, path = "/resumen")
	public @ResponseBody List<JurisdiccionResumen> getJurisdiccionesResumen(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return service
				.getJurisdiccionesResumen(DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Jurisdiccion getJurisdiccionPorId(@PathVariable final String id,
			final HttpServletRequest request)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return service.getJurisdiccionPorIdyEmail(id,
				DSUtils.getMailDelUsuarioDelToken(request.getHeader(HttpHeaders.AUTHORIZATION)));
	}

	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
	public @ResponseBody Jurisdiccion getCategoriaPorNombre(@PathVariable final String nombre)
			throws ESBException, JMSException {
		return service.getJurisdiccionesByName(nombre);
	}

	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
	public @ResponseBody Jurisdiccion getCategoriaPorCodigo(@PathVariable final String codigo)
			throws ESBException, JMSException {
		return service.getJurisdiccionesPorCodigo(codigo);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Jurisdiccion createJurisdiccion(@RequestBody final Jurisdiccion jurisdiccion,
			final HttpServletRequest request)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return service.createJurisdicciones(jurisdiccion,
				DSUtils.getMailDelUsuarioDelToken(request.getHeader(HttpHeaders.AUTHORIZATION)));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Jurisdiccion updateJurisdiccion(@RequestBody final Jurisdiccion jurisdiccion,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return service.updateJurisdicciones(jurisdiccion,
				DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteJurisdiccion(@PathVariable final String id, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		service.deleteJurisdiccion(id, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/presentarCompletos/{id}", method = RequestMethod.POST)
	public @ResponseBody void presentarProyectosCompletos(@PathVariable final String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		service.presentarProyectosCompletos(id,
				DSUtils.getMailDelUsuarioDelToken(token));
	}
}
