package ar.gob.buenosaires.importador.proyecto;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.jms.JMSException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FormulaError;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.importador.MensajeError;
import ar.gob.buenosaires.service.ProyectoService;

@Component
@Scope("prototype")
public class SolapaProyecto {

	private final static Logger LOGGER = LoggerFactory.getLogger(SolapaProyecto.class);

	private Sheet solapa;

	@Autowired
	ValidadorImportadorProyecto validador;

	@Autowired
	ProyectoService proyectoService;

	@Autowired
	Environment env;

	@Value("${proyecto.num.celda.oj}")
	int celdaCodigoOJ;

	@Value("${proyecto.num.celda.oop}")
	int celdaCodigoOOp;

	private int numeroFilaInicioImportacion;

	private int numeroFilaNombreColumnas;

	private int numeroUltimaFila;

	private boolean pisarProyectos = false;

	public List<MensajeError> validarSolapa() {
		return validador.validarSolapa(this);

	}

	public void configurarSolapa() {
		numeroFilaNombreColumnas = Integer.parseInt(env.getProperty("proyecto.fila.col.nombres"));
		numeroFilaInicioImportacion = numeroFilaNombreColumnas + 1;
		numeroUltimaFila = numeroUltimaFila();
		validador.limpiarErrores();
	}

	public void configurarSolapa(boolean pisarProyectos) {
		numeroFilaNombreColumnas = Integer.parseInt(env.getProperty("proyecto.fila.col.nombres"));
		numeroFilaInicioImportacion = numeroFilaNombreColumnas + 1;
		numeroUltimaFila = numeroUltimaFila();
		this.pisarProyectos = pisarProyectos;
		validador.limpiarErrores();
	}

	public Iterator<Cell> getCeldasDeFila(int numeroFila) {
		return solapa.getRow(numeroFila).cellIterator();
	}

	public boolean validarFila(Row unaFila) {
		return validador.validarFila(unaFila);
	}

	public void validarProyectosRepetidos() {
		validador.existeMuchasVecesElMismoProyecto(this);
	}

	public ValidadorImportadorProyecto getValidador() {
		return validador;
	}

