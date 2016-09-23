package ar.gob.buenosaires.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Consumer;

import javax.jms.JMSException;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.formula.FormulaParseException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.importador.MensajeError;
import ar.gob.buenosaires.importador.ResultadoProcesamiento;
import ar.gob.buenosaires.importador.proyecto.ImportadorProyectoBuilder;
import ar.gob.buenosaires.importador.proyecto.ProyectoImportadoDTO;
import ar.gob.buenosaires.importador.proyecto.SolapaProyecto;
import ar.gob.buenosaires.service.IServiceFactory;
import ar.gob.buenosaires.service.ImportarProyectoService;

@Service
public class ImportarProyectoServiceImpl implements ImportarProyectoService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ImportarProyectoServiceImpl.class);

	@Autowired
	private IServiceFactory serviceFactory;

	@Autowired
	Environment env;

	@Value("${proyecto.priorizado.num.celda.nombreProyecto}")
	Integer numeroCeldaNombreProyectoPriorizado;

	@Value("${proyecto.priorizado.num.celda.fechaFin}")
	Integer numeroCeldaFechaFinProyectoPriorizado;

	@Value("${proyecto.priorizado.num.celda.fechaInicio}")
	Integer numeroCeldaFechaInicioProyectoPriorizado;

	@Autowired
	private SolapaProyecto solapaProyecto;

	@Override
	public ResultadoProcesamiento previewProyectos(Workbook solpaAImportar) {
		getSolapaProyecto().setSolapa(solpaAImportar.getSheetAt(0));
		getSolapaProyecto().configurarSolapa();
		getSolapaProyecto().validarSolapa();
		ResultadoProcesamiento resultadoProcesamiento = getInformacionProyectosParaPreview(getSolapaProyecto());
		getSolapaProyecto().getValidador().getProblemasSolapa().forEach(new Consumer<MensajeError>() {
			@Override
			public void accept(MensajeError t) {
				resultadoProcesamiento.getErroresDeSolapa().add(t.getMensaje() + "\n");
			}
		});

		return resultadoProcesamiento;
	}

	@Override
	public List<MensajeError> validarSolapaProyectos(Workbook solpaAImportar) {
		getSolapaProyecto().setSolapa(solpaAImportar.getSheetAt(0));
		getSolapaProyecto().configurarSolapa();
		getSolapaProyecto().validarSolapa();

		return getSolapaProyecto().getValidador().getProblemasSolapa();
	}

	@Override
	public ResultadoProcesamiento importarSolapaProyectos(Workbook solpaAImportar, boolean pisarProyectos)
			throws InvalidFormatException, IOException {

		getSolapaProyecto().setSolapa(solpaAImportar.getSheetAt(0));
		getSolapaProyecto().configurarSolapa(pisarProyectos);

		List<MensajeError> problemasSolpa = getSolapaProyecto().validarSolapa();
		ResultadoProcesamiento resultadoProcesamiento = new ResultadoProcesamiento();

		if (problemasSolpa.isEmpty()) {
			ImportadorProyectoBuilder builder = null;
			boolean laProximaEsUltimaFila = false;
			Row unaFila = null;

			for (int i = getSolapaProyecto().getNumeroFilaInicioImportacion(); i <= getSolapaProyecto()
					.getNumeroUltimaFila() && !laProximaEsUltimaFila; i++) {
				unaFila = getSolapaProyecto().getFilaNumero(i);
				builder = new ImportadorProyectoBuilder(serviceFactory);
				builder.setJurisdiccion(getSolapaProyecto().getJurisdiccion());

				try {
					if (getSolapaProyecto().validarEImportarFila(unaFila, builder)) {
						resultadoProcesamiento.agregarProyecto(builder.build());
					}
				} catch (ESBException | JMSException e) {
					LOGGER.error("Hubo un error en la fila " + unaFila.getRowNum() + "\n" + e.getMessage());
					getSolapaProyecto().getValidador()
					.agregarMensajeParaFila(unaFila.getRowNum(), "Hubo un error inesperado en la fila "
							+ unaFila.getRowNum() + " al ser gurdada en la base de datos.",
							MensajeError.TIPO_ERROR);
				}
				laProximaEsUltimaFila = (getSolapaProyecto().getFilaNumero(i + 1) == null)
						|| getSolapaProyecto().esUltimaFila(getSolapaProyecto().getFilaNumero(i + 1));

			}
		}

		if (!getSolapaProyecto().getValidador().getProblemasSolapa().isEmpty()) {
			for (Iterator<MensajeError> iterator = getSolapaProyecto().getValidador().getProblemasSolapa()
					.iterator(); iterator.hasNext();) {
				MensajeError mensajeError = iterator.next();
				resultadoProcesamiento.agregarErrorSolapa(mensajeError.getMensaje());
			}

		}

		// Si hay errores, borro las filas que se importaron bien y deja las
		// malas
		if (!getSolapaProyecto().getValidador().getProblemasFilas().isEmpty()) {
			crearExcelErroresSiEsNecesario(resultadoProcesamiento);
		}
		return resultadoProcesamiento;
	}

	@Override
	public SolapaProyecto getSolapaProyecto() {
		return solapaProyecto;
	}

	public void crearExcelErroresSiEsNecesario(ResultadoProcesamiento resultadoProcesamiento)
			throws InvalidFormatException, IOException {
		XSSFWorkbook wb = null;

		try {
			Date now = new Date();
			String pathParaGuardar = env.getProperty("save.archivos.proyecto.error.path").replace("idJurisdiccion",
					String.valueOf(getSolapaProyecto().getJurisdiccion().getIdJurisdiccion()));

			if (Files.notExists(Paths.get(pathParaGuardar))) {
				Files.createDirectories(Paths.get(pathParaGuardar));
			}

			String nombreArchivo = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(now) + ".xlsx";

			FileInputStream fis = new FileInputStream(env.getProperty("download.template.proyecto.path"));
			FileOutputStream out = new FileOutputStream(pathParaGuardar + nombreArchivo);
			wb = new XSSFWorkbook(fis);
			Sheet solapaProyectoTemplate = wb.getSheetAt(0);
			int numeroFilaInicioImportacion = getSolapaProyecto().getNumeroFilaInicioImportacion();
			int index = 0;
			int numeroNuevaFila = 0;
			solapaProyectoTemplate.getRow(0).getCell(1).setCellValue("Primero seleccioná tu jurisdicción");

			for (Integer numeroFilaConError : getSolapaProyecto().getValidador().getProblemasFilas().keySet()) {
				numeroNuevaFila = numeroFilaInicioImportacion + index;
				getSolapaProyecto().copiarFilas(getSolapaProyecto().getFilaNumero(numeroFilaConError),
						solapaProyectoTemplate.createRow(numeroNuevaFila));

				resultadoProcesamiento.agregarProyectoFallido(getSolapaProyecto().getFilaNumero(numeroFilaConError),
						getSolapaProyecto().getValidador().getProblemasFilas().get(numeroFilaConError));
				index++;
			}
			agregarCeldasCombo(solapaProyectoTemplate, numeroNuevaFila);
			wb.write(out);
			fis.close();
			wb.close();
			out.close();
			resultadoProcesamiento.setNombreArchivoError(nombreArchivo);
		} catch (FileNotFoundException | FormulaParseException | IllegalStateException e) {
			String mensajeError = "Hubo un problema leyendo el archivo template : \n" + e.getMessage();
			LOGGER.debug(mensajeError);
			e.printStackTrace();
			resultadoProcesamiento.setErrorGenerico(mensajeError);
		} catch (ESBException | JMSException e) {
			String mensajeError = "Hubo un generando los combos de las celdas : \n" + e.getMessage();
			LOGGER.debug(mensajeError);
			e.printStackTrace();
			resultadoProcesamiento.setErrorGenerico(mensajeError);
		}
	}

	private void agregarCeldasCombo(Sheet sheet, int numeroUltimaFila) throws ESBException, JMSException {
		DataValidationHelper dvHelper = sheet.getDataValidationHelper();
		String[] nombresPoblacionMeta = serviceFactory.getPoblacionMetaService().getPoblacionesMeta().stream()
				.map(s -> s.getNombre()).toArray(String[]::new);
		String[] nombresComuna = serviceFactory.getComunaService().getComunas().stream().map(s -> s.getNombre())
				.toArray(String[]::new);
		String[] nombresEjesDeGobierno = serviceFactory.getEjeDeGobiernoService().getEjesDeGobierno().stream()
				.map(s -> s.getNombre()).toArray(String[]::new);

		// Opciones desde las properties
		String[] nombresTipoUbicacion = env.getProperty("proyecto.col.tipoUbicacion.opciones").split(",");
		String[] nombresTipoProyecto = env.getProperty("proyecto.col.tipoProyecto.opciones").split(",");
		String[] nombresImplicaCambioLegislativo = env.getProperty("proyecto.col.implicaCambioLegislativo.opciones")
				.split(",");
		String[] nombresPrioridadJurisdiccional = env.getProperty("proyecto.col.prioridadJurisdiccional.opciones")
				.split(",");
		String numeroCeldaInicio;
		String numeroCeldaFin;

		// Combo para el la Poblacion meta
		numeroCeldaInicio = env.getProperty("proyecto.col.segmento.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.segmento.numero.fin");
		agregarOpciones(sheet, nombresPoblacionMeta, dvHelper, numeroUltimaFila, numeroCeldaInicio, numeroCeldaFin,
				true);
		// Combo para el la Comuna
		numeroCeldaInicio = env.getProperty("proyecto.col.comuna.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.comuna.numero.fin");
		agregarOpciones(sheet, nombresComuna, dvHelper, numeroUltimaFila, numeroCeldaInicio, numeroCeldaFin, true);
		// Combo para el Tipo de ubicacion
		numeroCeldaInicio = env.getProperty("proyecto.col.tipoUbicacion.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.tipoUbicacion.numero.fin");
		agregarOpciones(sheet, nombresTipoUbicacion, dvHelper, numeroUltimaFila, numeroCeldaInicio, numeroCeldaFin,
				true);
		// Combo para el Tipo de Proyecto
		numeroCeldaInicio = env.getProperty("proyecto.col.tipoProyecto.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.tipoProyecto.numero.fin");
		agregarOpciones(sheet, nombresTipoProyecto, dvHelper, numeroUltimaFila, numeroCeldaInicio, numeroCeldaFin,
				true);
		// Combo para el los ejes de gobierno
		numeroCeldaInicio = env.getProperty("proyecto.col.ejeGobierno.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.ejeGobierno.numero.fin");
		agregarOpciones(sheet, nombresEjesDeGobierno, dvHelper, numeroUltimaFila, numeroCeldaInicio, numeroCeldaFin,
				true);
		// Combo para el implicaCambioLegislativo
		numeroCeldaInicio = env.getProperty("proyecto.col.implicaCambioLegislativo.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.implicaCambioLegislativo.numero.fin");
		agregarOpciones(sheet, nombresImplicaCambioLegislativo, dvHelper, numeroUltimaFila, numeroCeldaInicio,
				numeroCeldaFin, true);
		// Combo para el prioridadJurisdiccional
		numeroCeldaInicio = env.getProperty("proyecto.col.prioridadJurisdiccional.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.prioridadJurisdiccional.numero.fin");
		agregarOpciones(sheet, nombresPrioridadJurisdiccional, dvHelper, numeroUltimaFila, numeroCeldaInicio,
				numeroCeldaFin, true);
		// Combo para el Area
		numeroCeldaInicio = env.getProperty("proyecto.col.area.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.area.numero.fin");
		agregarOpciones(sheet, null, dvHelper, numeroUltimaFila, numeroCeldaInicio, numeroCeldaFin, false,
				"=INDIRECT(CONCATENATE($B$2,\".Areas\"))");
		// Combo para Objetivos Juris.
		numeroCeldaInicio = env.getProperty("proyecto.col.objetivoJurisdiccional.numero.inicio");
		numeroCeldaFin = env.getProperty("proyecto.col.objetivoJurisdiccional.numero.fin");
		agregarOpciones(sheet, null, dvHelper, numeroUltimaFila, numeroCeldaInicio, numeroCeldaFin, false,
				"=INDIRECT($B$2)");
	}

	private void agregarOpciones(Sheet sheet, String[] opciones, DataValidationHelper dvHelper, int numeroUltimaFila,
			String numeroColumnaInicio, String numeroColumnaFin, boolean showError, String... formula) {
		int numeroPrimerFilaExportada = 2;
		DataValidationConstraint dvConstraint;
		if (opciones == null || ArrayUtils.isNotEmpty(formula)) {
			dvConstraint = dvHelper.createFormulaListConstraint(formula[0]);
		} else {
			dvConstraint = dvHelper.createExplicitListConstraint(opciones);
		}
		CellRangeAddressList addressListPrioridadJefatura = new CellRangeAddressList(numeroPrimerFilaExportada,
				numeroUltimaFila, Integer.parseInt(numeroColumnaInicio), Integer.parseInt(numeroColumnaFin));
		DataValidation validation = dvHelper.createValidation(dvConstraint, addressListPrioridadJefatura);
		if (showError) {
			validation.setErrorStyle(DataValidation.ErrorStyle.STOP);
			validation.createErrorBox("Error", "El valor ingresado es inválido. Seleccioná un valor de la lista.");
			validation.setShowErrorBox(true);
		} else {
			validation.setErrorStyle(DataValidation.ErrorStyle.WARNING);
			validation.createErrorBox("Objetivo estratégico nuevo",
					"Cuando importes este archivo, estarás registrando un nuevo objetivo estratégico para la jurisdicción.");
			validation.setShowErrorBox(true);
		}

		sheet.addValidationData(validation);
	}

	private ResultadoProcesamiento getInformacionProyectosParaPreview(SolapaProyecto solpaAValidar) {

		List<String> nombresProyecto = new ArrayList<>();
		String nombreProyecto;
		ProyectoImportadoDTO proyectoDTO;
		Proyecto unProyecto;
		ResultadoProcesamiento resultadoProcesamiento = new ResultadoProcesamiento();
		int numeroCeldaFechaInicio = Integer.parseInt(env.getProperty("proyecto.col.fecha.inicio.numero"));
		int numeroCeldaFechaFin = Integer.parseInt(env.getProperty("proyecto.col.fecha.fin.numero"));

		for (int i = solpaAValidar.getNumeroFilaInicioImportacion(); i <= solpaAValidar.getNumeroUltimaFila(); i++) {

			Cell laCeldaProyecto = solpaAValidar.getFilaNumero(i).getCell(0, Row.CREATE_NULL_AS_BLANK);
			Cell celdaFechaInicio = solpaAValidar.getFilaNumero(i).getCell(numeroCeldaFechaInicio,
					Row.CREATE_NULL_AS_BLANK);
			Cell celdaFechaFin = solpaAValidar.getFilaNumero(i).getCell(numeroCeldaFechaFin, Row.CREATE_NULL_AS_BLANK);
			proyectoDTO = new ProyectoImportadoDTO();

			nombreProyecto = SolapaProyecto.getCellStringValue(laCeldaProyecto.getCellType(), laCeldaProyecto);
			if (!nombreProyecto.isEmpty()) {
				nombresProyecto.add(nombreProyecto);
				try {
					unProyecto = serviceFactory.getProyectoService().getProyectoPorNombreIdJurisdiccionYCiertosEstados(
							nombreProyecto, solapaProyecto.getJurisdiccion().getIdJurisdiccion(),
							Arrays.asList("Completo", "Incompleto", "Presentado"));
					if (unProyecto != null) {
						proyectoDTO.setNombreProyecto(unProyecto.getNombre());
						proyectoDTO.setFechaInicio(unProyecto.getFechaInicio());
						proyectoDTO.setFechaFin(unProyecto.getFechaFin());
						proyectoDTO.setIdProyecto(unProyecto.getIdProyecto());
					} else {
						proyectoDTO.setNombreProyecto(nombreProyecto);
						if (Cell.CELL_TYPE_NUMERIC == celdaFechaInicio.getCellType()) {
							proyectoDTO.setFechaInicio(celdaFechaInicio.getDateCellValue());
						}
						if (Cell.CELL_TYPE_NUMERIC == celdaFechaFin.getCellType()) {
							proyectoDTO.setFechaFin(celdaFechaFin.getDateCellValue());
						}
					}
					resultadoProcesamiento.getProyectoProcesados().add(proyectoDTO);

				} catch (ESBException | JMSException e) {
					LOGGER.debug(e.getMessage());
					e.printStackTrace();
					proyectoDTO.setNombreProyecto(nombreProyecto);
					resultadoProcesamiento.getProyectoProcesados().add(proyectoDTO);
				}
			}
		}
		return resultadoProcesamiento;
	}
}
