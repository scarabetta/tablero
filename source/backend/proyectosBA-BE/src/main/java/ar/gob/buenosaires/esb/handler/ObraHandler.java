package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ObraReqMsg;
import ar.gob.buenosaires.esb.domain.message.ObraRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.ObraService;

public class ObraHandler extends AbstractBaseEventHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ObraHandler.class);
	
	@Autowired
	private ObraService service;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ObraReqMsg.OBRA_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, ObraService.class);
		final ObraReqMsg request = (ObraReqMsg) JMSUtil.crearObjeto(getReader(ObraReqMsg.class), event.getXml());

		final ObraRespMsg response = new ObraRespMsg();
		event.setObj(response);
		List<Obra> obras = new ArrayList<Obra>();
		response.setObras(obras);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveObras(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			obras.add(service.createObra(request.getObra()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			obras.add(service.updateObra(request.getObra()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteObra(request.getId());
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para el servicio de Obra");
		}
		logResponseMessage(event, ObraService.class);
	}

	private void retrieveObras(final ObraRespMsg response, final ObraReqMsg request) {
		List<Obra> obras = new ArrayList<Obra>();

		if (request.getId() != null) {
			obras.add(service.getObraPorId(request.getId()));
		} else {
			obras = service.getObras();
		}
		response.setObras(obras);
	}
}
