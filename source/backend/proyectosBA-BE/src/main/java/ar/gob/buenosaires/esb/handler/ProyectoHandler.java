package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.AccionesProyecto;
import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ProyectoReqMsg;
import ar.gob.buenosaires.esb.domain.message.ProyectoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ProyectoService;

public class ProyectoHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProyectoHandler.class);

	@Autowired
	private ProyectoService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ProyectoService.class);
		final ProyectoRespMsg response = new ProyectoRespMsg();
		final ProyectoReqMsg request = (ProyectoReqMsg) event.getObj();

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveProyectos(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			createProyecto(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE_ACTIONS)) {
			retrieveActions(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			updateProyecto(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CANCEL)) {
			cancelarProyecto(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_VERIFICAR)) {
			verificarProyecto(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DESHACER_CANCELACION)) {
			deshacerCancelacion(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteProyecto(request.getId());
 		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CANCELAR_PRIORIZACION)) {
 			service.cancelarPriorizacionDeProyectosNoVerificados();
			service.cancelarPriorizacionDeProyectosVerificados();
 		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_INICIAR_PRIORIZACION)) {
 			service.iniciarPriorizacionDeProyectos();
 		} else {
 			throw new ESBException("La accion: " + event.getAction() + ", no existe para servicio de Proyecto");
		}
		logResponseMessage(event, ProyectoService.class);
	}

	private void deshacerCancelacion(ESBEvent event, ProyectoRespMsg response, ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());

		if (EstadoProyecto.CANCELADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado())) {
			setEstadoAnterior(proyectoGuardado);
			request.setProyecto(proyectoGuardado);
			updateProyecto(event, response, request);
		} else {
			throw new ESBException("El proyecto debe estar cancelado para deshacer la cancelacion");
		}
	}

	private void retrieveProyectos(ESBEvent event,
			final ProyectoRespMsg response, final ProyectoReqMsg request) {
		List<Proyecto> proyectos = new ArrayList<>();

		if (request.getId() != null) {
			proyectos.add(service.getProyectoPorId(request.getId()));
		} else if (StringUtils.isNotBlank(request.getName())) {
			proyectos.add(service.getProyectoPorNombre(request.getName()));
		} else if (StringUtils.isNotBlank(request.getCodigo())) {
			proyectos.add(service.getProyectoPorCodigo(request.getCodigo()));
		} else {
			proyectos = service.getProyectos();
		}
		addProyectosToResponse(event, response, proyectos);
	}

	private void retrieveActions(ESBEvent event, ProyectoRespMsg response, ProyectoReqMsg request) {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getId());
		String estado = proyectoGuardado.getEstado();
		List<String> acciones = new ArrayList<>();

		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(estado)){
			acciones.add(AccionesProyecto.CANCELAR.getName());
			acciones.add(AccionesProyecto.VERIFICAR.getName());
		} else if (EstadoProyecto.CANCELADO.getName().equalsIgnoreCase(estado)){
			acciones.add(AccionesProyecto.DESHACER_CANCELACION.getName());
		} else if (EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(estado)){
			acciones.add(AccionesProyecto.CANCELAR.getName());
		}

		response.setAccionesPermitidas(acciones);
		event.setObj(response);
	}

	private void createProyecto(ESBEvent event,
			final ProyectoRespMsg response, final ProyectoReqMsg request) throws ESBException {

		request.getProyecto().setVerificado(false);
		List<Proyecto> proyectos = new ArrayList<>();
		proyectos.add(service.createProyecto(request.getProyecto()));

		addProyectosToResponse(event, response, proyectos);
	}

	private void updateProyecto(ESBEvent event,
			final ProyectoRespMsg response, final ProyectoReqMsg request) throws ESBException {
		List<Proyecto> proyectos = new ArrayList<>();
		proyectos.add(service.updateProyecto(request.getProyecto()));

		addProyectosToResponse(event, response, proyectos);
	}

	private void cancelarProyecto(ESBEvent event, final ProyectoRespMsg response, final ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());

		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado())
				|| EstadoProyecto.VERIFICADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado())) {
			proyectoGuardado.setEstado(EstadoProyecto.CANCELADO.getName());
			request.setProyecto(proyectoGuardado);
			updateProyecto(event, response, request);
		} else {
			throw new ESBException("El proyecto debe estar presentado o verificado para poder ser cancelado");
		}

	}

	private void verificarProyecto(ESBEvent event, final ProyectoRespMsg response, final ProyectoReqMsg request) throws ESBException {
		Proyecto proyectoGuardado = service.getProyectoPorId(request.getProyecto().getIdProyecto());

		if (EstadoProyecto.PRESENTADO.getName().equalsIgnoreCase(proyectoGuardado.getEstado())) {
			proyectoGuardado.setEstado(EstadoProyecto.VERIFICADO.getName());
			proyectoGuardado.setVerificado(true);
			request.setProyecto(proyectoGuardado);
			updateProyecto(event, response, request);
		} else {
			throw new ESBException("El proyecto debe estar presentado para poder ser verificado");
		}

	}

	private void addProyectosToResponse(ESBEvent event, final ProyectoRespMsg response, List<Proyecto> proyectos) {
		response.setProyectos(proyectos);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ProyectoReqMsg.PROYECTO_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	private void setEstadoAnterior(Proyecto proyecto) {
		if(proyecto.getVerificado()){
			proyecto.setEstado(EstadoProyecto.VERIFICADO.getName());
		} else {
			proyecto.setEstado(EstadoProyecto.PRESENTADO.getName());
		}
	}
}

