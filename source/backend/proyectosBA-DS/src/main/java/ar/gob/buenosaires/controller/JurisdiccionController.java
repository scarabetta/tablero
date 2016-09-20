package ar.gob.buenosaires.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.JurisdiccionService;
import ar.gob.buenosaires.service.UsuarioService;

@RestController
@RequestMapping("/api/jurisdiccion")
public class JurisdiccionController {

	@Autowired
	private JurisdiccionService service;

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Jurisdiccion> getJurisdicciones(final HttpServletRequest request) throws ESBException, JMSException {
		
		Usuario user = getUserFromToken(request);		
		return service.getJurisdicciones(user);			
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Jurisdiccion getJurisdiccionPorId(@PathVariable final String id, final HttpServletRequest request)
			throws ESBException, JMSException {

		Usuario user = getUserFromToken(request);
		return service.getJurisdiccionPorId(id, user);
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
	public Jurisdiccion createJurisdiccion(@RequestBody final Jurisdiccion jurisdiccion) throws ESBException, JMSException {
		return service.createJurisdicciones(jurisdiccion);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Jurisdiccion updateJurisdiccion(@RequestBody final Jurisdiccion jurisdiccion) throws ESBException, JMSException {
		return service.updateJurisdicciones(jurisdiccion);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteJurisdiccion(@PathVariable final String id) throws ESBException, JMSException {
		service.deleteJurisdiccion(id);
	}
	
	@RequestMapping(path = "/presentarCompletos/{id}", method = RequestMethod.POST)
	public @ResponseBody void presentarProyectosCompletos(@PathVariable final String id) throws ESBException, JMSException {
		service.presentarProyectosCompletos(id);
	}
	
	private Usuario getUserFromToken(final HttpServletRequest request) throws ESBException, JMSException {
		Usuario user = null;
		if (!HttpMethod.OPTIONS.name().equals(request.getMethod())) {
			try {
				user = usuarioService.getUsuarioPorToken(request.getHeader(HttpHeaders.AUTHORIZATION));
			} catch (ParseException | JOSEException | SignatureVerificationException e) {
				e.printStackTrace();
			}
		}
		return user;
	}

}
