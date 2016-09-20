package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.OtrasEtiquetasReqMsg;
import ar.gob.buenosaires.esb.domain.message.OtrasEtiquetasRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.OtrasEtiquetasService;

public class OtrasEtiquetasHandler extends AbstractBaseEventHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(OtrasEtiquetasHandler.class);
	
	@Autowired
	private OtrasEtiquetasService service;

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(OtrasEtiquetasReqMsg.OTRAS_ETIQUETAS_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, OtrasEtiquetasService.class);
		final OtrasEtiquetasReqMsg request = (OtrasEtiquetasReqMsg) JMSUtil.crearObjeto(event.getXml(), OtrasEtiquetasReqMsg.class);

		final OtrasEtiquetasRespMsg response = new OtrasEtiquetasRespMsg();
		event.setObj(response);
		List<OtraEtiqueta> otrasEtiquetas = new ArrayList<OtraEtiqueta>();
		response.setOtrasEtiquetas(otrasEtiquetas);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveOtrasEtiquetas(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			otrasEtiquetas.add(service.createOtrasEtiquetas(request.getOtraEtiqueta()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			otrasEtiquetas.add(service.updateOtrasEtiquetas(request.getOtraEtiqueta()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteOtrasEtiquetas(request.getId());
		} else {

		}
		logResponseMessage(event, OtrasEtiquetasService.class);
	}

	private void retrieveOtrasEtiquetas(ESBEvent event, final OtrasEtiquetasRespMsg response, final OtrasEtiquetasReqMsg request) {
		List<OtraEtiqueta> otrasEtiquetas = new ArrayList<OtraEtiqueta>();

		if (request.getId() != null) {
			otrasEtiquetas.add(service.getOtrasEtiquetasPorId(request.getId()));
		} else {
			otrasEtiquetas = service.getOtrasEtiquetas();
		}
		response.setOtrasEtiquetas(otrasEtiquetas);
		event.setObj(response);
	}
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
