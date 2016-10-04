package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.EtiquetasMsg;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.EtiquetasReqMsg;
import ar.gob.buenosaires.esb.domain.message.EtiquetasRespMsg;
import ar.gob.buenosaires.esb.domain.message.ProyectoReqMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.EtiquetaService;

@Service
public class EtiquetasServiceImpl implements EtiquetaService {

	private final static Logger LOGGER = LoggerFactory.getLogger(EtiquetasServiceImpl.class);

	@Autowired
	private EsbService esbService;
	
	@Override
	public EtiquetasMsg getEtiquetas() throws ESBException, JMSException {
		EtiquetasReqMsg reqMsg = new EtiquetasReqMsg();

		return getEtiquetasFromReqMsg(reqMsg);
	}
	

	@Override
	public EtiquetasMsg getEtiquetasPorProyecto(String id) throws ESBException, JMSException {
		EtiquetasReqMsg reqMsg = new EtiquetasReqMsg();
		reqMsg.setId(Long.parseLong(id));

		return getEtiquetasFromReqMsg(reqMsg);
	}
	
	private EtiquetasMsg getEtiquetasFromReqMsg(EtiquetasReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener todas las etiquetas : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, EtiquetasRespMsg.class);

		EtiquetasMsg etiquetas = null;
		if (response.getEventType().equalsIgnoreCase(EtiquetasRespMsg.ETIQUETAS_TYPE)) {
			etiquetas = ((EtiquetasRespMsg) response).getEtiquetas();
			LOGGER.debug("Obteninendo las etiquetas de la respues del BUS de servicios: {}", etiquetas.toString());
		}
		return etiquetas ;
	}
	public static Logger getLogger() {
		return LOGGER;
	}
}
