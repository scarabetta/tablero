package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.JurisdiccionResumen;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.JurisdiccionReqMsg;
import ar.gob.buenosaires.esb.domain.message.JurisdiccionRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.JurisdiccionService;
import ar.gob.buenosaires.service.UsuarioService;

public class JurisdiccionHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(JurisdiccionHandler.class);

	@Autowired
	private JurisdiccionService service;
	
	@Autowired
	private UsuarioService usuarioService;
	
	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, JurisdiccionService.class);
		final JurisdiccionReqMsg request = (JurisdiccionReqMsg) JMSUtil.crearObjeto(getReader(JurisdiccionReqMsg.class), event.getXml());

		final JurisdiccionRespMsg response = new JurisdiccionRespMsg();
		event.setObj(response);
		List<Jurisdiccion> jurisdicciones = new ArrayList<Jurisdiccion>();
		response.setJurisdicciones(jurisdicciones);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveJurisdicciones(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE_RESUMEN)) {
			retrieveResumenJurisdicciones(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			jurisdicciones.add(service.createJurisdiccion(request.getJurisdiccion()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			jurisdicciones.add(service.updateJurisdiccion(request.getJurisdiccion()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteJurisdiccion(request.getId());
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_PRESENTAR_TODOS)) {
			service.presentarProyectosCompletos(request.getId());
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para el servicio de Jurisdiccion");
		}
		logResponseMessage(event, JurisdiccionService.class);
	}

	private void retrieveJurisdicciones(final JurisdiccionRespMsg response, final JurisdiccionReqMsg request) {
		List<Jurisdiccion> jurisdicciones = new ArrayList<Jurisdiccion>();
		obtenerUsuarioDelRequest(request);
		
		if(request.getUsuario() != null){
			if(request.getUsuario().tienePerfilSecretaria()){
				jurisdicciones = retrieveParaSecretaria(request, jurisdicciones);
			} else {
				jurisdicciones = retrieveParaUsuario(request, jurisdicciones);			
			}
		}
		response.setJurisdicciones(jurisdicciones);
	}
	
	private void obtenerUsuarioDelRequest(final JurisdiccionReqMsg request) {
		if(request.getUsuario() != null && request.getUsuario().getEmail() != null){
			Usuario usuario = usuarioService.getUsuarioPorEmail(request.getUsuario().getEmail());
			request.setUsuario(usuario);
		}
	}
	
	private void retrieveResumenJurisdicciones(final JurisdiccionRespMsg response, final JurisdiccionReqMsg request) {
		List<JurisdiccionResumen> jurisdiccionesResumen = new ArrayList<JurisdiccionResumen>();		
		Usuario usuario = usuarioService.getUsuarioPorEmail(request.getUsuario().getEmail());
		jurisdiccionesResumen = service.getJurisdiccionesResumen(usuario);			
		response.setJurisdiccionesResumen(jurisdiccionesResumen);
	}

	private List<Jurisdiccion> retrieveParaUsuario(final JurisdiccionReqMsg request, List<Jurisdiccion> jurisdicciones) {
		if (request.getId() != null) {
			jurisdicciones.add(service.getJurisdiccionPorId(request.getId()));
		} else if (request.getName() != null) {
			jurisdicciones.add(service.getJurisdiccionPorNombre(request.getName()));
		} else if (request.getCodigo() != null) {
			jurisdicciones.add(service.getJurisdiccionPorCodigo(request.getCodigo()));
		} else if(request.getUsuario() != null){
			jurisdicciones = request.getUsuario().getJurisdicciones();
		}
		return jurisdicciones;
	}
	
	private List<Jurisdiccion> retrieveParaSecretaria(final JurisdiccionReqMsg request, List<Jurisdiccion> jurisdicciones) {
		//TODO refactorizar y usar una query para cada caso
		if (request.getId() != null) {
//			jurisdicciones.add(service.getJurisdiccionPorIdParaSecretaria(request.getId()));
			jurisdicciones.add(service.getJurisdiccionPorId(request.getId()));
		} else {
//			jurisdicciones = service.getJurisdiccionesParaSecretaria();
			jurisdicciones = service.getJurisdicciones(); 		
		}
		sacarBorradoresSecretaria(jurisdicciones);

		return jurisdicciones;
	}

	private void sacarBorradoresSecretaria(List<Jurisdiccion> jurisdicciones) {
		//TODO sacar esto y hacelo con una query para cada caso
		//Sacamos todos los proyectos en estado borrador e incompletos	
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
										return Arrays.asList(EstadoProyecto.COMPLETO.getName(), EstadoProyecto.INCOMPLETO.getName()).contains(t.getEstado());
									}
								});
							}
						});
					}
				});
			}
		});
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(
				JurisdiccionReqMsg.JURISDICCION_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
