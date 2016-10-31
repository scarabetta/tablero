package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.PresupuestoPorMes;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.PresupuestoPorMesReqMsg;
import ar.gob.buenosaires.esb.domain.message.PresupuestoPorMesRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.PresupuestoPorMesService;

@Service
public class PresupuestoPorMesServiceImpl implements PresupuestoPorMesService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PresupuestoPorMesServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<PresupuestoPorMes> getPresupuestosPorMes() throws ESBException, JMSException {
		PresupuestoPorMesReqMsg reqMsg = new PresupuestoPorMesReqMsg();

		return getPresupuestoPorMesFromReqMsg(reqMsg);
	}

	@Override
	public List<PresupuestoPorMes> getPresupuestosPorMesPorProyecto(Long id) throws ESBException, JMSException {
		PresupuestoPorMesReqMsg reqMsg = new PresupuestoPorMesReqMsg();

		return getPresupuestoPorMesFromReqMsg(reqMsg);
	}

	@Override
	public PresupuestoPorMes getPresupuestoPorMesPorId(Long id) throws ESBException, JMSException {
		PresupuestoPorMesReqMsg reqMsg = new PresupuestoPorMesReqMsg();
		reqMsg.setId(id);

		List<PresupuestoPorMes> roles = getPresupuestoPorMesFromReqMsg(reqMsg);
		return getFirstPresupuestoPorMesFromTheList(roles);
	}

	@Override
	public PresupuestoPorMes createPresupuestoPorMes(PresupuestoPorMes ppm, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		PresupuestoPorMesReqMsg reqMsg = new PresupuestoPorMesReqMsg();
		reqMsg.getPpms().add(ppm);
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para crear un PresupuestoPorMes : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, PresupuestoPorMesRespMsg.class);
		List<PresupuestoPorMes> roles = getPresupuestoPorMesFromResponse(response);
		return getFirstPresupuestoPorMesFromTheList(roles);
	}

	@Override
	public List<PresupuestoPorMes> createPresupuestosPorMes(List<PresupuestoPorMes> ppms, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		PresupuestoPorMesReqMsg reqMsg = new PresupuestoPorMesReqMsg();
		reqMsg.getPpms().addAll(ppms);
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para crear un PresupuestoPorMes : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, PresupuestoPorMesRespMsg.class);
		return getPresupuestoPorMesFromResponse(response);
	}

	@Override
	public PresupuestoPorMes updatePresupuestoPorMes(PresupuestoPorMes ppm, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		PresupuestoPorMesReqMsg reqMsg = new PresupuestoPorMesReqMsg();
		reqMsg.getPpms().add(ppm);
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para actualizar un PresupuestoPorMes : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, PresupuestoPorMesRespMsg.class);
		List<PresupuestoPorMes> roles = getPresupuestoPorMesFromResponse(response);
		return getFirstPresupuestoPorMesFromTheList(roles);
	}

	@Override
	public List<PresupuestoPorMes> updatePresupuestosPorMes(List<PresupuestoPorMes> ppms, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		PresupuestoPorMesReqMsg reqMsg = new PresupuestoPorMesReqMsg();
		reqMsg.getPpms().addAll(ppms);
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para actualizar un PresupuestoPorMes : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, PresupuestoPorMesRespMsg.class);
		return getPresupuestoPorMesFromResponse(response);
	}

	@Override
	public void deletePresupuestoPorMes(String id, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		PresupuestoPorMesReqMsg reqMsg = new PresupuestoPorMesReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para borrar un PresupuestoPorMes : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, PresupuestoPorMesRespMsg.class);
	}

	private List<PresupuestoPorMes> getPresupuestoPorMesFromReqMsg(PresupuestoPorMesReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener un PresupuestoPorMes : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE, PresupuestoPorMesRespMsg.class);

		List<PresupuestoPorMes> ppms = getPresupuestoPorMesFromResponse(response);
		return ppms;
	}

	private List<PresupuestoPorMes> getPresupuestoPorMesFromResponse(EsbBaseMsg response) {
		List<PresupuestoPorMes> ppms = null;
		if (response.getEventType().equalsIgnoreCase(PresupuestoPorMesRespMsg.PPM_TYPE)) {
			ppms = ((PresupuestoPorMesRespMsg) response).getPpms();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obtenienndo los PresupuestoPorMes de la respuesta del BUS de servicios: {}", ppms.toString());
			} else {
				getLogger().info("Obtenienndo los PresuPuestosPorMes de la respues del BUS de servicios");
			}
		}
		return ppms;
	}

	private PresupuestoPorMes getFirstPresupuestoPorMesFromTheList(List<PresupuestoPorMes> ppms) {
		if (!ppms.isEmpty()) {
			return ppms.get(0);
		} else {
			return null;
		}
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
