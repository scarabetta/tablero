package ar.gob.buenosaires.importador.proyecto;

import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import javax.jms.JMSException;

import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.DateFormatConverter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.importador.MensajeError;
import ar.gob.buenosaires.service.ProyectoService;
import ar.gob.buenosaires.service.impl.ImportarProyectoServiceImpl;

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

	private int numeroFilaInicioImportacion;

	private int numeroFilaNombreColumnas;

	private int numeroUltimaFila;

	private boolean pisarProyectos = false;

	public List<MensajeError> validarSolapa() {
		return validador.validarSolapa(this);

	}

	public void configurarSolapa() {
		this.numeroFilaNombreColumnas = Integer.parseInt(env.getProperty("proyecto.fila.col.nombres"));
		this.numeroFilaInicioImportacion = this.numeroFilaNombreColumnas + 1;
		this.numeroUltimaFila = this.numeroUltimaFila();

	}

	public void configurarSolapa(boolean pisarProyectos) {
		this.numeroFilaNombreColumnas = Integer.parseInt(env.getProperty("proyecto.fila.col.nombres"));
		this.numeroFilaInicioImportacion = this.numeroFilaNombreColumnas + 1;
		this.numeroUltimaFila = this.numeroUltimaFila();
		this.pisarProyectos = pisarProyectos;

	}

	public Iterator<Cell> getCeldasDeFila(int numeroFila) {
		return this.solapa.getRow(numeroFila).cellIterator();
	}

	public boolean validarFila(Row unaFila) {
		return this.validador.validarFila(unaFila);
	}

	public void validarProyectosRepetidos() {
		this.validador.existeMuchasVecesElMismoProyecto(this);
	}

	public ValidadorImportadorProyecto getValidador() {
		return validador;
	}

	public boolean validarEImportarFila(Row unaFila, ImportadorProyectoBuilder builder) {
		boolean filaValida = this.validarFila(unaFila);
		String nombreProyecto = unaFila.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
		boolean esProyectoExistenteYActualizableONuevo = !StringUtils.isEmpty(nombreProyecto) && this.esProyectoExistenteYActualizableONuevo(nombreProyecto); 

		if (filaValida && esProyectoExistenteYActualizableONuevo) {
			String codigoOJ = "";
			String codigoOOp = "";

			try {
				if (Cell.CELL_TYPE_FORMULA == unaFila.getCell(37, Row.CREATE_NULL_AS_BLANK).getCellType()) {
					codigoOJ = unaFila.getCell(37, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
				}

				if (Cell.CELL_TYPE_FORMULA == unaFila.getCell(38, Row.CREATE_NULL_AS_BLANK).getCellType()) {
					codigoOOp = unaFila.getCell(38, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
				}
				builder.cargarProyecto(nombreProyecto)
						.cargarObjetivoJurisdiccional(unaFila.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
								codigoOJ)
						.cargarObjetivoOperativo(unaFila.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
								codigoOOp)

						.cargarProyectoDescripcion(unaFila.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
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
								unaFila.getCell(23, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())
						.cargarPresupuestoPorAnio(unaFila.getCell(24, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
								unaFila.getCell(25, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())
						.cargarPresupuestoPorAnio(unaFila.getCell(26, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
								unaFila.getCell(27, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())
						.cargarPresupuestoPorAnio(unaFila.getCell(28, Row.CREATE_NULL_AS_BLANK).getNumericCellValue(),
								unaFila.getCell(29, Row.CREATE_NULL_AS_BLANK).getNumericCellValue())

						.cargarProyectoTipoProyecto(unaFila.getCell(31, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
						.cargarEjeDeGobierno(unaFila.getCell(32, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
						.cargarEjeDeGobierno(unaFila.getCell(33, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
						.cargarEjeDeGobierno(unaFila.getCell(34, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
						.cargarProyectoImplicaCambioLegislativo(
								unaFila.getCell(35, Row.CREATE_NULL_AS_BLANK).getStringCellValue())
						.cargarProyectoPrioridadJurisdiccional(unaFila.getCell(36, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
			} catch (IllegalStateException | NumberFormatException e) {
				SolapaProyecto.getLogger().error("Hubo un error de formato o estado ilegal en la fila "
						+ unaFila.getRowNum() + "\n" + e.getMessage());
				LOGGER.debug(e.getMessage());
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
		return this.validador.getJurisdiccion();
	}

	public Row getFilaNumero(int numeroFila) {
		return this.solapa.getRow(numeroFila);
	}

	public boolean esUltimaFila(Row unaFila) {
		boolean todoEnBlanco = true;
		for (int i = 0; i < 37; i++) {
			todoEnBlanco = todoEnBlanco
					&& (unaFila.getCell(i, Row.CREATE_NULL_AS_BLANK).getStringCellValue().isEmpty());

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
		this.solapa.removeRow(unaFila);

	}

	private int numeroUltimaFila() {
		boolean esUltimaFila = false;
		int numeroUltimaFila = 0;

		for (Iterator<Row> unaFila = this.getFilas(); unaFila.hasNext() && !esUltimaFila;) {
			Row fila = (Row) unaFila.next();
			numeroUltimaFila = fila.getRowNum();
			if (numeroUltimaFila > this.getNumeroFilaInicioImportacion()) {
				esUltimaFila = this.esUltimaFila(fila);
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

		// Loop through source columns to add to new row
		for (int i = 0; i < filaOrigen.getLastCellNum(); i++) {
			// System.out.println("----------------------> Clonando fila " +
			// filaDestino.getRowNum() + " celda: " + i);
			// Grab a copy of the old/new cell
			oldCell = ((XSSFRow) filaOrigen).getCell(i, Row.CREATE_NULL_AS_BLANK);
			newCell = ((XSSFRow) filaDestino).createCell(i);

			// Set the cell data type
			newCell.setCellType(oldCell.getCellType());
			// Copy style from old cell and apply to new cell
			cellStyleCloner.cloneStyleFrom(oldCell.getCellStyle());
			newCell.setCellStyle(cellStyleCloner);

			// Set the cell data value
			switch (oldCell.getCellType()) {
			case Cell.CELL_TYPE_BLANK:
				newCell.setCellValue(oldCell.getStringCellValue());
				break;
			case Cell.CELL_TYPE_BOOLEAN:
				newCell.setCellValue(oldCell.getBooleanCellValue());
				break;
			case Cell.CELL_TYPE_ERROR:
				newCell.setCellErrorValue(oldCell.getErrorCellValue());
				break;
			case Cell.CELL_TYPE_FORMULA:
				if (oldCell.getColumnIndex() == 30) {
					CellStyle cellStyleAccountingCloner = filaDestino.getSheet().getWorkbook().createCellStyle();
					cellStyleAccountingCloner.setDataFormat((short) 7);
					newCell.setCellStyle(cellStyleAccountingCloner);
				}
				newCell.setCellFormula(oldCell.getCellFormula().replace(String.valueOf(filaOrigen.getRowNum() + 1),
						String.valueOf(filaDestino.getRowNum() + 1)));
				break;
			case Cell.CELL_TYPE_NUMERIC:
				if (oldCell.getColumnIndex() == 20) {
					CreationHelper ch = filaDestino.getSheet().getWorkbook().getCreationHelper();
					CellStyle cellStyleDateCloner = filaDestino.getSheet().getWorkbook().createCellStyle();
					cellStyleDateCloner.setDataFormat(ch.createDataFormat().getFormat("dd/MM/yyyy"));
					newCell.setCellStyle(cellStyleDateCloner);
					newCell.setCellValue(oldCell.getDateCellValue());
				} else {
					newCell.setCellValue(oldCell.getNumericCellValue());
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
		boolean esProyectoExistenteYActualizable = true;
		try {
			Proyecto pr = proyectoService.getProyectoByName(nombreProyecto);
			esProyectoExistenteYActualizable = (pr == null || (pr != null && this.pisarProyectos));
		} catch (ESBException | JMSException e) {
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
		return esProyectoExistenteYActualizable;
	}

}
