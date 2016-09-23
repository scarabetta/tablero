package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.Area;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.AreaReqMsg;
import ar.gob.buenosaires.esb.domain.message.AreaRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.AreaService;

public class AreaHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(AreaHandler.class);

	@Autowired
	private AreaService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, AreaService.class);
		final AreaReqMsg areaRequest = (AreaReqMsg) JMSUtil.crearObjeto(event.getXml(), AreaReqMsg.class);

		final AreaRespMsg areaResponse = new AreaRespMsg();
		event.setObj(areaResponse);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveAreas(event, areaResponse, areaRequest);
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para el servicio de Areas.");
		}
		logResponseMessage(event, AreaService.class);
	}

	private void retrieveAreas(ESBEvent event, final AreaRespMsg response, final AreaReqMsg request) {
		List<Area> areas = new ArrayList<Area>();

		if (request.getId() != null) {
			areas.add(service.getAreaPorId(request.getId()));
		} else if (request.getName() != null) {
			if (request.getIdJurisdiccion() == null) {
				areas = service.getAreasPorNombre(request.getName());
			} else {
				areas.add(service.getAreaPorNombreYIdJurisdiccion(request.getName(), request.getIdJurisdiccion()));
			}
		} else {
			areas = service.getAreas();
		}
		response.setAreas(areas);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(AreaReqMsg.AREA_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
