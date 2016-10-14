package ar.gob.buenosaires.importador.proyecto.priorizado;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.core.env.Environment;

public class ProyectoPriorizadoFila {

	private Row filaOriginal;

	private Environment env;

	ProyectoPriorizadoConfiguracionFila configuracionFila;

	private ProyectoPriorizadoSolapa solapa;

	public ProyectoPriorizadoFila(Row row, Environment env, ProyectoPriorizadoSolapa solapa) {
		filaOriginal = row;
		this.env = env;
		this.solapa = solapa;
		configuracionFila = ProyectoPriorizadoConfiguracionFila.getInstance(env);
	}

	/**
	 * @return the numeroCeldaIdProyecto
	 */
	public final int getNumeroCeldaIdProyecto() {
		return configuracionFila.getNumeroCeldaIdProyecto();
	}

	/**
	 * @return the numeroCeldaNombreProyecto
	 */
	public final int getNumeroCeldaNombreProyecto() {
		return configuracionFila.getNumeroCeldaNombreProyecto();
	}

	/**
	 * @return the numeroCeldaFechaInicio
	 */
	public final int getNumeroCeldaFechaInicio() {
		return configuracionFila.getNumeroCeldaFechaInicio();
	}

	/**
	 * @return the numeroPrioridadJefatura
	 */
	public final int getNumeroCeldaPrioridadJefatura() {
		return configuracionFila.getNumeroCeldaPrioridadJefatura();
	}

	/**
	 * @return the numeroCeldaEstadoAprobacion
	 */
	public final int getNumeroCeldaEstadoAprobacion() {
		return configuracionFila.getNumeroCeldaEstadoAprobacion();
	}

	/**
	 * @return the filaOriginal
	 */
	public final Row getFilaOriginal() {
		return filaOriginal;
	}

	public String getIdProyecto() {
		ProyectoPriorizadoCelda celdaProyecto = new ProyectoPriorizadoCelda(
				filaOriginal.getCell(configuracionFila.getNumeroCeldaIdProyecto(), Row.CREATE_NULL_AS_BLANK));
		return celdaProyecto.getSringValueParaCualquierTipo();
	}

	public int getNumeroFila() {
		return filaOriginal.getRowNum() + 1;
	}

	public String getEstadoAprobacion() {
		ProyectoPriorizadoCelda celdaEstadoAprobacion = getCeldaNumero(
				configuracionFila.getNumeroCeldaEstadoAprobacion());
		return celdaEstadoAprobacion.getSringValueParaCualquierTipo();
	}

	public Double getPresuAprobadoTotal() {
		ProyectoPriorizadoCelda celdaPresuAprobadoTotal = getCeldaNumero(
				configuracionFila.getNumeroCeldaPresuAprobadoTotal());
		return celdaPresuAprobadoTotal.getDoubleValueCeroIfNull();
	}

	public String getPrioridadJefatura() {
		ProyectoPriorizadoCelda celdaPrioridadJefatura = getCeldaNumero(
				configuracionFila.getNumeroCeldaPrioridadJefatura());
		return celdaPrioridadJefatura.getSringValueParaCualquierTipo();
	}

	public List<String> getNombresTemaTransversalAsignados() {
		List<String> nombresTemasTransversalesAsignados = new ArrayList<>();
		String valorCeldaFilaActual;
		for (ProyectoPriorizadoCelda celdaFilaHeader : solapa.getCeldasTemasTransversales()) {
			valorCeldaFilaActual = getCeldaNumero(celdaFilaHeader.getNumeroColumna()).getSringValueParaCualquierTipo();

			if (valorCeldaFilaActual.equalsIgnoreCase("X") || !valorCeldaFilaActual.isEmpty()) {
				nombresTemasTransversalesAsignados.add(celdaFilaHeader.getSringValueParaCualquierTipo()
						.replace(ProyectoPriorizadoSolapa.PREFIJO_TEMA_TRANSVERSAL, ""));
			}
		}
		return nombresTemasTransversalesAsignados;
	}

	public List<ProyectoPriorizadoCelda> getCeldas() {
		List<ProyectoPriorizadoCelda> listoCeldas = new ArrayList<>();
		getFilaOriginal().cellIterator().forEachRemaining(new Consumer<Cell>() {

			@Override
			public void accept(Cell arg0) {
				listoCeldas.add(new ProyectoPriorizadoCelda(arg0));
			}
		});
		return listoCeldas;
	}

	public Double getPresuTotal() {
		ProyectoPriorizadoCelda celdaPresuAprobadoTotal = getCeldaNumero(configuracionFila.getNumeroCeldaPresuTotal());
		return celdaPresuAprobadoTotal.getDoubleValueCeroIfNull();
	}

	public int getNumeroUltimaCelda() {
		return getFilaOriginal().getLastCellNum();
	}

	private ProyectoPriorizadoCelda getCeldaNumero(int numeroCelda) {
		return new ProyectoPriorizadoCelda(filaOriginal.getCell(numeroCelda, Row.CREATE_NULL_AS_BLANK));
	}

}