	public boolean validarEImportarFila(Row unaFila, ImportadorProyectoBuilder builder) {
		boolean filaValida = validarFila(unaFila);
		String nombreProyecto = SolapaProyecto.getCellStringValue(unaFila.getCell(0).getCellType(),
				unaFila.getCell(0, Row.CREATE_NULL_AS_BLANK));

		boolean esProyectoExistenteYActualizableONuevo = !StringUtils.isEmpty(nombreProyecto)
				&& esProyectoExistenteYActualizableONuevo(nombreProyecto);

		if (filaValida && esProyectoExistenteYActualizableONuevo) {
			Cell celdaOj = unaFila.getCell(celdaCodigoOJ, Row.CREATE_NULL_AS_BLANK);
			Cell celdaOop = unaFila.getCell(celdaCodigoOOp, Row.CREATE_NULL_AS_BLANK);
			String codigoOJ = SolapaProyecto.getCellStringValue(celdaOj.getCellType(), celdaOj);
			String codigoOOp = SolapaProyecto.getCellStringValue(celdaOop.getCellType(), celdaOop);

			try {

				builder.cargarProyecto(nombreProyecto)
				.cargarObjetivoJurisdiccional(unaFila.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
						codigoOJ)
				.cargarObjetivoOperativo(unaFila.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
						codigoOOp)

				.cargarProyectoDescripcion(SolapaProyecto.getCellStringValue(
						unaFila.getCell(3, Row.CREATE_NULL_AS_BLANK).getCellType(),
						unaFila.getCell(3, Row.CREATE_NULL_AS_BLANK)))
				.cargarProyectoMeta(unaFila.getCell(4, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())
				.cargarProyectoUnidadMeta(unaFila.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoPoblacionAfectada(
						unaFila.getCell(6, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())
				.cargarPoblacionMeta(unaFila.getCell(7, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarPoblacionMeta(unaFila.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarPoblacionMeta(unaFila.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarPoblacionMeta(unaFila.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoLider(unaFila.getCell(11, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoArea(unaFila.getCell(12, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoCorresponsable(
						unaFila.getCell(13, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoTipoUbucacion(unaFila.getCell(14, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoDireccion(unaFila.getCell(15, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarComuna(unaFila.getCell(16, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarComuna(unaFila.getCell(17, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarComuna(unaFila.getCell(18, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarComuna(unaFila.getCell(19, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoFechaInicio(unaFila.getCell(20, Row.CREATE_NULL_AS_BLANK).getDateCellValue())
				.cargarProyectoFechaFin(unaFila.getCell(21, Row.CREATE_NULL_AS_BLANK).getDateCellValue())

				.cargarPresupuestoPorAnio(unaFila.getCell(22, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
						unaFila.getCell(23, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
						unaFila.getCell(24, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())
				.cargarPresupuestoPorAnio(unaFila.getCell(25, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
						unaFila.getCell(26, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
						unaFila.getCell(27, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())
				.cargarPresupuestoPorAnio(unaFila.getCell(28, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
						unaFila.getCell(29, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
						unaFila.getCell(30, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())
				.cargarPresupuestoPorAnio(unaFila.getCell(31, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
						unaFila.getCell(32, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
						unaFila.getCell(33, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())

				.cargarProyectoTipoProyecto(unaFila.getCell(35, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarEjeDeGobierno(unaFila.getCell(36, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarEjeDeGobierno(unaFila.getCell(37, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarEjeDeGobierno(unaFila.getCell(38, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoImplicaCambioLegislativo(
						unaFila.getCell(39, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
				.cargarProyectoPrioridadJurisdiccional(
						unaFila.getCell(40, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			} catch (IllegalStateException | NumberFormatException e) {
				SolapaProyecto.getLogger().error("Hubo un error de formato o estado ilegal en la fila "
						+ unaFila.getRowNum() + "\n" + e.getMessage());
				LOGGER.debug(e.getMessage());
				getValidador().agregarMensajeParaFila(unaFila.getRowNum(),
						"Hubo un error de formato o estado ilegal en la fila " + unaFila.getRowNum() + ".",
						MensajeError.TIPO_ERROR);
				filaValida = false;
			}

		}
		return filaValida && esProyectoExistenteYActualizableONuevo;
	}

	public Sheet getSolapa() {
		return solapa;
	}

	public void setSolapa(Sheet solapa) {
		this.solapa = solapa;
	}

	public Iterator<Row> getFilas() {
		return solapa.iterator();
	}

	public Jurisdiccion getJurisdiccion() {
		return validador.getJurisdiccion();
	}

	public Row getFilaNumero(int numeroFila) {
		return solapa.getRow(numeroFila);
	}

	public boolean esUltimaFila(Row unaFila) {
		boolean todoEnBlanco = true;
		for (int i = 0; i < Integer.parseInt(env.getProperty("proyecto.total.celdas")) && todoEnBlanco; i++) {
			todoEnBlanco = todoEnBlanco
					&& ((unaFila.getCell(i, Row.CREATE_NULL_AS_BLANK).getCellType() == Cell.CELL_TYPE_BLANK)
							|| unaFila.getCell(i, Row.CREATE_NULL_AS_BLANK).getCellType() == Cell.CELL_TYPE_FORMULA);

		}
		return todoEnBlanco;
	}

	public int getNumeroFilaInicioImportacion() {
		return numeroFilaInicioImportacion;
	}

	public int getNumeroUltimaFila() {
		return numeroUltimaFila;
	}

	public void removerFila(Row unaFila) {
		solapa.removeRow(unaFila);

	}

	private int numeroUltimaFila() {
		boolean esUltimaFila = false;
		int numeroUltimaFila = 0;

		for (Iterator<Row> unaFila = getFilas(); unaFila.hasNext() && !esUltimaFila;) {
			Row fila = unaFila.next();
			numeroUltimaFila = fila.getRowNum();
			if (numeroUltimaFila >= getNumeroFilaInicioImportacion()) {
				esUltimaFila = esUltimaFila(fila);
			}

		}
		return numeroUltimaFila;
	}

	public int getNumeroFilaNombreColumnas() {
		return numeroFilaNombreColumnas;
	}

	public void setNumeroFilaNombreColumnas(int numeroFilaNombreColumnas) {
		this.numeroFilaNombreColumnas = numeroFilaNombreColumnas;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	public Row copiarFilas(Row filaOrigen, Row filaDestino) {
		XSSFCell oldCell;
		XSSFCell newCell;
		CellStyle cellStyleCloner = filaDestino.getSheet().getWorkbook().createCellStyle();
		List<Integer> celdasDate = Arrays.asList(20, 21);
		List<Integer> celdasCodigoObjetivos = Arrays.asList(celdaCodigoOJ, celdaCodigoOOp);
		List<Integer> celdasMoneda = Arrays.asList(23, 24, 26, 27, 29, 30, 32, 33);
		List<Integer> celdasPresupuestoAnios = Arrays.asList(22, 25, 28, 31);
		List<Integer> numeroCeldaNumericas = Arrays.asList(4, 6);

		// Loop through source columns to add to new row
		for (int i = 0; i < filaOrigen.getLastCellNum(); i++) {
			// Grab a copy of the old/new cell
			oldCell = ((XSSFRow) filaOrigen).getCell(i, Row.CREATE_NULL_AS_BLANK);
			newCell = ((XSSFRow) filaDestino).createCell(i);

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());
			// Copy style from old cell and apply to new cell
			cellStyleCloner.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(cellStyleCloner);
			CellStyle cellStyleAccountingCloner = filaDestino.getSheet().getWorkbook().createCellStyle();
			CreationHelper ch = filaDestino.getSheet().getWorkbook().getCreationHelper();
			// Estilo date
			CellStyle cellStyleDateCloner = filaDestino.getSheet().getWorkbook().createCellStyle();
			cellStyleDateCloner.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));

			// Estilo number
			CellStyle cellStyleNumeric = filaDestino.getSheet().getWorkbook().createCellStyle();
			cellStyleNumeric.setDataFormat((short) 1);

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				if (celdasMoneda.contains(oldCell.getColumnIndex())) {
					cellStyleAccountingCloner.setDataFormat((short) 7);
					newCell.setCellStyle(cellStyleAccountingCloner);
					newCell.setCellType(Cell.CELL_TYPE_NUMERIC);

				} else if (celdasDate.contains(oldCell.getColumnIndex())) {
					newCell.setCellStyle(cellStyleDateCloner);
				} else if (numeroCeldaNumericas.contains(oldCell.getColumnIndex())) {
					newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
					newCell.setCellStyle(cellStyleNumeric);
				} else {
					newCell.setCellValue(oldCell.getStringCellValue());
				}
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				if (celdasMoneda.contains(oldCell.getColumnIndex())) {
					cellStyleAccountingCloner.setDataFormat((short) 7);
					newCell.setCellStyle(cellStyleAccountingCloner);
					if (oldCell.getCachedFormulaResultType() != Cell.CELL_TYPE_ERROR) {
						String rowNum = String.valueOf(newCell.getRowIndex() + 1);
						String xColumn = CellReference.convertNumToColString(oldCell.getColumnIndex() - 11)
								.concat(rowNum);
						String aaColumn = CellReference.convertNumToColString(oldCell.getColumnIndex() - 8)
								.concat(rowNum);
						String adColumn = CellReference.convertNumToColString(oldCell.getColumnIndex() - 5)
								.concat(rowNum);
						String agColumn = CellReference.convertNumToColString(oldCell.getColumnIndex() - 2)
								.concat(rowNum);
						newCell.setCellFormula("Proyectos!" + agColumn + "+Proyectos!" + adColumn + "+Proyectos!"
								+ aaColumn + "+Proyectos!" + xColumn);
					} else {
						newCell.setCellErrorValue(FormulaError.VALUE);
					}
				} else if (celdasPresupuestoAnios.contains(oldCell.getColumnIndex())) {
					newCell.setCellStyle(cellStyleAccountingCloner);
					if (oldCell.getCachedFormulaResultType() == Cell.CELL_TYPE_ERROR) {
						newCell.setCellErrorValue(FormulaError.VALUE);
					} else {
						newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
						if (oldCell.getNumericCellValue() > 0) {
							newCell.setCellValue(oldCell.getNumericCellValue());
						}
					}
				} else if (celdasCodigoObjetivos.contains(oldCell.getColumnIndex())) {
					if (oldCell.getCachedFormulaResultType() == Cell.CELL_TYPE_ERROR) {
						newCell.setCellErrorValue(FormulaError.VALUE);
					} else {
						newCell.setCellType(Cell.CELL_TYPE_STRING);
						newCell.setCellValue(oldCell.getStringCellValue());
					}
				}
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (celdasDate.contains(oldCell.getColumnIndex())) {
					newCell.setCellStyle(cellStyleDateCloner);
					newCell.setCellValue(oldCell.getDateCellValue());

				} else if (celdasMoneda.contains(oldCell.getColumnIndex())) {
					cellStyleAccountingCloner.setDataFormat((short) 7);
					newCell.setCellStyle(cellStyleAccountingCloner);
					newCell.setCellValue(oldCell.getNumericCellValue());
					newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				} else {
					newCell.setCellValue(oldCell.getNumericCellValue());
					newCell.setCellType(Cell.CELL_TYPE_NUMERIC);
				}
				break;
			case Cell.CELL_TYPE_STRING:
				newCell.setCellValue(oldCell.getRichStringCellValue());
				break;
			}

		}
		return filaDestino;
	}

	private boolean esProyectoExistenteYActualizableONuevo(String nombreProyecto) {
		boolean esProyectoExistenteYActualizable = pisarProyectos;
		try {
			Proyecto pr = proyectoService.getProyectoPorNombreIdJurisdiccion(nombreProyecto,
					getValidador().getJurisdiccion().getIdJurisdiccion());
			esProyectoExistenteYActualizable = (pr == null || (pr != null && pisarProyectos));
		} catch (ESBException | JMSException e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
		return esProyectoExistenteYActualizable;
	}

	public static String getCellStringValue(int cellType, Cell celda) {
		String returnValue = "";

		switch (cellType) {
		case Cell.CELL_TYPE_BLANK:
		case Cell.CELL_TYPE_STRING:
			returnValue = celda.getStringCellValue();
			break;
		case Cell.CELL_TYPE_BOOLEAN:
			returnValue = celda.getBooleanCellValue() ? "Si" : "No";
			break;
		case Cell.CELL_TYPE_ERROR:
			returnValue = ((XSSFCell) celda).getErrorCellString();
			break;
		case Cell.CELL_TYPE_FORMULA:
			returnValue = getCellStringValue(celda.getCachedFormulaResultType(), celda);
			break;
		case Cell.CELL_TYPE_NUMERIC:
			returnValue = String.valueOf(celda.getNumericCellValue());
			break;
		}

		return returnValue;
	}

}
