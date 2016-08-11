package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.JurisdiccionReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoJurisdiccionalReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoJurisdiccionalRespMsg;
import ar.gob.buenosaires.esb.domain.message.ProyectoReqMsg;
import ar.gob.buenosaires.esb.domain.message.ProyectoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.JurisdiccionService;
import ar.gob.buenosaires.service.ObjetivoJurisdiccionalService;

public class ObjetivoJurisdiccionalHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ObjetivoJurisdiccionalHandler.class);

	@Autowired
	private ObjetivoJurisdiccionalService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ObjetivoJurisdiccionalService.class);
		final ObjetivoJurisdiccionalRespMsg response = new ObjetivoJurisdiccionalRespMsg();
		final ObjetivoJurisdiccionalReqMsg request = (ObjetivoJurisdiccionalReqMsg) event.getObj(); 

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveObjetivosJurisdiccionales(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			createObjetivoJurisdiccional(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			updateObjetivoJurisdiccional(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteObjetivoJurisdiccional(request.getId());
		} else {

		}
		logResponseMessage(event, ObjetivoJurisdiccionalService.class);
	}
	
	private void createObjetivoJurisdiccional(ESBEvent event,
			final ObjetivoJurisdiccionalRespMsg response, final ObjetivoJurisdiccionalReqMsg request) throws ESBException {		
		List<ObjetivoJurisdiccional> objetivosJurisdiccionales = new ArrayList<ObjetivoJurisdiccional>();
		objetivosJurisdiccionales.add(service.createObjetivoJurisdiccional(request.getObjetivoJurisdiccional()));
		
		addObjetivosJurisdiccionalesToResponse(event, response, objetivosJurisdiccionales);
	}
	
	private void updateObjetivoJurisdiccional(ESBEvent event,
			final ObjetivoJurisdiccionalRespMsg response, final ObjetivoJurisdiccionalReqMsg request) throws ESBException {		
		List<ObjetivoJurisdiccional> objetivosJurisdiccionales = new ArrayList<ObjetivoJurisdiccional>();
		objetivosJurisdiccionales.add(service.updateObjetivoJurisdiccional(request.getObjetivoJurisdiccional()));
		
		addObjetivosJurisdiccionalesToResponse(event, response, objetivosJurisdiccionales);
	}

	private void retrieveObjetivosJurisdiccionales(ESBEvent event,
			final ObjetivoJurisdiccionalRespMsg response, final ObjetivoJurisdiccionalReqMsg request) {
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
		addObjetivosJurisdiccionalesToResponse(event, response, objetivosJurisdiccionales);
	}
	
	private void addObjetivosJurisdiccionalesToResponse(ESBEvent event, final ObjetivoJurisdiccionalRespMsg response, 
			List<ObjetivoJurisdiccional> objetivosJurisdiccionales) {
		response.setObjetivosJurisdiccionales(objetivosJurisdiccionales);
		event.setObj(response);
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
