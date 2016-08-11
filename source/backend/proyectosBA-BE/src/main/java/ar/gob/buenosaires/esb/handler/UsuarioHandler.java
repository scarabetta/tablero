package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.UsuarioReqMsg;
import ar.gob.buenosaires.esb.domain.message.UsuarioRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.UsuarioService;

public class UsuarioHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(UsuarioHandler.class);

	@Autowired
	private UsuarioService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, UsuarioService.class);
		final UsuarioRespMsg response = new UsuarioRespMsg();
		final UsuarioReqMsg request = (UsuarioReqMsg) event.getObj();
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveUsuarios(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			usuarios.add(service.createUsuario(request.getUsuario()));
			response.setUsuarios(usuarios);
			event.setObj(response);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			usuarios.add(service.updateUsuario(request.getUsuario()));
			response.setUsuarios(usuarios);
			event.setObj(response);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteUsuario(request.getId());
		} else {

		}
		logResponseMessage(event, UsuarioService.class);
	}

	private void retrieveUsuarios(ESBEvent event, final UsuarioRespMsg response, final UsuarioReqMsg request) {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (request.getId() != null) {
			usuarios.add(service.getUsuarioPorId(request.getId()));
		} else if (StringUtils.isNotBlank(request.getEmail())) {
			usuarios.add(service.getUsuarioPorEmail(request.getEmail()));
		} else {
			usuarios = service.getUsuarios();
		}
		response.setUsuarios(usuarios);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(UsuarioReqMsg.USUARIO_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
