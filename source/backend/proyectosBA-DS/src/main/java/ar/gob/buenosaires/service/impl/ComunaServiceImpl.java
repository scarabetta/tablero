package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.Comuna;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.ComunaReqMsg;
import ar.gob.buenosaires.esb.domain.message.ComunaRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.ComunaService;


@Service
public class ComunaServiceImpl implements ComunaService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ComunaServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<Comuna> getComunas() throws ESBException, JMSException {
		ComunaReqMsg reqMsg = new ComunaReqMsg();

		return getComunasFromReqMsg(reqMsg);
	}

//	@Override
//	public Comuna getComunaPorId(String id) throws ESBException, JMSException {
//		ComunaReqMsg reqMsg = new ComunaReqMsg();
//		reqMsg.setId(Long.parseLong(id));
//
//		List<Comuna> Comunaes = getComunaesFromReqMsg(reqMsg);
//		return getComunaFromResponse(Comunaes);
//	}
//
//	@Override
//	public Comuna getComunaesByName(String nombre) throws ESBException, JMSException {
//		ComunaReqMsg reqMsg = new ComunaReqMsg();
//		reqMsg.setName(nombre);
//		
//		List<Comuna> Comunaes = getComunaesFromReqMsg(reqMsg);
//		return getComunaFromResponse(Comunaes);
//	}
//	
//	@Override
//	public Comuna getComunaesPorCodigo(String codigo) throws ESBException, JMSException {
//		ComunaReqMsg reqMsg = new ComunaReqMsg();
//		reqMsg.setCodigo(codigo);
//		
//		List<Comuna> Comunaes = getComunaesFromReqMsg(reqMsg);
//		return getComunaFromResponse(Comunaes);
//	}
//
//	@Override
//	public void createComunaes(@RequestBody Comuna Comuna) throws ESBException, JMSException {		
//		ComunaReqMsg reqMsg = new ComunaReqMsg();
//		reqMsg.setComuna(Comuna);
//
//		getLogger().debug("Mensaje creado para crear una Comuna : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_CREATE);		
//	}
//
//	@Override
//	public void updateComunaes(@RequestBody Comuna Comuna) throws ESBException, JMSException {
//		ComunaReqMsg reqMsg = new ComunaReqMsg();
//		reqMsg.setComuna(Comuna);
//
//		getLogger().debug("Mensaje creado para actualizar una Comuna : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_UPDATE);		
//	}
//	
//	@Override
//	public void deleteComuna(@RequestBody Comuna Comuna) throws ESBException, JMSException {
//		ComunaReqMsg reqMsg = new ComunaReqMsg();
//		reqMsg.setComuna(Comuna);
//
//		getLogger().debug("Mensaje creado para borrar una Comuna : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE);		
//	}
//
	public static Logger getLogger() {
		return LOGGER;
	}
	
	private List<Comuna> getComunasFromReqMsg(ComunaReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener una Comuna : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, ComunaRespMsg.class);

		List<Comuna> comunas = null;
		if (response.getEventType().equalsIgnoreCase(ComunaRespMsg.COMUNA_TYPE)) {
			comunas = ((ComunaRespMsg) response).getComunas();
			LOGGER.debug("Obteninendo las Comunaes de la respues del BUS de servicios: {}",
					comunas.toString());
		}
		return comunas;
	}
	
	private Comuna getComunaFromResponse(List<Comuna> comunas) {
		if (!comunas.isEmpty()) {
			return comunas.get(0);
		} else {
			return null;
		}
	}
}
