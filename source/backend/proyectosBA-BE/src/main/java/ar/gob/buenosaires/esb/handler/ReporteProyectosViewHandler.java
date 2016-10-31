package ar.gob.buenosaires.esb.handler;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import ar.gob.buenosaires.domain.ReporteProyectosView;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.message.ReporteProyectosViewReqMsg;
import ar.gob.buenosaires.esb.domain.message.ReporteProyectosViewRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.util.JMSUtil;
import ar.gob.buenosaires.service.ExportacionProyectoViewService;
import ar.gob.buenosaires.service.ReporteProyectosViewService;
import ar.gob.buenosaires.service.UsuarioService;

public class ReporteProyectosViewHandler extends AbstractBaseEventHandler {

	private final static Logger LOGGER = LoggerFactory.getLogger(ReporteProyectosViewHandler.class);

	@Autowired
	private ReporteProyectosViewService service;

	@Autowired
	private UsuarioService usuarioService;

	@Override
	protected void process(ESBEvent event) throws ESBException {

		logRequestMessage(event, ReporteProyectosViewService.class);
		final ReporteProyectosViewReqMsg exportacionProyectoViewRequest = (ReporteProyectosViewReqMsg) JMSUtil
				.crearObjeto(getReader(ReporteProyectosViewReqMsg.class), event.getXml());

		final ReporteProyectosViewRespMsg reporteProyectosViewResponse = new ReporteProyectosViewRespMsg();
		event.setObj(reporteProyectosViewResponse);

		if (event.getAction().equalsIgnoreCase(ESBEvent.ACTION_RETRIEVE)) {
			retrieveReporteProyectosViews(event, reporteProyectosViewResponse, exportacionProyectoViewRequest);
		} else {
			throw new ESBException(CodigoError.ACCION_INEXISTENTE.getCodigo(),
					"La accion: " + event.getAction() + ", no existe para el servicio de Reporte Proyecto");
		}
		logResponseMessage(event, ExportacionProyectoViewService.class);
	}

	private void retrieveReporteProyectosViews(ESBEvent event, final ReporteProyectosViewRespMsg response,
			final ReporteProyectosViewReqMsg request) {
		List<ReporteProyectosView> reporteProyectosView = new ArrayList<>();
		reporteProyectosView = service.getAllReporteProyectosView(obtenerUsuarioDelRequest(request));

		response.setReporteProyectosViews(reporteProyectosView);
		event.setObj(response);
	}

	private Usuario obtenerUsuarioDelRequest(final ReporteProyectosViewReqMsg request) {
		return usuarioService.getUsuarioPorEmail(request.getMailUsuario());
	}

	@Override
	protected boolean validateEvent(ESBEvent event) {
		return event.getType().equalsIgnoreCase(ReporteProyectosViewReqMsg.REPORTE_PROYECTOS_VIEW_TYPE);
	}

	@Override
	protected Logger getLogger() {
		return LOGGER;
	}
}
