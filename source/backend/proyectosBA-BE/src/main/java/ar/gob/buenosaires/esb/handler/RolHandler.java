package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.RolReqMsg;
import ar.gob.buenosaires.esb.domain.message.RolRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.JurisdiccionService;
import ar.gob.buenosaires.service.RolService;

public class RolHandler extends AbstractBaseEventHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(RolHandler.class);
	
	@Autowired
	private RolService service;

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(RolReqMsg.ROL_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, JurisdiccionService.class);
		final RolRespMsg response = new RolRespMsg();
		final RolReqMsg request = (RolReqMsg) event.getObj();
		List<Rol> roles = new ArrayList<Rol>();

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveRoles(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			roles.add(service.createRol(request.getRol()));
			response.setRoles(roles);
			event.setObj(response);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			roles.add(service.updateRol(request.getRol()));
			response.setRoles(roles);
			event.setObj(response);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteRol(request.getId());
		} else {

		}
		logResponseMessage(event, RolService.class);
	}

	private void retrieveRoles(ESBEvent event, final RolRespMsg response, final RolReqMsg request) {
		List<Rol> roles = new ArrayList<Rol>();

		if (request.getId() != null) {
			roles.add(service.getRolPorId(request.getId()));
		} else {
			roles = service.getRoles();
		}
		response.setRoles(roles);
		event.setObj(response);
	}
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}