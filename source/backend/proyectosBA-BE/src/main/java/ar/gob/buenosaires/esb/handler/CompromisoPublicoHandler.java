package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.CompromisoPublicoReqMsg;
import ar.gob.buenosaires.esb.domain.message.CompromisoPublicoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.CompromisoPublicoService;

public class CompromisoPublicoHandler extends AbstractBaseEventHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(CompromisoPublicoHandler.class);
	
	@Autowired
	private CompromisoPublicoService service;

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(CompromisoPublicoReqMsg.COMPROMISO_PUBLICO_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, CompromisoPublicoService.class);
		final CompromisoPublicoReqMsg request = (CompromisoPublicoReqMsg)  JMSUtil.crearObjeto(getReader(CompromisoPublicoReqMsg.class), event.getXml());

		final CompromisoPublicoRespMsg response = new CompromisoPublicoRespMsg();
		event.setObj(response);
		List<CompromisoPublico> compromisosPublicos = new ArrayList<CompromisoPublico>();
		response.setCompromisosPublicos(compromisosPublicos);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveCompromisosPublicos(event, response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			compromisosPublicos.add(service.createCompromisoPublico(request.getCompromisoPublico()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			compromisosPublicos.add(service.updateCompromisoPublico(request.getCompromisoPublico()));
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deleteCompromisoPublico(request.getId());
		} else {

		}
		logResponseMessage(event, CompromisoPublicoService.class);
	}

	private void retrieveCompromisosPublicos(ESBEvent event, final CompromisoPublicoRespMsg response, final CompromisoPublicoReqMsg request) {
		List<CompromisoPublico> compromisosPublicos = new ArrayList<CompromisoPublico>();

		if (request.getId() != null) {
			compromisosPublicos.add(service.getCompromisoPublicoPorId(request.getId()));
		} else {
			compromisosPublicos = service.getCompromisosPublicos();
		}
		response.setCompromisosPublicos(compromisosPublicos);
		event.setObj(response);
	}
	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
