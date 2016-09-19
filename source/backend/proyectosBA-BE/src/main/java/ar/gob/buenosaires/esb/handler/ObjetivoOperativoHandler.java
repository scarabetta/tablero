package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ObjetivoOperativoReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObjetivoOperativoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.ObjetivoOperativoService;

public class ObjetivoOperativoHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ObjetivoOperativoHandler.class);

	@Autowired
	private ObjetivoOperativoService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ObjetivoOperativoService.class);
		final ObjetivoOperativoReqMsg request = (ObjetivoOperativoReqMsg) JMSUtil.crearObjeto(event.getXml(), ObjetivoOperativoReqMsg.class); 

		final ObjetivoOperativoRespMsg response = new ObjetivoOperativoRespMsg();
		event.setObj(response);
		List<ObjetivoOperativo> objetivosOperativos = new ArrayList<ObjetivoOperativo>();
		response.setObjetivosOperativos(objetivosOperativos);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveObjetivosOperativos(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			objetivosOperativos.add(service.createObjetivoOperativo(request.getObjetivoOperativo()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			objetivosOperativos.add(service.updateObjetivoOperativo(request.getObjetivoOperativo()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteObjetivoOperativo(request.getId());
		} else {
			throw new ESBException("La accion: " + event.getAction() + ", no existe para el servicio de Objetivo Operativo");
		}
		logResponseMessage(event, ObjetivoOperativoService.class);
	}
	
	private void retrieveObjetivosOperativos(final ObjetivoOperativoRespMsg response, final ObjetivoOperativoReqMsg request) {
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
