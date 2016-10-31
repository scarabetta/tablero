package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.ObraReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObraRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.ObraService;

@Service
public class ObraServiceImpl implements ObraService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ObraServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<Obra> getObras() throws ESBException, JMSException {
		ObraReqMsg reqMsg = new ObraReqMsg();

		return getObraFromReqMsg(reqMsg);
	}

	@Override
	public Obra getObraPorId(Long id) throws ESBException, JMSException {
		ObraReqMsg reqMsg = new ObraReqMsg();
		reqMsg.setId(id);

		List<Obra> obras = getObraFromReqMsg(reqMsg);
		return getFirstObraFromTheList(obras);
	}

	@Override
	public Obra createObra(Obra obra, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		ObraReqMsg reqMsg = new ObraReqMsg();
		reqMsg.setObra(obra);
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para crear una Obra : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, ObraRespMsg.class);
		List<Obra> obras = getObraFromResponse(response);
		return getFirstObraFromTheList(obras);
	}

	@Override
	public Obra updateObra(Obra obra, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		ObraReqMsg reqMsg = new ObraReqMsg();
		reqMsg.setObra(obra);
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para actualizar una Obra : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, ObraRespMsg.class);
		List<Obra> obras = getObraFromResponse(response);
		return getFirstObraFromTheList(obras);
	}

	@Override
	public void deleteObra(String id, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		ObraReqMsg reqMsg = new ObraReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para borrar una Obra : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, ObraRespMsg.class);
	}
	
	private List<Obra> getObraFromReqMsg(ObraReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener una Obra : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE, ObraRespMsg.class);

		List<Obra> obras = getObraFromResponse(response);
		return obras;
	}

	private List<Obra> getObraFromResponse(EsbBaseMsg response) {
		List<Obra> obras = null;
		if (response.getEventType().equalsIgnoreCase(ObraRespMsg.OBRA_TYPE)) {
			obras = ((ObraRespMsg) response).getObras();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo las Obras de la respuesta del BUS de servicios: {}", obras.toString());
			} else {
				getLogger().info("Obteninendo las Obras de la respuesta del BUS de servicios");
			}
		}
		return obras;
	}

	private Obra getFirstObraFromTheList(List<Obra> obras) {
		if (!obras.isEmpty()) {
			return obras.get(0);
		} else {
			return null;
		}
	}
	
	public static Logger getLogger() {
		return LOGGER;
	}
}
