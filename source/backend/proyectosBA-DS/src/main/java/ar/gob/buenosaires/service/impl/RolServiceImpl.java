package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.RolReqMsg;
import ar.gob.buenosaires.esb.domain.message.RolRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.RolService;

@Service
public class RolServiceImpl implements RolService {

	private final static Logger LOGGER = LoggerFactory.getLogger(RolServiceImpl.class);

	@Autowired
	private EsbService esbService;
	
	@Override
	public List<Rol> getRoles() throws ESBException, JMSException {
		RolReqMsg reqMsg = new RolReqMsg();

		return getRolFromReqMsg(reqMsg);
	}

	@Override
	public Rol createRol(Rol Rol) throws ESBException, JMSException {
		RolReqMsg reqMsg = new RolReqMsg();
		reqMsg.setRol(Rol);

		getLogger().debug("Mensaje creado para crear un Rol : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, RolRespMsg.class);
		List<Rol> roles = getRolFromResponse(response);
		return getFirstRolFromTheList(roles);
	}

	@Override
	public Rol updateRol(Rol Rol) throws ESBException, JMSException {
		RolReqMsg reqMsg = new RolReqMsg();
		reqMsg.setRol(Rol);

		getLogger().debug("Mensaje creado para actualizar un Rol : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, RolRespMsg.class);
		List<Rol> roles = getRolFromResponse(response);
		return getFirstRolFromTheList(roles);
	}

	@Override
	public void deleteRol(String id) throws ESBException, JMSException {
		RolReqMsg reqMsg = new RolReqMsg();
		reqMsg.setId(Long.parseLong(id));

		getLogger().debug("Mensaje creado para borrar un Rol : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, RolRespMsg.class);
	}

	@Override
	public Rol getRolPorId(Long id) throws ESBException, JMSException {
		RolReqMsg reqMsg = new RolReqMsg();
		reqMsg.setId(id);

		List<Rol> roles = getRolFromReqMsg(reqMsg);
		return getFirstRolFromTheList(roles);
	}

	private List<Rol> getRolFromReqMsg(RolReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener un Rol : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE, RolRespMsg.class);

		List<Rol> ususarios = getRolFromResponse(response);
		return ususarios;
	}

	private List<Rol> getRolFromResponse(EsbBaseMsg response) {
		List<Rol> roles = null;
		if (response.getEventType().equalsIgnoreCase(RolRespMsg.ROL_TYPE)) {
			roles = ((RolRespMsg) response).getRoles();
			LOGGER.debug("Obteninendo los Roles de la respues del BUS de servicios: {}", roles.toString());
		}
		return roles;
	}

	private Rol getFirstRolFromTheList(List<Rol> roles) {
		if (!roles.isEmpty()) {
			return roles.get(0);
		} else {
			return null;
		}
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
