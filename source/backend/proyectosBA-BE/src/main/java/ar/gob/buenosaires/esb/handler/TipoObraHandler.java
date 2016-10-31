package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.TipoObra;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.TipoObraReqMsg;
import ar.gob.buenosaires.esb.domain.message.TipoObraRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.TipoObraService;

public class TipoObraHandler extends AbstractBaseEventHandler {

private final static Logger LOGGER = LoggerFactory.getLogger(TipoObraHandler.class);
	
	@Autowired
	private TipoObraService service;

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(TipoObraReqMsg.TIPO_OBRA_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, TipoObraService.class);
		final TipoObraReqMsg request = (TipoObraReqMsg) JMSUtil.crearObjeto(getReader(TipoObraReqMsg.class), event.getXml());

		final TipoObraRespMsg response = new TipoObraRespMsg();
		event.setObj(response);
		List<TipoObra> obras = new ArrayList<TipoObra>();
		response.setTiposObras(obras);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveTipoObras(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			obras.add(service.createTipoObra(request.getTipoObra()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			obras.add(service.updateTipoObra(request.getTipoObra()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteTipoObra(request.getId());
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para el servicio de TipoObra");
		}
		logResponseMessage(event, TipoObraService.class);
	}

	private void retrieveTipoObras(final TipoObraRespMsg response, final TipoObraReqMsg request) {
		List<TipoObra> obras = new ArrayList<TipoObra>();

		if (request.getId() != null) {
			obras.add(service.getTipoObraPorId(request.getId()));
		} else {
			obras = service.getTipoObras();
		}
		response.setTiposObras(obras);
	}
}
