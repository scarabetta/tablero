package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.PoblacionMetaReqMsg;
import ar.gob.buenosaires.esb.domain.message.PoblacionMetaRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.PoblacionMetaService;

@Service
public class PoblacionMetaServiceImpl implements PoblacionMetaService {

	private final static Logger LOGGER = LoggerFactory.getLogger(PoblacionMetaServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<PoblacionMeta> getPoblacionesMeta() throws ESBException, JMSException {
		PoblacionMetaReqMsg reqMsg = new PoblacionMetaReqMsg();

		return getPoblacionesMetaFromReqMsg(reqMsg);
	}

//	@Override
//	public PoblacionMeta getPoblacionMetaPorId(String id) throws ESBException, JMSException {
//		PoblacionMetaReqMsg reqMsg = new PoblacionMetaReqMsg();
//		reqMsg.setId(Long.parseLong(id));
//
//		List<PoblacionMeta> PoblacionMetaes = getPoblacionMetaesFromReqMsg(reqMsg);
//		return getPoblacionMetaFromResponse(PoblacionMetaes);
//	}
//
//	@Override
//	public PoblacionMeta getPoblacionMetaesByName(String nombre) throws ESBException, JMSException {
//		PoblacionMetaReqMsg reqMsg = new PoblacionMetaReqMsg();
//		reqMsg.setName(nombre);
//		
//		List<PoblacionMeta> PoblacionMetaes = getPoblacionMetaesFromReqMsg(reqMsg);
//		return getPoblacionMetaFromResponse(PoblacionMetaes);
//	}
//	
//	@Override
//	public PoblacionMeta getPoblacionMetaesPorCodigo(String codigo) throws ESBException, JMSException {
//		PoblacionMetaReqMsg reqMsg = new PoblacionMetaReqMsg();
//		reqMsg.setCodigo(codigo);
//		
//		List<PoblacionMeta> PoblacionMetaes = getPoblacionMetaesFromReqMsg(reqMsg);
//		return getPoblacionMetaFromResponse(PoblacionMetaes);
//	}
//
//	@Override
//	public void createPoblacionMetaes(@RequestBody PoblacionMeta PoblacionMeta) throws ESBException, JMSException {		
//		PoblacionMetaReqMsg reqMsg = new PoblacionMetaReqMsg();
//		reqMsg.setPoblacionMeta(PoblacionMeta);
//
//		getLogger().debug("Mensaje creado para crear una PoblacionMeta : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_CREATE);		
//	}
//
//	@Override
//	public void updatePoblacionMetaes(@RequestBody PoblacionMeta PoblacionMeta) throws ESBException, JMSException {
//		PoblacionMetaReqMsg reqMsg = new PoblacionMetaReqMsg();
//		reqMsg.setPoblacionMeta(PoblacionMeta);
//
//		getLogger().debug("Mensaje creado para actualizar una PoblacionMeta : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_UPDATE);		
//	}
//	
//	@Override
//	public void deletePoblacionMeta(@RequestBody PoblacionMeta PoblacionMeta) throws ESBException, JMSException {
//		PoblacionMetaReqMsg reqMsg = new PoblacionMetaReqMsg();
//		reqMsg.setPoblacionMeta(PoblacionMeta);
//
//		getLogger().debug("Mensaje creado para borrar una PoblacionMeta : {}", reqMsg.toString());
//		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE);		
//	}

	public static Logger getLogger() {
		return LOGGER;
	}
	
	private List<PoblacionMeta> getPoblacionesMetaFromReqMsg(PoblacionMetaReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener una PoblacionMeta : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, PoblacionMetaRespMsg.class);

		List<PoblacionMeta> poblacionMeta = null;
		if (response.getEventType().equalsIgnoreCase(PoblacionMetaRespMsg.POBLACION_META_TYPE)) {
			poblacionMeta = ((PoblacionMetaRespMsg) response).getPoblacionesMeta();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo las PoblacionMetaes de la respuesta del BUS de servicios: {}",
					poblacionMeta.toString());
			} else {
				getLogger().info("Obteninendo las PoblacionMetaes de la respuesta del BUS de servicios");
			}
		}
		return poblacionMeta;
	}
	
	private PoblacionMeta getPoblacionMetaFromResponse(List<PoblacionMeta> poblacionesMeta) {
		if (!poblacionesMeta.isEmpty()) {
			return poblacionesMeta.get(0);
		} else {
			return null;
		}
	}
}
