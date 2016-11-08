package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.TipoObra;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.TipoObraReqMsg;
import ar.gob.buenosaires.esb.domain.message.TipoObraRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.TipoObraService;

@Service
public class TipoObraServiceImpl implements TipoObraService{
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TipoObraServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<TipoObra> getTiposObras() throws ESBException, JMSException {
		TipoObraReqMsg reqMsg = new TipoObraReqMsg();

		return getTipoObraFromReqMsg(reqMsg);
	}

	@Override
	public TipoObra getTipoObraPorId(Long id) throws ESBException, JMSException {
		TipoObraReqMsg reqMsg = new TipoObraReqMsg();
		reqMsg.setId(id);

		List<TipoObra> obras = getTipoObraFromReqMsg(reqMsg);
		return getFirstTipoObraFromTheList(obras);
	}

	@Override
	public TipoObra getTipoObraPorSubtipoObra(Long id) throws ESBException, JMSException {
		TipoObraReqMsg reqMsg = new TipoObraReqMsg();
		reqMsg.setSubtipoObraId(id);

		List<TipoObra> obras = getTipoObraFromReqMsg(reqMsg);
		return getFirstTipoObraFromTheList(obras);
	}
	
	@Override
	public TipoObra createTipoObra(TipoObra tipoObra, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		TipoObraReqMsg reqMsg = new TipoObraReqMsg();
		reqMsg.setTipoObra(tipoObra);
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para crear un TipoObra : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, TipoObraRespMsg.class);
		List<TipoObra> obras = getTipoObraFromResponse(response);
		return getFirstTipoObraFromTheList(obras);
	}

	@Override
	public TipoObra updateTipoObra(TipoObra tipoObra, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		TipoObraReqMsg reqMsg = new TipoObraReqMsg();
		reqMsg.setTipoObra(tipoObra);
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para actualizar un TipoObra : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, TipoObraRespMsg.class);
		List<TipoObra> obras = getTipoObraFromResponse(response);
		return getFirstTipoObraFromTheList(obras);
	}

	@Override
	public void deleteTipoObra(String id, String mailDelUsuarioDelToken) throws ESBException, JMSException {
		TipoObraReqMsg reqMsg = new TipoObraReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(mailDelUsuarioDelToken);

		getLogger().info("Mensaje creado para borrar un TipoObra : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, TipoObraRespMsg.class);
	}

	private List<TipoObra> getTipoObraFromReqMsg(TipoObraReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener un TipoObra : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE, TipoObraRespMsg.class);

		List<TipoObra> obras = getTipoObraFromResponse(response);
		return obras;
	}

	private List<TipoObra> getTipoObraFromResponse(EsbBaseMsg response) {
		List<TipoObra> obras = null;
		if (response.getEventType().equalsIgnoreCase(TipoObraRespMsg.TIPO_OBRA_TYPE)) {
			obras = ((TipoObraRespMsg) response).getTiposObras();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo las TipoObras de la respuesta del BUS de servicios: {}", obras.toString());
			} else {
				getLogger().info("Obteninendo las TipoObras de la respuesta del BUS de servicios");
			}
		}
		return obras;
	}

	private TipoObra getFirstTipoObraFromTheList(List<TipoObra> obras) {
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
