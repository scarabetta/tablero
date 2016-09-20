package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.UsuarioReqMsg;
import ar.gob.buenosaires.esb.domain.message.UsuarioRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.UsuarioService;

public class UsuarioHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(UsuarioHandler.class);

	@Autowired
	private UsuarioService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, UsuarioService.class);
		final UsuarioReqMsg request = (UsuarioReqMsg) JMSUtil.crearObjeto(event.getXml(), UsuarioReqMsg.class);

		final UsuarioRespMsg response = new UsuarioRespMsg();
		event.setObj(response);
		List<Usuario> usuarios = new ArrayList<Usuario>();
		response.setUsuarios(usuarios);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveUsuarios(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			usuarios.add(service.createUsuario(request.getUsuario()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			usuarios.add(service.updateUsuario(request.getUsuario()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteUsuario(request.getId());
		} else {
			throw new ESBException("La accion: " + event.getAction() + ", no existe para el servicio de Usuario");
		}
		logResponseMessage(event, UsuarioService.class);
	}

	private void retrieveUsuarios(final UsuarioRespMsg response, final UsuarioReqMsg request) {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		if (request.getId() != null) {
			usuarios.add(service.getUsuarioPorId(request.getId()));
		} else if (request.getEmail() != null) {
			usuarios.add(service.getUsuarioPorEmail(request.getEmail()));
		} else {
			usuarios = service.getUsuarios();
		}
		response.setUsuarios(usuarios);
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
