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

import com.nimbusds.jose.JOSEException;

@RestController
@RequestMapping("/api/jurisdiccion")
public class JurisdiccionController {

	@Autowired
	private JurisdiccionService service;

	@Autowired
	private UsuarioService usuarioService;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Jurisdiccion> getJurisdicciones(final HttpServletRequest request)
			throws ESBException, JMSException {
		Usuario user = null;
		List<Jurisdiccion> jurisdicciones = new ArrayList<>();

		if (!HttpMethod.OPTIONS.name().equals(request.getMethod())) {
			try {
				user = usuarioService.getUsuarioPorToken(request.getHeader(HttpHeaders.AUTHORIZATION));
			} catch (ParseException | JOSEException | SignatureVerificationException e) {
				e.printStackTrace();
			}
		}
		// TODO sale rapido el jurisdicciones por usuario. Cambiarlo lo antes
		// posible.
		if (user.tienePerfilSecretaria()) {
			jurisdicciones = service.getJurisdicciones();
			// Sacamos todos los proyectos en estado borrador e incompletos
			jurisdicciones.parallelStream().forEach(new Consumer<Jurisdiccion>() {

				@Override
				public void accept(final Jurisdiccion t) {
					t.getObjetivosJurisdiccionales().parallelStream().forEach(new Consumer<ObjetivoJurisdiccional>() {

						@Override
						public void accept(final ObjetivoJurisdiccional t) {
							t.getObjetivosOperativos().parallelStream().forEach(new Consumer<ObjetivoOperativo>() {

								@Override
								public void accept(final ObjetivoOperativo t) {
									t.getProyectos().removeIf(new Predicate<Proyecto>() {

										@Override
										public boolean test(final Proyecto t) {
											return Arrays.asList(EstadoProyecto.BORRADOR.getClass(), 
													EstadoProyecto.INCOMPLETO.getClass()).contains(t.getEstado());
										}
									});

								}
							});

						}
					});
				}
			});
		} else {
			jurisdicciones = user.getJurisdicciones();
		}

		return jurisdicciones;
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Jurisdiccion getJurisdiccionPorId(@PathVariable final String id)
			throws ESBException, JMSException {
		return service.getJurisdiccionPorId(id);
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
	public void createJurisdiccion(@RequestBody final Jurisdiccion jurisdiccion) throws ESBException, JMSException {
		service.createJurisdicciones(jurisdiccion);
	}

	@RequestMapping(method = RequestMethod.PUT)
	public void updateJurisdiccion(@RequestBody final Jurisdiccion jurisdiccion) throws ESBException, JMSException {
		service.updateJurisdicciones(jurisdiccion);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteJurisdiccion(@PathVariable final String id) throws ESBException, JMSException {
		service.deleteJurisdiccion(id);
	}
	
	@RequestMapping(path = "/presentarCompletos/{id}", method = RequestMethod.POST) //TODO ver con socas si es metodo correspondiente
	public @ResponseBody void presentarProyectosCompletos(@PathVariable final String id) throws ESBException, JMSException {
		service.presentarProyectosCompletos(id);
	}

}
