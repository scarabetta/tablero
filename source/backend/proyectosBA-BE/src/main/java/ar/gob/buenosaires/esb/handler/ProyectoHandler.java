package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

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
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			updateProyecto(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteProyecto(request.getId());
		} else {

		}
		logResponseMessage(event, ProyectoService.class);
	}

	private void retrieveProyectos(ESBEvent event,
			final ProyectoRespMsg response, final ProyectoReqMsg request) {
		List<Proyecto> proyectos = new ArrayList<Proyecto>();

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
	
	private void createProyecto(ESBEvent event,
			final ProyectoRespMsg response, final ProyectoReqMsg request) throws ESBException {		
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		proyectos.add(service.createProyecto(request.getProyecto()));
		
		addProyectosToResponse(event, response, proyectos);
	}
	
	private void updateProyecto(ESBEvent event,
			final ProyectoRespMsg response, final ProyectoReqMsg request) throws ESBException {		
		List<Proyecto> proyectos = new ArrayList<Proyecto>();
		proyectos.add(service.updateProyecto(request.getProyecto()));
		
		addProyectosToResponse(event, response, proyectos);
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
}
