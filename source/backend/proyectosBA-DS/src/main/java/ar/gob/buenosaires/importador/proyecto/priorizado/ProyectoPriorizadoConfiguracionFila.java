package ar.gob.buenosaires.importador.proyecto.priorizado;

import org.springframework.core.env.Environment;

public class ProyectoPriorizadoConfiguracionFila {

	private static volatile ProyectoPriorizadoConfiguracionFila INSTANCE = null;

	private static int numeroCeldaIdProyecto;

	private static int numeroCeldaNombreProyecto;

	private static int numeroCeldaFechaFin;

	private static int numeroCeldaFechaInicio;

	private static int numeroCeldaEstadoAprobacion;

	private static int numeroCeldaPresuAprobadoTotal;

	private static int numeroCeldaPrioridadJefatura;

	private static Integer numeroCeldaPresuTotal;

	private ProyectoPriorizadoConfiguracionFila() {

	}

	public static ProyectoPriorizadoConfiguracionFila getInstance(Environment env) {

		if (INSTANCE == null) {
			synchronized (ProyectoPriorizadoConfiguracionFila.class) {
				if (INSTANCE == null) {
					INSTANCE = new ProyectoPriorizadoConfiguracionFila();
				}
			}
		}
		numeroCeldaIdProyecto = Integer.parseInt(env.getProperty("proyecto.priorizado.num.celda.idproyecto"));
		numeroCeldaNombreProyecto = Integer.valueOf(env.getProperty("proyecto.priorizado.num.celda.nombreProyecto"));
		numeroCeldaFechaFin = Integer.valueOf(env.getProperty("proyecto.priorizado.num.celda.fechaFin"));
		numeroCeldaFechaInicio = Integer.valueOf(env.getProperty("proyecto.priorizado.num.celda.fechaInicio"));
		numeroCeldaEstadoAprobacion = Integer
				.valueOf(env.getProperty("proyecto.priorizado.num.celda.estadoAprobacion"));
		numeroCeldaPresuAprobadoTotal = Integer
				.valueOf(env.getProperty("proyecto.priorizado.num.celda.presupuestoAprobadoTotal"));
		numeroCeldaPresuTotal = Integer
				.valueOf(env.getProperty("proyecto.priorizado.num.celda.presupuestoTotal"));
		numeroCeldaPrioridadJefatura = Integer
				.valueOf(env.getProperty("proyecto.priorizado.num.celda.prioridadJefatura"));
		return INSTANCE;
	}

	/**
	 * @return the numeroCeldaIdProyecto
	 */
	public final int getNumeroCeldaIdProyecto() {
		return numeroCeldaIdProyecto;
	}

	/**
	 * @return the numeroCeldaNombreProyecto
	 */
	public final int getNumeroCeldaNombreProyecto() {
		return numeroCeldaNombreProyecto;
	}

	/**
	 * @return the numeroCeldaFechaFin
	 */
	public final int getNumeroCeldaFechaFin() {
		return numeroCeldaFechaFin;
	}

	/**
	 * @return the numeroCeldaFechaInicio
	 */
	public final int getNumeroCeldaFechaInicio() {
		return numeroCeldaFechaInicio;
	}

	/**
	 * @return the numeroCeldaEstadoAprobacion
	 */
	public final int getNumeroCeldaEstadoAprobacion() {
		return numeroCeldaEstadoAprobacion;
	}

	/**
	 * @return the numeroCeldaPresuAprobadoTotal
	 */
	public final int getNumeroCeldaPresuAprobadoTotal() {
		return numeroCeldaPresuAprobadoTotal;
	}

	/**
	 * @return the numeroCeldaPrioridadJefatura
	 */
	public final int getNumeroCeldaPrioridadJefatura() {
		return numeroCeldaPrioridadJefatura;
	}

	/**
	 * @return the numeroCeldaPresuTotal
	 */
	public Integer getNumeroCeldaPresuTotal() {
		return numeroCeldaPresuTotal;
	}

}
