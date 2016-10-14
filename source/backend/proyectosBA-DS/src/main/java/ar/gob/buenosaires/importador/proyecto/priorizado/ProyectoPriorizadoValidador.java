package ar.gob.buenosaires.importador.proyecto.priorizado;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.IServiceFactory;
import ar.gob.buenosaires.service.ProyectoService;

@Component
public class ProyectoPriorizadoValidador {

	@Autowired
	Environment env;

	@Autowired
	private IServiceFactory serviceFactory;

	public List<String> validarSolapaRestrictivo(ProyectoPriorizadoSolapa solapa, String userMail)
			throws ESBException, JMSException {
		List<String> problemasRestrictivosSolapa = new ArrayList<>();
		problemasRestrictivosSolapa.add(columnasValidas(solapa));
		problemasRestrictivosSolapa.add(proyectosDelArchivoExistenEnLaBase(solapa, userMail));
		return problemasRestrictivosSolapa.stream().filter(s -> s != null && StringUtils.isNotEmpty(s))
				.collect(Collectors.toList());
	}

	public List<String> validarSolapaInformativo(ProyectoPriorizadoSolapa solapa, String userMail)
			throws ESBException, JMSException {
		List<String> problemasInformativosSolapa = new ArrayList<>();
		problemasInformativosSolapa.add(todoLosProyectosEnPriorizacionEnBaseEnElArchivo(solapa, userMail));
		problemasInformativosSolapa.add(todoLosProyectosEnEstadoAprobado(solapa));
		return problemasInformativosSolapa.stream().filter(s -> s != null && StringUtils.isNotEmpty(s))
				.collect(Collectors.toList());
	}

	public Map<ProyectoPriorizadoFila, List<String>> validarFilas(ProyectoPriorizadoSolapa solapa) {
		Map<ProyectoPriorizadoFila, List<String>> problemasFila = new HashMap<>();

		for (ProyectoPriorizadoFila fila : solapa.getFilas()) {
			problemasFila.put(fila, new ArrayList<>());
			problemasFila.get(fila).add(proyectosSinEstadoVacio(fila));
			problemasFila.get(fila).add(proyectosEnEstadoRechazadoOAplazadoConPresuCero(fila));
			problemasFila.get(fila).add(presupuestoAprobadoMayorQuePresuTotal(fila));
			problemasFila.get(fila).removeIf(new Predicate<String>() {

				@Override
				public boolean test(String t) {
					return t == null || StringUtils.isEmpty(t);
				}
			});
		}

		return problemasFila;
	}

	/**
	 * Val que no esta en el CU, salio de testing
	 *
	 * @param solapa
	 */
	private String proyectosSinEstadoVacio(ProyectoPriorizadoFila fila) {
		String mensajeError = null;
		if (fila.getEstadoAprobacion() == null || StringUtils.isEmpty(fila.getEstadoAprobacion())) {
			mensajeError = "El estado de aprobación no esta cargado en el Excel.";
		}
		return mensajeError;
	}

	/**
	 * Val 1
	 *
	 * @param solapa
	 */
	private String columnasValidas(ProyectoPriorizadoSolapa solapa) {
		StringBuilder mensajeError = new StringBuilder();
		String nombreAValidar;
		String nombreValido = "";
		boolean hayError = false;
		List<String> problemasSolapa = Arrays
				.asList("El archivo seleccionado no es válido. Los errores encontrados fueron los siguientes: \n");

		for (ProyectoPriorizadoCelda celda : solapa.getFilaHeader().getCeldas()) {

			nombreValido = env.getProperty("proyecto.priorizado.col." + String.valueOf(celda.getNumeroFila()) + "."
					+ String.valueOf(celda.getNumeroColumna()) + ".nombre");
			nombreAValidar = celda.getSringValueParaCualquierTipo();

			if (nombreValido != null && !nombreValido.equalsIgnoreCase(nombreAValidar)) {
				problemasSolapa
						.add("El template ingresado no es válido. No se encontró la columna " + nombreValido + ".");
			}
		}
		if (hayError) {
			problemasSolapa.forEach(new Consumer<String>() {

				@Override
				public void accept(String t) {
					mensajeError.append(t).append("\n");

				}
			});
		}
		return mensajeError.toString();
	}

