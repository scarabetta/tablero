package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.PresupuestoPorMes;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.PresupuestoPorMesReqMsg;
import ar.gob.buenosaires.esb.domain.message.PresupuestoPorMesRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.PresupuestoPorMesService;

public class PresupuestoPorMesHandler extends AbstractBaseEventHandler {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(PresupuestoPorMesHandler.class);
	
	@Autowired
	private PresupuestoPorMesService service;

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(PresupuestoPorMesReqMsg.PPM_TYPE);
	}

	@Override
	protected void process(ESBEvent event) throws ESBException {
		logRequestMessage(event, PresupuestoPorMesService.class);
		final PresupuestoPorMesReqMsg request = (PresupuestoPorMesReqMsg) JMSUtil.crearObjeto(getReader(PresupuestoPorMesReqMsg.class), event.getXml());

		final PresupuestoPorMesRespMsg response = new PresupuestoPorMesRespMsg();
		event.setObj(response);
		List<PresupuestoPorMes> roles = new ArrayList<PresupuestoPorMes>();
		response.setPpms(roles);
		
		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrievePresupuestosPorMes(response, request);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_CREATE)) {
			crearPresupuestoPorMes(request, roles);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_UPDATE)) {
			actualizaPresupuestoPorMes(request, roles);
		} else if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_DELETE)) {
			service.deletePresupuestoPorMes(request.getId());
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para el servicio de Presupuestos por Mes");
		}
		logResponseMessage(event, PresupuestoPorMesService.class);
	}

	private void actualizaPresupuestoPorMes(final PresupuestoPorMesReqMsg request, List<PresupuestoPorMes> result) {
		for (PresupuestoPorMes presupuestoPorMes : request.getPpms()) {
			result.add(service.updatePrespuestoPorMes(presupuestoPorMes));
		}
	}

	private void crearPresupuestoPorMes(final PresupuestoPorMesReqMsg request, List<PresupuestoPorMes> result) throws ESBException {
		for (PresupuestoPorMes presupuestoPorMes : request.getPpms()) {
			result.add(service.createPrespuestoPorMes(presupuestoPorMes));
		}
	}

	private void retrievePresupuestosPorMes(final PresupuestoPorMesRespMsg response, final PresupuestoPorMesReqMsg request) {
		List<PresupuestoPorMes> ppms = new ArrayList<PresupuestoPorMes>();

		if (request.getId() != null) {
			ppms.add(service.getPresupuestoPorMesPorId(request.getId()));
		} else {
			ppms = service.getPresupuestosPorMes();
		}
		response.getPpms().addAll(ppms);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}

}
