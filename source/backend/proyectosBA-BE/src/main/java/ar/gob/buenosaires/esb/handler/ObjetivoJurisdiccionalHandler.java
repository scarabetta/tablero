package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ObjetivoJurisdiccionalReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoJurisdiccionalRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.ObjetivoJurisdiccionalService;

public class ObjetivoJurisdiccionalHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ObjetivoJurisdiccionalHandler.class);

	@Autowired
	private ObjetivoJurisdiccionalService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ObjetivoJurisdiccionalService.class);
		final ObjetivoJurisdiccionalReqMsg request = (ObjetivoJurisdiccionalReqMsg) JMSUtil.crearObjeto(getReader(ObjetivoJurisdiccionalReqMsg.class), event.getXml());

		final ObjetivoJurisdiccionalRespMsg response = new ObjetivoJurisdiccionalRespMsg();
		event.setObj(response);
		List<ObjetivoJurisdiccional> objetivosJurisdiccionales = new ArrayList<ObjetivoJurisdiccional>();
		response.setObjetivosJurisdiccionales(objetivosJurisdiccionales);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveObjetivosJurisdiccionales(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			objetivosJurisdiccionales.add(service.createObjetivoJurisdiccional(request.getObjetivoJurisdiccional()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			objetivosJurisdiccionales.add(service.updateObjetivoJurisdiccional(request.getObjetivoJurisdiccional()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteObjetivoJurisdiccional(request.getId());
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para el servicio de Objetivo Jurisdiccional");
		}
		logResponseMessage(event, ObjetivoJurisdiccionalService.class);
	}

	private void retrieveObjetivosJurisdiccionales(final ObjetivoJurisdiccionalRespMsg response, final ObjetivoJurisdiccionalReqMsg request) {
		List<ObjetivoJurisdiccional> objetivosJurisdiccionales = new ArrayList<ObjetivoJurisdiccional>();

		if (request.getId() != null) {
			objetivosJurisdiccionales.add(service.getObjetivoJurisdiccionalPorId(request.getId()));
		} else if (request.getName() != null) {
			objetivosJurisdiccionales.add(service.getObjetivoJurisdiccionalPorNombre(request.getName()));
		} else if (request.getCodigo() != null) {
			objetivosJurisdiccionales.add(service.getObjetivoJurisdiccionalPorCodigo(request.getCodigo()));
		} else {
			objetivosJurisdiccionales = service.getObjetivosJurisdiccionales();
		}
		response.setObjetivosJurisdiccionales(objetivosJurisdiccionales);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ObjetivoJurisdiccionalReqMsg.OBJETIVO_JURISDICCIONAL_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
