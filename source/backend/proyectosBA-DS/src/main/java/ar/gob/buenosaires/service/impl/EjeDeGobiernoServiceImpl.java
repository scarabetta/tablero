package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.EjeDeGobiernoReqMsg;
import ar.gob.buenosaires.esb.domain.message.EjeDeGobiernoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.EjeDeGobiernoService;

@Service
public class EjeDeGobiernoServiceImpl implements EjeDeGobiernoService {

	private final static Logger LOGGER = LoggerFactory.getLogger(EjeDeGobiernoServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<EjeDeGobierno> getEjesDeGobierno() throws ESBException, JMSException {
		EjeDeGobiernoReqMsg reqMsg = new EjeDeGobiernoReqMsg();

		return getEjesDeGobiernoFromReqMsg(reqMsg);
	}

	@Override
	public EjeDeGobierno getEjeDeGobiernoPorId(String id) throws ESBException, JMSException {
		EjeDeGobiernoReqMsg reqMsg = new EjeDeGobiernoReqMsg();
		reqMsg.setId(Long.parseLong(id));

		List<EjeDeGobierno> ejesDeGobierno = getEjesDeGobiernoFromReqMsg(reqMsg);
		return getEjeDeGobiernoFromResponse(ejesDeGobierno);
	}

	@Override
	public EjeDeGobierno getEjeDeGobiernoByName(String nombre) throws ESBException, JMSException {
		EjeDeGobiernoReqMsg reqMsg = new EjeDeGobiernoReqMsg();
		reqMsg.setName(nombre);
		
		List<EjeDeGobierno> ejesDeGobierno = getEjesDeGobiernoFromReqMsg(reqMsg);
		return getEjeDeGobiernoFromResponse(ejesDeGobierno);
	}

//	@Override
//	public void createEjeDeGobierno(@RequestBody EjeDeGobierno EjeDeGobierno) throws ESBException, JMSException {		
//		EjeDeGobiernoReqMsg reqMsg = new EjeDeGobiernoReqMsg();
//		reqMsg.setEjeDeGobierno(EjeDeGobierno);
//
//		getLogger().debug("Mensaje creado para crear una EjeDeGobierno : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_CREATE);		
//	}
//
//	@Override
//	public void updateEjeDeGobierno(@RequestBody EjeDeGobierno EjeDeGobierno) throws ESBException, JMSException {
//		EjeDeGobiernoReqMsg reqMsg = new EjeDeGobiernoReqMsg();
//		reqMsg.setEjeDeGobierno(EjeDeGobierno);
//
//		getLogger().debug("Mensaje creado para actualizar una EjeDeGobierno : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_UPDATE);		
//	}
//	
//	@Override
//	public void deleteEjeDeGobierno(@RequestBody EjeDeGobierno EjeDeGobierno) throws ESBException, JMSException {
//		EjeDeGobiernoReqMsg reqMsg = new EjeDeGobiernoReqMsg();
//		reqMsg.setEjeDeGobierno(EjeDeGobierno);
//
//		getLogger().debug("Mensaje creado para borrar una EjeDeGobierno : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE);		
//	}

	public static Logger getLogger() {
		return LOGGER;
	}
	
	private List<EjeDeGobierno> getEjesDeGobiernoFromReqMsg(EjeDeGobiernoReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener una EjeDeGobierno : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, EjeDeGobiernoRespMsg.class);

		List<EjeDeGobierno> ejesDeGobierno = null;
		if (response.getEventType().equalsIgnoreCase(EjeDeGobiernoRespMsg.EJE_DE_GOBIERNO_TYPE)) {
			ejesDeGobierno = ((EjeDeGobiernoRespMsg) response).getEjesDeGobierno();
			LOGGER.debug("Obteninendo las EjeDeGobiernoes de la respues del BUS de servicios: {}",
					ejesDeGobierno.toString());
		}
		return ejesDeGobierno;
	}
	
	private EjeDeGobierno getEjeDeGobiernoFromResponse(
			List<EjeDeGobierno> ejesDeGobierno) {
		if (!ejesDeGobierno.isEmpty()) {
			return ejesDeGobierno.get(0);
		} else {
			return null;
		}
	}
}
