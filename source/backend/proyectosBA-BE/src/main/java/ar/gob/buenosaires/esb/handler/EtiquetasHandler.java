package ar.gob.buenosaires.esb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.EtiquetaResponse;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.EtiquetasReqMsg;
import ar.gob.buenosaires.esb.domain.message.EtiquetasRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.EtiquetasService;

public class EtiquetasHandler extends AbstractBaseEventHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(EtiquetasHandler.class);
	
	@Autowired
	private EtiquetasService service;

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(EtiquetasReqMsg.ETIQUETAS_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, EtiquetasService.class);
		final EtiquetasReqMsg request = (EtiquetasReqMsg) JMSUtil.crearObjeto(event.getXml(), EtiquetasReqMsg.class);

		final EtiquetasRespMsg response = new EtiquetasRespMsg();
		event.setObj(response);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveEtiquetas(event, response, request);
		} 
		logResponseMessage(event, EtiquetasService.class);
	}

	private void retrieveEtiquetas(ESBEvent event, final EtiquetasRespMsg response, final EtiquetasReqMsg request) {
		EtiquetaResponse etiquetas = new EtiquetaResponse();
		etiquetas = service.getEtiquetas();
		
		response.setEtiquetas(etiquetas);
		event.setObj(response);
	}
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
