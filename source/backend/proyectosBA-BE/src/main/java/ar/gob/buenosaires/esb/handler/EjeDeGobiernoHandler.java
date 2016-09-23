package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.EjeDeGobiernoReqMsg;
import ar.gob.buenosaires.esb.domain.message.EjeDeGobiernoRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.EjeDeGobiernoService;

public class EjeDeGobiernoHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(EjeDeGobiernoHandler.class);

	@Autowired
	private EjeDeGobiernoService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, EjeDeGobiernoService.class);
		final EjeDeGobiernoReqMsg request = (EjeDeGobiernoReqMsg) JMSUtil.crearObjeto(event.getXml(), EjeDeGobiernoReqMsg.class); 

		final EjeDeGobiernoRespMsg response = new EjeDeGobiernoRespMsg();
		event.setObj(response);
		List<EjeDeGobierno> ejesDeGob = new ArrayList<EjeDeGobierno>();
		response.setEjesDeGobierno(ejesDeGob);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveEjesDeGobierno(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			ejesDeGob.add(service.createEjeDeGobierno(request.getEjeDeGobierno()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			ejesDeGob.add(service.updateEjeDeGobierno(request.getEjeDeGobierno()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteEjeDeGobierno(request.getEjeDeGobierno());
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para el servicio de Ejes de Gobierno.");
		}
		logResponseMessage(event, EjeDeGobiernoService.class);
	}

	private void retrieveEjesDeGobierno(final EjeDeGobiernoRespMsg response, final EjeDeGobiernoReqMsg request) {
		List<EjeDeGobierno> ejesDeGobierno = new ArrayList<EjeDeGobierno>();

		if (request.getId() != null) {
			ejesDeGobierno.add(service.getEjeDeGobiernoPorId(request.getId()));
		} else if (request.getName() != null) {
			ejesDeGobierno.add(service.getEjeDeGobiernoPorNombre(request.getName()));
		} else {
			ejesDeGobierno = service.getEjesDeGobierno();
		}
		response.setEjesDeGobierno(ejesDeGobierno);
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
