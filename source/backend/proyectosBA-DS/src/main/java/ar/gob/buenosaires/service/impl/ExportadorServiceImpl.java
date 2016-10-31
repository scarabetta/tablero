package ar.gob.buenosaires.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.jms.JMSException;

import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTAutoFilter;
import org.openxmlformats.schemas.spreadsheetml.x2006.main.CTTable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.ExportacionProyectoView;
import ar.gob.buenosaires.domain.ReporteProyectosView;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.ExportacionProyectoViewReqMsg;
import ar.gob.buenosaires.esb.domain.message.ExportacionProyectoViewRespMsg;
import ar.gob.buenosaires.esb.domain.message.ReporteProyectosViewReqMsg;
import ar.gob.buenosaires.esb.domain.message.ReporteProyectosViewRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.exportador.consumers.CreadorDinamicoDeColumnasConsumer;
import ar.gob.buenosaires.importador.proyecto.priorizado.ProyectoPriorizadoSolapa;
import ar.gob.buenosaires.service.ExportarProyectoService;

@Service
public class ExportadorServiceImpl implements ExportarProyectoService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ImportarProyectoServiceImpl.class);

	public final static String REPORTE_PROYECTOS_JURISDICCION = "ReporteJurisdiccion";

	public final static String REPORTE_PROYECTOS_SECRETARIA = "ReporteSecretaria";

	@Autowired
	private EsbService esbService;

	@Autowired
	private Environment env;

	@Override
	public List<ExportacionProyectoView> getAllExportacionProyectoView() throws ESBException, JMSException {
		ExportacionProyectoViewReqMsg reqMsg = new ExportacionProyectoViewReqMsg();

		return getExportacionProyectoViewFromReqMsg(reqMsg);
	}

	@Override
	public List<ReporteProyectosView> getAllReporteProyectosView(String mailUsuario) throws ESBException, JMSException {
		ReporteProyectosViewReqMsg reqMsg = new ReporteProyectosViewReqMsg();
		reqMsg.setEmailUsuario(mailUsuario);
		return getReporteProyectosViewFromReqMsg(reqMsg);
	}

	@Override
	public void generarExcel(XSSFSheet sheetAt0) throws ESBException, JMSException {
		int newRoxIdx = 2;
		List<ExportacionProyectoView> allExportacionProyectoView = getAllExportacionProyectoView();
		XSSFRow nuevaFila = null;
		Map<Long, XSSFRow> filasCreadas = new HashMap<>();

		for (Iterator<ExportacionProyectoView> iteratorItem = allExportacionProyectoView.iterator(); iteratorItem
				.hasNext();) {
			ExportacionProyectoView exportacionProyectoView = iteratorItem.next();
			nuevaFila = sheetAt0.createRow(newRoxIdx);
			popularCeldasFijas(nuevaFila, exportacionProyectoView);

			if (exportacionProyectoView.getPoblacionesMeta() != null
					&& !exportacionProyectoView.getPoblacionesMeta().isEmpty()) {
				exportacionProyectoView.getPoblacionesMeta().parallelStream()
						.forEachOrdered(new CreadorDinamicoDeColumnasConsumer(sheetAt0, nuevaFila.getRowNum()));
			}
			filasCreadas.put(exportacionProyectoView.getIdProyecto(), nuevaFila);
			newRoxIdx++;
		}

		for (Iterator<ExportacionProyectoView> iteratorItem = allExportacionProyectoView.iterator(); iteratorItem
				.hasNext();) {
			ExportacionProyectoView exportacionProyectoView = iteratorItem.next();
			nuevaFila = filasCreadas.get(exportacionProyectoView.getIdProyecto());

			if (exportacionProyectoView.getEjesDeGobierno() != null
					&& !exportacionProyectoView.getEjesDeGobierno().isEmpty()) {
				exportacionProyectoView.getEjesDeGobierno().parallelStream()
						.forEachOrdered(new CreadorDinamicoDeColumnasConsumer(sheetAt0, nuevaFila.getRowNum()));
			}
		}

		for (Iterator<ExportacionProyectoView> iteratorItem = allExportacionProyectoView.iterator(); iteratorItem
				.hasNext();) {
			ExportacionProyectoView exportacionProyectoView = iteratorItem.next();
			nuevaFila = filasCreadas.get(exportacionProyectoView.getIdProyecto());

			if (exportacionProyectoView.getComunas() != null && !exportacionProyectoView.getComunas().isEmpty()) {
				exportacionProyectoView.getComunas().parallelStream()
						.forEachOrdered(new CreadorDinamicoDeColumnasConsumer(sheetAt0, nuevaFila.getRowNum()));
			}
		}

		for (Iterator<ExportacionProyectoView> iteratorItem = allExportacionProyectoView.iterator(); iteratorItem
				.hasNext();) {
			ExportacionProyectoView exportacionProyectoView = iteratorItem.next();
			nuevaFila = filasCreadas.get(exportacionProyectoView.getIdProyecto());

			if (exportacionProyectoView.getTemasTransversales() != null
					&& !exportacionProyectoView.getTemasTransversales().isEmpty()) {
				exportacionProyectoView.getTemasTransversales().parallelStream()
						.forEachOrdered(new CreadorDinamicoDeColumnasConsumer(sheetAt0, nuevaFila.getRowNum()));
			}
		}

		if (!filasCreadas.isEmpty()) {
			ProyectoPriorizadoSolapa solapa = new ProyectoPriorizadoSolapa(sheetAt0, env);
			int ultimaCelda = solapa.getFilaHeader().getNumeroUltimaCelda();

			CTTable ctTable = sheetAt0.getTables().get(0).getCTTable();
			String cellRef = "A2:" + CellReference.convertNumToColString(ultimaCelda - 1)
					+ String.valueOf(nuevaFila.getRowNum() + 1);
			ctTable.setRef(cellRef);
			CTAutoFilter ctAutoFilter = ctTable.getAutoFilter();
			ctAutoFilter.setRef(cellRef);
			ctTable.setAutoFilter(ctAutoFilter);
			agregarCeldasCombo(sheetAt0, nuevaFila.getRowNum());
		}
	}

	@Override
	public void generarExcelReporte(XSSFSheet sheetAt0, String mailUsuario) throws ESBException, JMSException {
		int newRoxIdx = 2;
		List<ReporteProyectosView> allReporteProyectosView = getAllReporteProyectosView(mailUsuario);
		XSSFRow nuevaFila = null;
		Map<Long, XSSFRow> filasCreadas = new HashMap<>();

		if (allReporteProyectosView == null) {
			return;
		}

		for (Iterator<ReporteProyectosView> iteratorItem = allReporteProyectosView.iterator(); iteratorItem
				.hasNext();) {
			ReporteProyectosView reporteProyectoView = iteratorItem.next();
			nuevaFila = sheetAt0.createRow(newRoxIdx);
			popularCeldasFijasReporte(nuevaFila, reporteProyectoView);

			if (reporteProyectoView.getPoblacionesMeta() != null
					&& !reporteProyectoView.getPoblacionesMeta().isEmpty()) {
				reporteProyectoView.getPoblacionesMeta().parallelStream()
						.forEachOrdered(new CreadorDinamicoDeColumnasConsumer(sheetAt0, nuevaFila.getRowNum()));
			}
			filasCreadas.put(reporteProyectoView.getIdProyecto(), nuevaFila);
			newRoxIdx++;
		}

		for (Iterator<ReporteProyectosView> iteratorItem = allReporteProyectosView.iterator(); iteratorItem
				.hasNext();) {
			ReporteProyectosView reporteProyectoView = iteratorItem.next();
			nuevaFila = filasCreadas.get(reporteProyectoView.getIdProyecto());

			if (reporteProyectoView.getEjesDeGobierno() != null && !reporteProyectoView.getEjesDeGobierno().isEmpty()) {
				reporteProyectoView.getEjesDeGobierno().parallelStream()
						.forEachOrdered(new CreadorDinamicoDeColumnasConsumer(sheetAt0, nuevaFila.getRowNum()));
			}
		}

		for (Iterator<ReporteProyectosView> iteratorItem = allReporteProyectosView.iterator(); iteratorItem
				.hasNext();) {
			ReporteProyectosView reporteProyectoView = iteratorItem.next();
			nuevaFila = filasCreadas.get(reporteProyectoView.getIdProyecto());

			if (reporteProyectoView.getComunas() != null && !reporteProyectoView.getComunas().isEmpty()) {
				reporteProyectoView.getComunas().parallelStream()
						.forEachOrdered(new CreadorDinamicoDeColumnasConsumer(sheetAt0, nuevaFila.getRowNum()));
			}
		}

		for (Iterator<ReporteProyectosView> iteratorItem = allReporteProyectosView.iterator(); iteratorItem
				.hasNext();) {
			ReporteProyectosView reporteProyectoView = iteratorItem.next();
			nuevaFila = filasCreadas.get(reporteProyectoView.getIdProyecto());

			if (reporteProyectoView.getTemasTransversales() != null
					&& !reporteProyectoView.getTemasTransversales().isEmpty()) {
				reporteProyectoView.getTemasTransversales().parallelStream()
						.forEachOrdered(new CreadorDinamicoDeColumnasConsumer(sheetAt0, nuevaFila.getRowNum()));
			}
		}

		if (!filasCreadas.isEmpty()) {
			ProyectoPriorizadoSolapa solapa = new ProyectoPriorizadoSolapa(sheetAt0, env);
			int ultimaCelda = solapa.getFilaHeader().getNumeroUltimaCelda();

			CTTable ctTable = sheetAt0.getTables().get(0).getCTTable();
			String cellRef = "A2:" + CellReference.convertNumToColString(ultimaCelda - 1)
					+ String.valueOf(nuevaFila.getRowNum() + 1);
			ctTable.setRef(cellRef);
			CTAutoFilter ctAutoFilter = ctTable.getAutoFilter();
			ctAutoFilter.setRef(cellRef);
			ctTable.setAutoFilter(ctAutoFilter);
		}
	}

	private void popularCeldasFijas(XSSFRow nuevaFila, ExportacionProyectoView datos) {
		// Estilo date
		CellStyle cellStyleDateCloner = nuevaFila.getSheet().getWorkbook().createCellStyle();
		CreationHelper ch = nuevaFila.getSheet().getWorkbook().getCreationHelper();
		cellStyleDateCloner.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));
		// Estilo currency
		CellStyle cellStyleAccountingCloner = nuevaFila.getSheet().getWorkbook().createCellStyle();
		cellStyleAccountingCloner.setDataFormat((short) 7);

		nuevaFila.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getNombreJurisidiccion());
		nuevaFila.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getIdProyecto());
		nuevaFila.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getNombreProyecto());
		nuevaFila.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getEstado());
		nuevaFila.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getObjetivoEstrategico());
		nuevaFila.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getObjetivoOperativo());
		nuevaFila.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getDescripcionProyecto());
		nuevaFila.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getResponsable());
		nuevaFila.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getAreaNombre());
		nuevaFila.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getOrganismosCorresponsables());

		// Fechas
		nuevaFila.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleDateCloner);
		nuevaFila.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getFechaInicio());
		nuevaFila.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleDateCloner);
		nuevaFila.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getFechaFin());

		nuevaFila.getCell(12, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getTipoProyecto());
		nuevaFila.getCell(13, Row.CREATE_NULL_AS_BLANK)
				.setCellValue(humanizarBoolean(datos.isImplicaCambioLegislativo()));
		nuevaFila.getCell(14, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getPrioridadJurisdiccional());

		if (datos.getMeta() != null) {
			nuevaFila.getCell(15, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getMeta().doubleValue());
		} else {
			nuevaFila.getCell(15, Row.CREATE_NULL_AS_BLANK).setCellValue("");
		}
		nuevaFila.getCell(16, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getUnidadMeta());
		nuevaFila.getCell(17, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getCantidadPoblacionImpactada());

		nuevaFila.getCell(18, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleAccountingCloner);
		nuevaFila.getCell(18, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getPresupuestoPrimerAnio());
		nuevaFila.getCell(19, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleAccountingCloner);
		nuevaFila.getCell(19, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getPresupuestoTotal());
	}

	private void popularCeldasFijasReporte(XSSFRow nuevaFila, ReporteProyectosView datos) {
		// Estilo date
		CellStyle cellStyleDateCloner = nuevaFila.getSheet().getWorkbook().createCellStyle();
		CreationHelper ch = nuevaFila.getSheet().getWorkbook().getCreationHelper();
		cellStyleDateCloner.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));
		// Estilo currency
		CellStyle cellStyleAccountingCloner = nuevaFila.getSheet().getWorkbook().createCellStyle();
		cellStyleAccountingCloner.setDataFormat((short) 7);

		nuevaFila.getCell(0, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getNombreJurisidiccion());
		nuevaFila.getCell(1, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getIdProyecto());
		nuevaFila.getCell(2, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getNombreProyecto());
		nuevaFila.getCell(3, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getEstado());
		nuevaFila.getCell(4, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getObjetivoEstrategico());
		nuevaFila.getCell(5, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getObjetivoOperativo());
		nuevaFila.getCell(6, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getDescripcionProyecto());
		nuevaFila.getCell(7, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getResponsable());
		nuevaFila.getCell(8, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getAreaNombre());
		nuevaFila.getCell(9, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getOrganismosCorresponsables());

		// Fechas
		nuevaFila.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleDateCloner);
		nuevaFila.getCell(10, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getFechaInicio());
		nuevaFila.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleDateCloner);
		nuevaFila.getCell(11, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getFechaFin());

		nuevaFila.getCell(12, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getTipoProyecto());
		nuevaFila.getCell(13, Row.CREATE_NULL_AS_BLANK)
				.setCellValue(humanizarBoolean(datos.isImplicaCambioLegislativo()));
		nuevaFila.getCell(14, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getPrioridadJurisdiccional());

		if (datos.getMeta() != null) {
			nuevaFila.getCell(15, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getMeta().doubleValue());
		} else {
			nuevaFila.getCell(15, Row.CREATE_NULL_AS_BLANK).setCellValue("");
		}
		nuevaFila.getCell(16, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getUnidadMeta());
		nuevaFila.getCell(17, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getCantidadPoblacionImpactada());

		nuevaFila.getCell(18, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleAccountingCloner);
		nuevaFila.getCell(18, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getPresupuestoPrimerAnio());
		nuevaFila.getCell(19, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleAccountingCloner);
		nuevaFila.getCell(19, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getPresupuestoTotal());

		nuevaFila.getCell(20, Row.CREATE_NULL_AS_BLANK).setCellStyle(cellStyleAccountingCloner);
		nuevaFila.getCell(20, Row.CREATE_NULL_AS_BLANK)
				.setCellValue(datos.getPresupuestoAprobado() == null ? 0 : datos.getPresupuestoAprobado());

		nuevaFila.getCell(21, Row.CREATE_NULL_AS_BLANK).setCellValue(datos.getPrioridadJefatura());

	}

	private void agregarCeldasCombo(XSSFSheet sheet, int numeroUltimaFila) {
		DataValidationHelper dvHelper = sheet.getDataValidationHelper();

		// Combo para el la prioridad jefatura
		int numeroPrimerFilaExportada = 2;
		DataValidationConstraint dvConstraintPrioridadJefatura = dvHelper.createExplicitListConstraint(
				env.getProperty("proyecto.priorizado.opciones.prioridadJefatura").split(","));
		CellRangeAddressList addressListPrioridadJefatura = new CellRangeAddressList(numeroPrimerFilaExportada,
				numeroUltimaFila, 21, 21);
		DataValidation validationPrioridadJefatura = dvHelper.createValidation(dvConstraintPrioridadJefatura,
				addressListPrioridadJefatura);

		// Combo para el la EstadoAprobacion
		DataValidationConstraint dvConstraintEstadoAprobacion = dvHelper.createExplicitListConstraint(
				env.getProperty("proyecto.priorizado.opciones.estadoAprobacion").split(","));
		CellRangeAddressList addressListEstadoAprobacion = new CellRangeAddressList(numeroPrimerFilaExportada,
				numeroUltimaFila, 22, 22);
		DataValidation validationEstadoAprobacion = dvHelper.createValidation(dvConstraintEstadoAprobacion,
				addressListEstadoAprobacion);

		sheet.addValidationData(validationPrioridadJefatura);
		sheet.addValidationData(validationEstadoAprobacion);
	}

	private String humanizarBoolean(Boolean booleano) {

		return booleano != null && booleano ? "Si" : "No";
	}

	private List<ExportacionProyectoView> getExportacionProyectoViewFromReqMsg(ExportacionProyectoViewReqMsg reqMsg)
			throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener una ExportacionProyectoView : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE,
				ExportacionProyectoViewRespMsg.class);

		List<ExportacionProyectoView> exportacionProyectoViews = null;
		if (response.getEventType().equalsIgnoreCase(ExportacionProyectoViewRespMsg.EXPORTACION_PROYECTO_VIEW_TYPE)) {
			exportacionProyectoViews = ((ExportacionProyectoViewRespMsg) response).getExportacionProyectoViews();
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("Obteninendo las ExportacionProyectoView de la respuesta del BUS de servicios: {}",
						exportacionProyectoViews.toString());
			} else {
				getLogger().info("Obteninendo las ExportacionProyectoView de la respuesta del BUS de servicios");
			}
		}
		return exportacionProyectoViews;
	}

	private List<ReporteProyectosView> getReporteProyectosViewFromReqMsg(ReporteProyectosViewReqMsg reqMsg)
			throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener una ReporteProyectosView : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE,
				ReporteProyectosViewRespMsg.class);

		List<ReporteProyectosView> reporteProyectosViews = null;
		if (response.getEventType().equalsIgnoreCase(ReporteProyectosViewRespMsg.REPORTE_PROYECTOS_VIEW_TYPE)) {
			reporteProyectosViews = ((ReporteProyectosViewRespMsg) response).getReporteProyectosViews();
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("Obteninendo las ReporteProyectosView de la respuesta del BUS de servicios: {}",
						reporteProyectosViews.toString());
			} else {
				getLogger().info("Obteninendo las ReporteProyectosView de la respuesta del BUS de servicios");
			}
		}
		return reporteProyectosViews;
	}

	/**
	 * @return the logger
	 */
	public static Logger getLogger() {
		return LOGGER;
	}
}
