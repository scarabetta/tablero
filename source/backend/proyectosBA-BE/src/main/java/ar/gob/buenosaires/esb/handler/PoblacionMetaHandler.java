package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.PoblacionMetaReqMsg;
import ar.gob.buenosaires.esb.domain.message.PoblacionMetaRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.PoblacionMetaService;

public class PoblacionMetaHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(PoblacionMetaHandler.class);

	@Autowired
	private PoblacionMetaService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, PoblacionMetaService.class);
		final PoblacionMetaRespMsg response = new PoblacionMetaRespMsg();
		final PoblacionMetaReqMsg request = (PoblacionMetaReqMsg) event.getObj(); 

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrievePoblacionesMeta(event, response, request);
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
//			service.createPoblacionMeta(request.getPoblacionMeta());
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
//			service.updatePoblacionMeta(request.getPoblacionMeta());
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
//			service.deletePoblacionMeta(request.getPoblacionMeta());
		} else {

		}
		logResponseMessage(event, PoblacionMetaService.class);
	}

	private void retrievePoblacionesMeta(ESBEvent event,
			final PoblacionMetaRespMsg response, final PoblacionMetaReqMsg request) {
		List<PoblacionMeta> poblacionesMeta = new ArrayList<PoblacionMeta>();

		if (request.getId() != null) {
//			poblacionesMeta.add(service.getPoblacionMetaPorId(request.getId()));
		} else if (StringUtils.isNotBlank(request.getName())) {
//			poblacionesMeta.add(service.getPoblacionMetaPorNombre(request.getName()));
		} else if (StringUtils.isNotBlank(request.getCodigo())) {
//			poblacionesMeta.add(service.getPoblacionMetaPorCodigo(request.getCodigo()));
		} else {
			poblacionesMeta = service.getPoblacionesMeta();
		}
		response.setPoblacionesMeta(poblacionesMeta);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(PoblacionMetaReqMsg.POBLACION_META_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
