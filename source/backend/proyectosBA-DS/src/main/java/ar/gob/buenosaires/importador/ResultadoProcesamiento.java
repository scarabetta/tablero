package ar.gob.buenosaires.importador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.importador.proyecto.ProyectoImportadoDTO;
import ar.gob.buenosaires.importador.proyecto.ProyectoImportadoFallidoDTO;
import ar.gob.buenosaires.importador.proyecto.SolapaProyecto;

public class ResultadoProcesamiento {

	private String nombreArchivoError;

	private String errorGenerico;

	private List<ProyectoImportadoDTO> proyectoProcesados = new ArrayList<>();

	private List<String> erroresDeSolapa = new ArrayList<>();

	private int numeroCeldaFI = 20;

	private int numeroCeldaFF = 21;

	private int numeroCeldaNombreProyecto = 0;

	public ResultadoProcesamiento() {
	}

	public ResultadoProcesamiento(final int celdaFI, final int celdaFF, final int celdaNombreProyecto) {
		numeroCeldaFI = celdaFI;
		numeroCeldaFF = celdaFF;
		numeroCeldaNombreProyecto = celdaNombreProyecto;
	}

	/**
	 * @return the erroresDeSolapa
	 */
	public List<String> getErroresDeSolapa() {
		return erroresDeSolapa;
	}

	/**
	 * @param erroresDeSolapa
	 *            the erroresDeSolapa to set
	 */
	public void setErroresDeSolapa(List<String> erroresDeSolapa) {
		this.erroresDeSolapa = erroresDeSolapa;
	}

	/**
	 * @return the proyectoProcesados
	 */
	public final List<ProyectoImportadoDTO> getProyectoProcesados() {
		return proyectoProcesados;
	}

	/**
	 * @param proyectoProcesados
	 *            the proyectoProcesados to set
	 */
	public final void setProyectoProcesados(List<ProyectoImportadoDTO> proyectoProcesados) {
		this.proyectoProcesados = proyectoProcesados;
	}

	/**
	 * @return the nombreArchivoError
	 */
	public final String getNombreArchivoError() {
		return nombreArchivoError;
	}

	/**
	 * @param nombreArchivoError
	 *            the nombreArchivoError to set
	 */
	public final void setNombreArchivoError(String nombreArchivoError) {
		this.nombreArchivoError = nombreArchivoError;
	}

	/**
	 * @return the errorGenerico
	 */
	public String getErrorGenerico() {
		return errorGenerico;
	}

	/**
	 * @param errorGenerico
	 *            the errorGenerico to set
	 */
	public void setErrorGenerico(String errorGenerico) {
		this.errorGenerico = errorGenerico;
	}

	public void agregarProyecto(Proyecto unProyecto) {
		ProyectoImportadoDTO proyectoImportadoDTO = new ProyectoImportadoDTO();

		buidProyectoDTOFromProyecto(proyectoImportadoDTO, unProyecto.getIdProyecto(), unProyecto.getNombre(),
				unProyecto.getEstado(), unProyecto.getFechaInicio(), unProyecto.getFechaFin());
		proyectoImportadoDTO.setImporteAprobado(unProyecto.getTotalPresupuestoAprobado());
		getProyectoProcesados().add(proyectoImportadoDTO);

	}

	public ProyectoImportadoFallidoDTO agregarProyectoFallido(Row unaFila, List<MensajeError> errores) {
		ProyectoImportadoFallidoDTO unProyectoDTO = buidProyectoFallidoDTOFromProyecto(unaFila.getRowNum() + 1, errores,
				null, unaFila);
		getProyectoProcesados().add(unProyectoDTO);
		return unProyectoDTO;
	}

	public void agregarErrorSolapa(String unError) {
		getErroresDeSolapa().add(unError);

	}

	private ProyectoImportadoFallidoDTO buidProyectoFallidoDTOFromProyecto(int numeroFila, List<MensajeError> errores,
			Long id, Row unaFila) {
		String nombre, estado;
		Date fechaInicio, fechaFin;
		Cell celdaNombreProyecto = unaFila.getCell(numeroCeldaNombreProyecto, Row.CREATE_NULL_AS_BLANK);
		Cell celdaFi = unaFila.getCell(numeroCeldaFI, Row.CREATE_NULL_AS_BLANK);
		Cell celdaFf = unaFila.getCell(numeroCeldaFF, Row.CREATE_NULL_AS_BLANK);

		estado = null;
		nombre = SolapaProyecto.getCellStringValue(celdaNombreProyecto.getCellType(), celdaNombreProyecto);
		fechaInicio = Cell.CELL_TYPE_NUMERIC == celdaFi.getCellType() ? celdaFi.getDateCellValue() : null;
		fechaFin = Cell.CELL_TYPE_NUMERIC == celdaFf.getCellType() ? celdaFf.getDateCellValue() : null;

		ProyectoImportadoFallidoDTO unProyectoFallidoDTO = new ProyectoImportadoFallidoDTO();
		buidProyectoDTOFromProyecto(unProyectoFallidoDTO, id, nombre, estado, fechaInicio, fechaFin);
		unProyectoFallidoDTO.setNumeroFila(numeroFila);
		errores.parallelStream().forEach(new Consumer<MensajeError>() {

			@Override
			public void accept(MensajeError t) {
				unProyectoFallidoDTO.agregarMensajeError(t.getMensaje());
			}
		});
		return unProyectoFallidoDTO;
	}

	private void buidProyectoDTOFromProyecto(ProyectoImportadoDTO unProyectoDTO, Long id, String nombre, String estado,
			Date fechaInicio, Date fechaFin) {

		unProyectoDTO.setIdProyecto(id);
		unProyectoDTO.setNombreProyecto(nombre);
		unProyectoDTO.setEstado(estado);
		unProyectoDTO.setFechaInicio(fechaInicio);
		unProyectoDTO.setFechaFin(fechaFin);
	}
}
