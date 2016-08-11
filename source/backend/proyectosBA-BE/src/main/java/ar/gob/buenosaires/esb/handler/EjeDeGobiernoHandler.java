package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.EjeDeGobiernoReqMsg;
import ar.gob.buenosaires.esb.domain.message.EjeDeGobiernoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.EjeDeGobiernoService;

public class EjeDeGobiernoHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(EjeDeGobiernoHandler.class);

	@Autowired
	private EjeDeGobiernoService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, EjeDeGobiernoService.class);
		final EjeDeGobiernoRespMsg response = new EjeDeGobiernoRespMsg();
		final EjeDeGobiernoReqMsg request = (EjeDeGobiernoReqMsg) event.getObj(); 

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveEjesDeGobierno(event, response, request);
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
//			service.createEjeDeGobierno(request.getEjeDeGobierno());
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
//			service.updateEjeDeGobierno(request.getEjeDeGobierno());
//		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
//			service.deleteEjeDeGobierno(request.getEjeDeGobierno());
		} else {

		}
		logResponseMessage(event, EjeDeGobiernoService.class);
	}

	private void retrieveEjesDeGobierno(ESBEvent event,
			final EjeDeGobiernoRespMsg response, final EjeDeGobiernoReqMsg request) {
		List<EjeDeGobierno> ejesDeGobierno = new ArrayList<EjeDeGobierno>();

		if (request.getId() != null) {
			ejesDeGobierno.add(service.getEjeDeGobiernoPorId(request.getId()));
		} else if (StringUtils.isNotBlank(request.getName())) {
			ejesDeGobierno.add(service.getEjeDeGobiernoPorNombre(request.getName()));
		} else if (StringUtils.isNotBlank(request.getCodigo())) {
//			ejesDeGobierno.add(service.getEjeDeGobiernoPorCodigo(request.getCodigo()));
		} else {
			ejesDeGobierno = service.getEjesDeGobierno();
		}
		response.setEjesDeGobierno(ejesDeGobierno);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(
				EjeDeGobiernoReqMsg.EJE_DE_GOBIERNO_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
