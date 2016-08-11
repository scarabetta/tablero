package ar.gob.buenosaires.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoJurisdiccionalReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoJurisdiccionalRespMsg;
import ar.gob.buenosaires.esb.domain.message.ProyectoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.ObjetivoJurisdiccionalService;

@Service
public class ObjetivoJurisdiccionalServiceImpl implements ObjetivoJurisdiccionalService {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(ObjetivoJurisdiccionalServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<ObjetivoJurisdiccional> getObjetivosJurisdiccionales() throws ESBException, JMSException {
		ObjetivoJurisdiccionalReqMsg reqMsg = new ObjetivoJurisdiccionalReqMsg();

		return getObjetivosJurisdiccionalesFromReqMsg(reqMsg);
	}

	@Override
	public ObjetivoJurisdiccional getObjetivoJurisdiccionalPorId(String id) throws ESBException, JMSException {
		ObjetivoJurisdiccionalReqMsg reqMsg = new ObjetivoJurisdiccionalReqMsg();
		reqMsg.setId(Long.parseLong(id));

		List<ObjetivoJurisdiccional> objetivosJurisdiccionales = getObjetivosJurisdiccionalesFromReqMsg(reqMsg);
		return getObjetivoJurisdiccionalFromResponse(objetivosJurisdiccionales);
	}

	@Override
	public ObjetivoJurisdiccional getObjetivoJurisdiccionalPorNombre(String nombre) throws ESBException, JMSException {
		ObjetivoJurisdiccionalReqMsg reqMsg = new ObjetivoJurisdiccionalReqMsg();
		reqMsg.setName(nombre);

		List<ObjetivoJurisdiccional> objetivosJurisdiccionales = getObjetivosJurisdiccionalesFromReqMsg(reqMsg);
		return getObjetivoJurisdiccionalFromResponse(objetivosJurisdiccionales);
	}

	@Override
	public ObjetivoJurisdiccional getObjetivoJurisdiccionalPorCodigo(String codigo) throws ESBException, JMSException {
		ObjetivoJurisdiccionalReqMsg reqMsg = new ObjetivoJurisdiccionalReqMsg();
		reqMsg.setCodigo(codigo);

		List<ObjetivoJurisdiccional> objetivosJurisdiccionales = getObjetivosJurisdiccionalesFromReqMsg(reqMsg);
		return getObjetivoJurisdiccionalFromResponse(objetivosJurisdiccionales);
	}

	@Override
	public ObjetivoJurisdiccional createObjetivoJurisdiccional(@RequestBody ObjetivoJurisdiccional objetivoJurisdiccional)
			throws ESBException, JMSException {
		
		ObjetivoJurisdiccionalReqMsg reqMsg = new ObjetivoJurisdiccionalReqMsg();
		reqMsg.setObjetivoJurisdiccional(objetivoJurisdiccional);
		List<ObjetivoJurisdiccional> responseObjetivosJurisdiccionales = new ArrayList<ObjetivoJurisdiccional>();

		getLogger().debug("Mensaje creado para crear un ObjetivoJurisdiccional : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE);
		if (response.getEventType().equalsIgnoreCase(ObjetivoJurisdiccionalRespMsg.OBJETIVO_JURISDICCIONAL_TYPE)) {
			responseObjetivosJurisdiccionales = ((ObjetivoJurisdiccionalRespMsg) response).getObjetivosJurisdiccionales();
		}
		return getObjetivoJurisdiccionalFromResponse(responseObjetivosJurisdiccionales);
	}

	@Override
	public ObjetivoJurisdiccional updateObjetivoJurisdiccional(@RequestBody ObjetivoJurisdiccional objetivoJurisdiccional)
			throws ESBException, JMSException {
		
		ObjetivoJurisdiccionalReqMsg reqMsg = new ObjetivoJurisdiccionalReqMsg();
		reqMsg.setObjetivoJurisdiccional(objetivoJurisdiccional);
		List<ObjetivoJurisdiccional> responseObjetivosJurisdiccionales = new ArrayList<ObjetivoJurisdiccional>();

		getLogger().debug("Mensaje creado para actualizar un ObjetivoJurisdiccional : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE);
		if (response.getEventType().equalsIgnoreCase(ObjetivoJurisdiccionalRespMsg.OBJETIVO_JURISDICCIONAL_TYPE)) {
			responseObjetivosJurisdiccionales = ((ObjetivoJurisdiccionalRespMsg) response).getObjetivosJurisdiccionales();
		}
		return getObjetivoJurisdiccionalFromResponse(responseObjetivosJurisdiccionales);		 
	}

	@Override
	public void deleteObjetivoJurisdiccional(String id)
			throws ESBException, JMSException {
		
		ObjetivoJurisdiccionalReqMsg reqMsg = new ObjetivoJurisdiccionalReqMsg();
		reqMsg.setId(Long.parseLong(id));

		getLogger().debug("Mensaje creado para borrar una Jurisdiccion : {}",reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE);
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private List<ObjetivoJurisdiccional> getObjetivosJurisdiccionalesFromReqMsg(
			ObjetivoJurisdiccionalReqMsg reqMsg) throws ESBException, JMSException {

		getLogger()
				.debug("Mensaje creado para obtener todos los Objetivos Jurisdiccionales: {}",reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE);

		List<ObjetivoJurisdiccional> objetivosJurisdiccionales = null;
		if (response.getEventType().equalsIgnoreCase(ObjetivoJurisdiccionalRespMsg.OBJETIVO_JURISDICCIONAL_TYPE)) {
			objetivosJurisdiccionales = ((ObjetivoJurisdiccionalRespMsg) response).getObjetivosJurisdiccionales();
			LOGGER.debug("Obteninendo las jurisdicciones de la respues del BUS de servicios: {}",
					objetivosJurisdiccionales.toString());
		}
		return objetivosJurisdiccionales;
	}

	private ObjetivoJurisdiccional getObjetivoJurisdiccionalFromResponse(List<ObjetivoJurisdiccional> objetivosJurisdiccionales) {
		if (!objetivosJurisdiccionales.isEmpty()) {
			return objetivosJurisdiccionales.get(0);
		} else {
			return null;
		}
	}

}
