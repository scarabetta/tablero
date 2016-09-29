package ar.gob.buenosaires.esb.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ErroresReqMsg;
import ar.gob.buenosaires.esb.domain.message.ErroresRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.ErrorDescripcionService;

public class ErrorDescripcionHandler extends AbstractBaseEventHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ErrorDescripcionHandler.class);
	
	@Autowired
	private ErrorDescripcionService service;

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ErroresReqMsg.ERRORES_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, ErrorDescripcionService.class);
		final ErroresReqMsg request = (ErroresReqMsg) JMSUtil.crearObjeto(getReader(ErroresReqMsg.class), event.getXml());

		final ErroresRespMsg response = new ErroresRespMsg();
		event.setObj(response);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveErrores(event, response, request);
		} 
		logResponseMessage(event, ErrorDescripcionService.class);
	}

	private void retrieveErrores(ESBEvent event, final ErroresRespMsg response, final ErroresReqMsg request) {		
		response.setErrores(service.getErrores());
		event.setObj(response);
	}
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
