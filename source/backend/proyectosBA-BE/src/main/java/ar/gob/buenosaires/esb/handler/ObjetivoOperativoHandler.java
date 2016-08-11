package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ObjetivoJurisdiccionalRespMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoOperativoReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoOperativoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ObjetivoOperativoService;

public class ObjetivoOperativoHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ObjetivoOperativoHandler.class);

	@Autowired
	private ObjetivoOperativoService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ObjetivoOperativoService.class);
		final ObjetivoOperativoRespMsg response = new ObjetivoOperativoRespMsg();
		final ObjetivoOperativoReqMsg request = (ObjetivoOperativoReqMsg) event.getObj(); 

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveObjetivosOperativos(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			createObjetivoOperativo(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			updateObjetivoOperativo(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteObjetivoOperativo(request.getId());
		} else {

		}
		logResponseMessage(event, ObjetivoOperativoService.class);
	}
	
	private void createObjetivoOperativo(ESBEvent event,
			final ObjetivoOperativoRespMsg response, final ObjetivoOperativoReqMsg request) throws ESBException {		
		List<ObjetivoOperativo> objetivosOperativos = new ArrayList<ObjetivoOperativo>();
		objetivosOperativos.add(service.createObjetivoOperativo(request.getObjetivoOperativo()));
		
		addObjetivosOperativosToResponse(event, response, objetivosOperativos);
	}
	
	private void updateObjetivoOperativo(ESBEvent event,
			final ObjetivoOperativoRespMsg response, final ObjetivoOperativoReqMsg request) throws ESBException {		
		List<ObjetivoOperativo> objetivosOperativos = new ArrayList<ObjetivoOperativo>();
		objetivosOperativos.add(service.updateObjetivoOperativo(request.getObjetivoOperativo()));
		
		addObjetivosOperativosToResponse(event, response, objetivosOperativos);
	}

	private void retrieveObjetivosOperativos(ESBEvent event,
			final ObjetivoOperativoRespMsg response, final ObjetivoOperativoReqMsg request) {
		List<ObjetivoOperativo> objetivosOperativos = new ArrayList<ObjetivoOperativo>();

		if (request.getId() != null) {
			objetivosOperativos.add(service.getObjetivoOperativoPorId(request.getId()));
		} else if (request.getName() != null) {
			objetivosOperativos.add(service.getObjetivoOperativoPorNombre(request.getName()));
		} else if (request.getCodigo() != null) {
			objetivosOperativos.add(service.getObjetivoOperativoPorCodigo(request.getCodigo()));
		} else {
			objetivosOperativos = service.getObjetivosOperativos();
		}
		response.setObjetivosOperativos(objetivosOperativos);
		event.setObj(response);
	}
	
	private void addObjetivosOperativosToResponse(ESBEvent event, final ObjetivoOperativoRespMsg response, 
			List<ObjetivoOperativo> objetivosOperativos) {
		response.setObjetivosOperativos(objetivosOperativos);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ObjetivoOperativoReqMsg.OBJETIVO_OPERATIVO_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}