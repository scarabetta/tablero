package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.Comuna;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ComunaReqMsg;
import ar.gob.buenosaires.esb.domain.message.ComunaRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ComunaService;

public class ComunaHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ComunaHandler.class);

	@Autowired
	private ComunaService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ComunaService.class);
		final ComunaRespMsg comunaResponse = new ComunaRespMsg();
		final ComunaReqMsg comunaRequest = (ComunaReqMsg) event.getObj(); 

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveComunas(event, comunaResponse, comunaRequest);
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
//			service.createComuna(request.getComuna());
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
//			service.updateComuna(request.getComuna());
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
//			service.deleteComuna(request.getComuna());
//		} else {

		}
		logResponseMessage(event, ComunaService.class);
	}

	private void retrieveComunas(ESBEvent event,
			final ComunaRespMsg response, final ComunaReqMsg request) {
		List<Comuna> comunas = new ArrayList<Comuna>();

		if (request.getId() != null) {
//			Comunaes.add(service.getComunaPorId(request.getId()));
		} else if (StringUtils.isNotBlank(request.getName())) {
//			Comunaes.add(service.getComunaPorNombre(request.getName()));
		} else if (StringUtils.isNotBlank(request.getCodigo())) {
//			Comunaes.add(service.getComunaPorCodigo(request.getCodigo()));
		} else {
			comunas = service.getComunas();
		}
		response.setComunas(comunas);
		event.setObj(response);
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
