package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.PoblacionMetaReqMsg;
import ar.gob.buenosaires.esb.domain.message.PoblacionMetaRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.PoblacionMetaService;

public class PoblacionMetaHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(PoblacionMetaHandler.class);

	@Autowired
	private PoblacionMetaService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, PoblacionMetaService.class);
		final PoblacionMetaReqMsg request = (PoblacionMetaReqMsg) JMSUtil.crearObjeto(event.getXml(), PoblacionMetaReqMsg.class);

		final PoblacionMetaRespMsg response = new PoblacionMetaRespMsg();
		event.setObj(response);
		List<PoblacionMeta> metas = new ArrayList<PoblacionMeta>();
		response.setPoblacionesMeta(metas);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrievePoblacionesMeta(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			metas.add(service.createPoblacionMeta(request.getPoblacionMeta()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			metas.add(service.updatePoblacionMeta(request.getPoblacionMeta()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deletePoblacionMeta(request.getPoblacionMeta());
		} else {
			throw new ESBException("La accion: " + event.getAction() + ", no existe para el servicio de Poblacion Meta");
		}
		logResponseMessage(event, PoblacionMetaService.class);
	}

	private void retrievePoblacionesMeta(final PoblacionMetaRespMsg response, final PoblacionMetaReqMsg request) {
		List<PoblacionMeta> poblacionesMeta = new ArrayList<PoblacionMeta>();

		if (request.getId() != null) {
			poblacionesMeta.add(service.getPoblacionMetaPorId(request.getId()));
		} else if (request.getName() != null) {
			poblacionesMeta.add(service.getPoblacionMetaPorNombre(request.getName()));
		} else {
			poblacionesMeta = service.getPoblacionesMeta();
		}
		response.setPoblacionesMeta(poblacionesMeta);
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
