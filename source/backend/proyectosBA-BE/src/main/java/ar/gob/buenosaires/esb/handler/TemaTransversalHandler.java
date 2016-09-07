package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.TemaTransversalReqMsg;
import ar.gob.buenosaires.esb.domain.message.TemaTransversalRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.TemaTransversalService;

public class TemaTransversalHandler extends AbstractBaseEventHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(TemaTransversalHandler.class);
	
	@Autowired
	private TemaTransversalService service;

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(TemaTransversalReqMsg.TEMA_TRANSVERSAL_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, TemaTransversalService.class);
		final TemaTransversalRespMsg response = new TemaTransversalRespMsg();
		final TemaTransversalReqMsg request = (TemaTransversalReqMsg) event.getObj();
		List<TemaTransversal> temasTransversales = new ArrayList<TemaTransversal>();

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveTemasTransversales(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			temasTransversales.add(service.createTemaTransversal(request.getTemaTransversal()));
			response.setTemasTransversales(temasTransversales);
			event.setObj(response);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			temasTransversales.add(service.updateTemaTransversal(request.getTemaTransversal()));
			response.setTemasTransversales(temasTransversales);
			event.setObj(response);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteTemaTransversal(request.getId());
		} else {

		}
		logResponseMessage(event, TemaTransversalService.class);
	}

	private void retrieveTemasTransversales(ESBEvent event, final TemaTransversalRespMsg response, final TemaTransversalReqMsg request) {
		List<TemaTransversal> temasTransversales = new ArrayList<TemaTransversal>();

		if (request.getId() != null) {
			temasTransversales.add(service.getTemaTransversalPorId(request.getId()));
		} else {
			temasTransversales = service.getTemasTransversales();
		}
		response.setTemasTransversales(temasTransversales);
		event.setObj(response);
	}
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
