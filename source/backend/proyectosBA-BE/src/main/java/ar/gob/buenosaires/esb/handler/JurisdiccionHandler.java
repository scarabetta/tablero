package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.JurisdiccionReqMsg;
import ar.gob.buenosaires.esb.domain.message.JurisdiccionRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.JurisdiccionService;

public class JurisdiccionHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(JurisdiccionHandler.class);

	@Autowired
	private JurisdiccionService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, JurisdiccionService.class);
		final JurisdiccionRespMsg response = new JurisdiccionRespMsg();
		final JurisdiccionReqMsg request = (JurisdiccionReqMsg) event.getObj(); 

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveJurisdicciones(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			service.createJurisdiccion(request.getJurisdiccion());
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			service.updateJurisdiccion(request.getJurisdiccion());
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteJurisdiccion(request.getId());
		} else {

		}
		logResponseMessage(event, JurisdiccionService.class);
	}

	private void retrieveJurisdicciones(ESBEvent event,
			final JurisdiccionRespMsg response, final JurisdiccionReqMsg request) {
		List<Jurisdiccion> jurisdicciones = new ArrayList<Jurisdiccion>();

		if (request.getId() != null) {
			jurisdicciones.add(service.getJurisdiccionPorId(request.getId()));
		} else if (StringUtils.isNotBlank(request.getName())) {
			jurisdicciones.add(service.getJurisdiccionPorNombre(request.getName()));
		} else if (StringUtils.isNotBlank(request.getCodigo())) {
			jurisdicciones.add(service.getJurisdiccionPorCodigo(request.getCodigo()));
		} else {
			jurisdicciones = service.getJurisdicciones();
		}
		response.setJurisdicciones(jurisdicciones);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(
				JurisdiccionReqMsg.JURISDICCION_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
