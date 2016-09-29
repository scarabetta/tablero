package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.ExportacionProyectoView;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ExportacionProyectoViewReqMsg;
import ar.gob.buenosaires.esb.domain.message.ExportacionProyectoViewRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.ExportacionProyectoViewService;

public class ExportacionProyectoViewHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportacionProyectoViewHandler.class);

	@Autowired
	private ExportacionProyectoViewService service;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ExportacionProyectoViewService.class);
		final ExportacionProyectoViewReqMsg exportacionProyectoViewRequest = (ExportacionProyectoViewReqMsg) JMSUtil
				.crearObjeto(getReader(ExportacionProyectoViewReqMsg.class), event.getXml());

		final ExportacionProyectoViewRespMsg exportacionProyectoViewResponse = new ExportacionProyectoViewRespMsg();
		event.setObj(exportacionProyectoViewResponse);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveExportacionProyectoViews(event, exportacionProyectoViewResponse,
					exportacionProyectoViewRequest);
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(), "La accion: " + event.getAction() + ", no existe para el servicio de Exportacion Proyecto");
		}
		logResponseMessage(event, ExportacionProyectoViewService.class);
	}

	private void retrieveExportacionProyectoViews(ESBEvent event, final ExportacionProyectoViewRespMsg response,
			final ExportacionProyectoViewReqMsg request) {
		List<ExportacionProyectoView> exportacionProyectosView = new ArrayList<>();
		exportacionProyectosView = service.getAllExportacionProyectoView();

		response.setExportacionProyectoViews(exportacionProyectosView);
		event.setObj(response);
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ExportacionProyectoViewReqMsg.EXPORTACION_PROYECTO_VIEW_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
