package ar.gob.buenosaires.importador.proyecto.priorizado;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.importador.ResultadoProcesamiento;
import ar.gob.buenosaires.importador.proyecto.ProyectoImportadoDTO;

public class ProyectosPriorizadosResultadoProcesamiento extends ResultadoProcesamiento {

	private Map<String, List<String>> erroresDeSolapaMapa = new HashMap<>();

	private Double importeAprobado = new Double(0);

	private int totalProyectosAprobados = 0;

	public ProyectosPriorizadosResultadoProcesamiento(final int celdaFI, final int celdaFF,
			final int celdaNombreProyecto) {
		super(celdaFI, celdaFF, celdaNombreProyecto);
	}

	public ProyectosPriorizadosResultadoProcesamiento() {
	}

	public void sumarTotalPresupuesto() {
		for (ProyectoImportadoDTO proyecto : getProyectoProcesados()) {
			if (proyecto.getEstado() != null
					&& proyecto.getEstado().equalsIgnoreCase(EstadoProyecto.PREAPROBADO.getName())) {
				totalProyectosAprobados++;
				importeAprobado += proyecto.getImporteAprobado();
			}
		}
	}

	/**
	 * @return the erroresDeSolapaMapa
	 */
	public Map<String, List<String>> getErroresDeSolapaMapa() {
		return erroresDeSolapaMapa;
	}

	/**
	 * @param erroresDeSolapaMapa
	 *            the erroresDeSolapaMapa to set
	 */
	public void setErroresDeSolapaMapa(Map<String, List<String>> erroresDeSolapaMapa) {
		this.erroresDeSolapaMapa = erroresDeSolapaMapa;
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

	public int getTotalProyectosAprobados() {
		return totalProyectosAprobados;
	}

	public void setTotalProyectosAprobados(int totalProyectosAprobados) {
		this.totalProyectosAprobados = totalProyectosAprobados;
	}
}