	/**
	 * Val 2 verifica que todos los proyectos (en la base) en estado “En
	 * priorización” se encuentren en el archivo buscando por Id proyecto:
	 *
	 * @param solapa
	 * @throws JMSException
	 * @throws ESBException
	 */
	private String todoLosProyectosEnPriorizacionEnBaseEnElArchivo(ProyectoPriorizadoSolapa solapa, String userMail)
			throws ESBException, JMSException {
		String mensajeAdvertencia = null;
		ProyectoService proyectoService = serviceFactory.getProyectoService();

		List<Integer> idProyectosEnPriorizacion = ListUtils
				.emptyIfNull(proyectoService.getTodosLosIdsProyectosEnPriorizacion(userMail));
		List<Integer> idProyectosEnExcel = ListUtils.emptyIfNull(solapa.getTodosLosIdProyectos());

		if (idProyectosEnPriorizacion != null && idProyectosEnExcel != null) {
			idProyectosEnPriorizacion.removeAll(idProyectosEnExcel);
			if (!idProyectosEnPriorizacion.isEmpty()) {
				mensajeAdvertencia = "Faltan proyectos en el archivo que estás queriendo importar. Una vez finalizada la importación en curso, "
						+ "será necesario descargar un nuevo Excel para priorizarlos.";

			}
		} else {
			mensajeAdvertencia = "Existen inconsistencias entre los proyectos del archivo con los de la base.";
		}
		return mensajeAdvertencia;
	}

	/**
	 * Val 3
	 *
	 * @param solapa
	 * @throws JMSException
	 * @throws ESBException
	 */
	private String todoLosProyectosEnEstadoAprobado(ProyectoPriorizadoSolapa solapa) throws ESBException, JMSException {
		String mensajeAdvertencia = null;
		List<String> estadosAprobacionEnExcel = solapa.getTodosLosEstados();
		List<String> estadosCorrectos = Arrays.asList(EstadoProyecto.PREAPROBADO.getName(),
				EstadoProyecto.APROBADO.getName(), EstadoProyecto.DEMORADO.getName(),
				EstadoProyecto.RECHAZADO.getName());

		List<String> estadosArchivosFiltrados = estadosAprobacionEnExcel.parallelStream()
				.filter(new Predicate<String>() {

					@Override
					public boolean test(String t) {
						return (StringUtils.isNotEmpty(t) && !estadosCorrectos.contains(t.replace("-", " ")))
								|| t.isEmpty();
					}
				}).collect(Collectors.toList());

		if (!estadosArchivosFiltrados.isEmpty()) {
			mensajeAdvertencia = "El archivo contiene proyectos sin estado de aprobación y, finalizada la importación en curso, "
					+ "será necesario descargar un nuevo Excel para priorizarlos. ";
		}
		return mensajeAdvertencia;
	}

	/**
	 * Val 4
	 *
	 * @param fila
	 */
	private String proyectosEnEstadoRechazadoOAplazadoConPresuCero(ProyectoPriorizadoFila fila) {
		String mensajeError = null;
		List<String> estadosAVerificar = Arrays.asList(EstadoProyecto.RECHAZADO.getName(),
				EstadoProyecto.DEMORADO.getName());

		if (estadosAVerificar.contains(fila.getEstadoAprobacion()) && fila.getPresuAprobadoTotal() > 0) {
			mensajeError = "El estado de aprobación no es consistente con el presupuesto aprobado total";
		}
		return mensajeError;
	}

	/**
	 * Val 8
	 *
	 * @param fila
	 */
	private String presupuestoAprobadoMayorQuePresuTotal(ProyectoPriorizadoFila fila) {
		String mensajeError = null;

		if (fila.getPresuAprobadoTotal() > fila.getPresuTotal()) {
			mensajeError = "No es posible aprobar más presupuesto que el que fue solicitado";
		}
		return mensajeError;
	}

	/**
	 * Val 9 verifica que todos los proyectos del archivo se encuentren en el
	 * sistema en estado “En priorización”
	 *
	 * @param solapa
	 * @throws JMSException
	 * @throws ESBException
	 */
	private String proyectosDelArchivoExistenEnLaBase(ProyectoPriorizadoSolapa solapa, String userMail)
			throws ESBException, JMSException {
		String mensajeError = null;
		ProyectoService proyectoService = serviceFactory.getProyectoService();

		List<Integer> idProyectosEnPriorizacion = ListUtils
				.emptyIfNull(proyectoService.getTodosLosIdsProyectosEnPriorizacion(userMail));
		List<Integer> idProyectosEnExcel = ListUtils.emptyIfNull(solapa.getTodosLosIdProyectos());

		if (idProyectosEnExcel.isEmpty()) {
			mensajeError = "No hay proyectos en el archivo";
		}

		// sacamos los de la base en el excel, si quedan es porque hay proyectos
		// en el excel que en la base no estan.
		idProyectosEnExcel.removeAll(idProyectosEnPriorizacion);
		if (!idProyectosEnExcel.isEmpty()) {
			mensajeError = "Hay proyectos en el archivo que no se encuentran en el proceso de priorización. Descargá nuevamente el Excel de Priorización";
		}
		return mensajeError;
	}
}