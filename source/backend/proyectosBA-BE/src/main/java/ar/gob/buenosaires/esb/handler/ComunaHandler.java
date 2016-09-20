package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.Comuna;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ComunaReqMsg;
import ar.gob.buenosaires.esb.domain.message.ComunaRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.ComunaService;

public class ComunaHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ComunaHandler.class);

	@Autowired
	private ComunaService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ComunaService.class);
		final ComunaReqMsg comunaRequest = (ComunaReqMsg) JMSUtil.crearObjeto(event.getXml(), ComunaReqMsg.class);

		final ComunaRespMsg comunaResponse = new ComunaRespMsg();
		event.setObj(comunaResponse);
		List<Comuna> comunas = new ArrayList<Comuna>();
		comunaResponse.setComunas(comunas);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveComunas(comunaResponse, comunaRequest);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			comunas.add(service.createComuna(comunaRequest.getComuna()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			comunas.add(service.updateComuna(comunaRequest.getComuna()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteComuna(comunaRequest.getComuna());
		} else {
			throw new ESBException("La accion: " + event.getAction() + ", no existe para el servicio de Comuna.");
		}
		logResponseMessage(event, ComunaService.class);
	}

	private void retrieveComunas(final ComunaRespMsg response, final ComunaReqMsg request) {
		List<Comuna> comunas = new ArrayList<Comuna>();

		if (request.getId() != null) {
			comunas.add(service.getComunaPorId(request.getId()));
		} else if (request.getName() != null) {
			comunas.add(service.getComunaPorNombre(request.getName()));
		} else {
			comunas = service.getComunas();
		}
		response.setComunas(comunas);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ComunaReqMsg.COMUNA_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
