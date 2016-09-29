package ar.gob.buenosaires.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoOperativoReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoOperativoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.ObjetivoOperativoService;

@Service
public class ObjetivoOperativoServiceImpl implements ObjetivoOperativoService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ObjetivoOperativoServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<ObjetivoOperativo> getObjetivosOperativos() throws ESBException, JMSException {
		ObjetivoOperativoReqMsg reqMsg = new ObjetivoOperativoReqMsg();

		return getObjetivosOperativoesFromReqMsg(reqMsg);
	}

	@Override
	public ObjetivoOperativo getObjetivoOperativoPorId(String id) throws ESBException, JMSException {
		ObjetivoOperativoReqMsg reqMsg = new ObjetivoOperativoReqMsg();
		reqMsg.setId(Long.parseLong(id));

		List<ObjetivoOperativo> objetivosOperativoes = getObjetivosOperativoesFromReqMsg(reqMsg);
		return getObjetivoOperativoFromResponse(objetivosOperativoes);
	}

	@Override
	public ObjetivoOperativo getObjetivoOperativoPorNombre(String nombre) throws ESBException, JMSException {
		ObjetivoOperativoReqMsg reqMsg = new ObjetivoOperativoReqMsg();
		reqMsg.setName(nombre);

		List<ObjetivoOperativo> objetivosOperativoes = getObjetivosOperativoesFromReqMsg(reqMsg);
		return getObjetivoOperativoFromResponse(objetivosOperativoes);
	}

	@Override
	public ObjetivoOperativo getObjetivoOperativoPorCodigo(String codigo) throws ESBException, JMSException {
		ObjetivoOperativoReqMsg reqMsg = new ObjetivoOperativoReqMsg();
		reqMsg.setCodigo(codigo);

		List<ObjetivoOperativo> objetivosOperativoes = getObjetivosOperativoesFromReqMsg(reqMsg);
		return getObjetivoOperativoFromResponse(objetivosOperativoes);
	}

	@Override
	public ObjetivoOperativo createObjetivoOperativo(ObjetivoOperativo objetivoOperativo, String email)
			throws ESBException, JMSException {
		
		ObjetivoOperativoReqMsg reqMsg = new ObjetivoOperativoReqMsg();
		reqMsg.setObjetivoOperativo(objetivoOperativo);
		reqMsg.setEmailUsuario(email);
		List<ObjetivoOperativo> responseObjetivosOperativos = new ArrayList<ObjetivoOperativo>();

		getLogger().debug("Mensaje creado para crear un Objetivo Operativo : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, ObjetivoOperativoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ObjetivoOperativoRespMsg.OBJETIVO_OPERATIVO_TYPE)) {
			responseObjetivosOperativos = ((ObjetivoOperativoRespMsg) response).getObjetivosOperativos();
		}
		return getObjetivoOperativoFromResponse(responseObjetivosOperativos);
	}

	@Override
	public ObjetivoOperativo updateObjetivoOperativo(ObjetivoOperativo objetivoOperativo, String email)
			throws ESBException, JMSException {
		
		ObjetivoOperativoReqMsg reqMsg = new ObjetivoOperativoReqMsg();
		reqMsg.setObjetivoOperativo(objetivoOperativo);
		reqMsg.setEmailUsuario(email);
		List<ObjetivoOperativo> responseObjetivosOperativos = new ArrayList<ObjetivoOperativo>();
		
		getLogger().debug("Mensaje creado para actualizar un Objetivo Operativo : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, ObjetivoOperativoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ObjetivoOperativoRespMsg.OBJETIVO_OPERATIVO_TYPE)) {
			responseObjetivosOperativos = ((ObjetivoOperativoRespMsg) response).getObjetivosOperativos();
		}
		return getObjetivoOperativoFromResponse(responseObjetivosOperativos);		
	}

	@Override
	public void deleteObjetivoOperativo(String id, String email) throws ESBException, JMSException {
		
		ObjetivoOperativoReqMsg reqMsg = new ObjetivoOperativoReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para borrar un Objetivo Operativo : {}",reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE, ObjetivoOperativoRespMsg.class);
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private List<ObjetivoOperativo> getObjetivosOperativoesFromReqMsg(
			ObjetivoOperativoReqMsg reqMsg) throws ESBException, JMSException {

		getLogger()
				.debug("Mensaje creado para obtener todos los Objetivos Operativoes: {}",reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, ObjetivoOperativoRespMsg.class);

		List<ObjetivoOperativo> objetivosOperativoes = null;
		if (response.getEventType().equalsIgnoreCase(ObjetivoOperativoRespMsg.OBJETIVO_OPERATIVO_TYPE)) {
			objetivosOperativoes = ((ObjetivoOperativoRespMsg) response).getObjetivosOperativos();
			LOGGER.debug("Obteninendo las jurisdicciones de la respues del BUS de servicios: {}",
					objetivosOperativoes.toString());
		}
		return objetivosOperativoes;
	}

	private ObjetivoOperativo getObjetivoOperativoFromResponse(List<ObjetivoOperativo> objetivosOperativoes) {
		if (!objetivosOperativoes.isEmpty()) {
			return objetivosOperativoes.get(0);
		} else {
			return null;
		}
	}

}
