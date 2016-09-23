package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.ErrorDescripcion;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.ErroresReqMsg;
import ar.gob.buenosaires.esb.domain.message.ErroresRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.ErrorDescripcionService;

@Service
public class ErrorDescripcionServiceImpl implements ErrorDescripcionService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ErrorDescripcionServiceImpl.class);

	@Autowired
	private EsbService esbService;
	
	@Override
	public List<ErrorDescripcion> getErrores() throws ESBException, JMSException {
		ErroresReqMsg reqMsg = new ErroresReqMsg();

		return getErroresFromReqMsg(reqMsg);
	}
	
	private List<ErrorDescripcion> getErroresFromReqMsg(ErroresReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener todos los codigos de error: {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, ErroresRespMsg.class);

		List<ErrorDescripcion> errores = null;
		if (response.getEventType().equalsIgnoreCase(ErroresRespMsg.ERRORES_TYPE)) {
			errores = ((ErroresRespMsg) response).getErrores();
			LOGGER.debug("Obteninendo los codigos de error de la respues del BUS de servicios: {}", errores.toString());
		}
		return errores;
	}
	public static Logger getLogger() {
		return LOGGER;
	}
}
