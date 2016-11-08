package ar.gob.buenosaires.importador.proyecto;

import java.util.Date;

public class ProyectoImportadoDTO {

	private Long idProyecto;

	private String nombreProyecto;

	private Date fechaInicio;

	private Date fechaFin;

	private String estado;

	private Double importeAprobado;

	/**
	 * @return the idProyecto
	 */
	public final Long getIdProyecto() {
		return idProyecto;
	}

	/**
	 * @param idProyecto
	 *            the idProyecto to set
	 */
	public final void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	/**
	 * @return the nombreProyecto
	 */
	public final String getNombreProyecto() {
		return nombreProyecto;
	}

	/**
	 * @param nombreProyecto
	 *            the nombreProyecto to set
	 */
	public final void setNombreProyecto(String nombreProyecto) {
		this.nombreProyecto = nombreProyecto;
	}

	/**
	 * @return the fechaInicio
	 */
	public final Date getFechaInicio() {
		return fechaInicio;
	}

	/**
	 * @param fechaInicio
	 *            the fechaInicio to set
	 */
	public final void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	/**
	 * @return the fechaFin
	 */
	public final Date getFechaFin() {
		return fechaFin;
	}

	/**
	 * @param fechaFin
	 *            the fechaFin to set
	 */
	public final void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	/**
	 * @return the estado
	 */
	public final String getEstado() {
		return estado;
	}

	/**
	 * @param estado
	 *            the estado to set
	 */
	public final void setEstado(String estado) {
		this.estado = estado;
	}

	public Double getImporteAprobado() {
		if(importeAprobado == null){
			importeAprobado = new Double(0);
		}
		return importeAprobado;
	}

	public void setImporteAprobado(Double importeAprobado) {
		this.importeAprobado = importeAprobado;
	}

}
