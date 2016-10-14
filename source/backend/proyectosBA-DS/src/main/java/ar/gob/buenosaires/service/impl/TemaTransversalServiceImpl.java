package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.TemaTransversalReqMsg;
import ar.gob.buenosaires.esb.domain.message.TemaTransversalRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.TemaTransversalService;

@Service
public class TemaTransversalServiceImpl implements TemaTransversalService {

	private final static Logger LOGGER = LoggerFactory.getLogger(TemaTransversalServiceImpl.class);

	@Autowired
	private EsbService esbService;
	
	@Override
	public List<TemaTransversal> getTemasTransversales() throws ESBException, JMSException {
		TemaTransversalReqMsg reqMsg = new TemaTransversalReqMsg();

		return getTemaTransversalFromReqMsg(reqMsg);
	}

	@Override
	public TemaTransversal createTemaTransversal(TemaTransversal temaTransversal, String email) throws ESBException, JMSException {
		TemaTransversalReqMsg reqMsg = new TemaTransversalReqMsg();
		reqMsg.setTemaTransversal(temaTransversal);
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para crear un Tema Transversal : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, TemaTransversalRespMsg.class);
		List<TemaTransversal> temasTransversales = getTemaTransversalFromResponse(response);
		return getFirstTemaTransversalFromTheList(temasTransversales);
	}

	@Override
	public TemaTransversal updateTemaTransversal(TemaTransversal temaTransversal, String email) throws ESBException, JMSException {
		TemaTransversalReqMsg reqMsg = new TemaTransversalReqMsg();
		reqMsg.setTemaTransversal(temaTransversal);
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para actualizar un Tema Transversal : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, TemaTransversalRespMsg.class);
		List<TemaTransversal> temasTransversales = getTemaTransversalFromResponse(response);
		return getFirstTemaTransversalFromTheList(temasTransversales);
	}

	@Override
	public void deleteTemaTransversal(String id, String email) throws ESBException, JMSException {
		TemaTransversalReqMsg reqMsg = new TemaTransversalReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para borrar un Tema Transversal : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, TemaTransversalRespMsg.class);
	}

	@Override
	public TemaTransversal getTemaTransversalPorId(Long id) throws ESBException, JMSException {
		TemaTransversalReqMsg reqMsg = new TemaTransversalReqMsg();
		reqMsg.setId(id);

		List<TemaTransversal> temasTransversales = getTemaTransversalFromReqMsg(reqMsg);
		return getFirstTemaTransversalFromTheList(temasTransversales);
	}

	private List<TemaTransversal> getTemaTransversalFromReqMsg(TemaTransversalReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener un Tema Transversal : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, TemaTransversalRespMsg.class);

		List<TemaTransversal> temasTransversales = null;
		if (response.getEventType().equalsIgnoreCase(TemaTransversalRespMsg.TEMA_TRANSVERSAL_TYPE)) {
			temasTransversales = ((TemaTransversalRespMsg) response).getTemasTransversales();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo los temas Transversales  de la respuesta del BUS de servicios: {}",
					temasTransversales .toString());
			} else {
				getLogger().info("Obteninendo los temas Transversales  de la respuesta del BUS de servicios");
			}
		}
		return temasTransversales ;
	}

	private List<TemaTransversal> getTemaTransversalFromResponse(EsbBaseMsg response) {
		List<TemaTransversal> temasTransversales = null;
		if (response.getEventType().equalsIgnoreCase(TemaTransversalRespMsg.TEMA_TRANSVERSAL_TYPE)) {
			temasTransversales = ((TemaTransversalRespMsg) response).getTemasTransversales();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo los temas Transversales  de la respuesta del BUS de servicios: {}",
					temasTransversales .toString());
			} else {
				getLogger().info("Obteninendo los temas Transversales  de la respuesta del BUS de servicios");
			}
		}
		return temasTransversales;
	}

	private TemaTransversal getFirstTemaTransversalFromTheList(List<TemaTransversal> temaTransversal) {
		if (!temaTransversal.isEmpty()) {
			return temaTransversal.get(0);
		} else {
			return null;
		}
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
