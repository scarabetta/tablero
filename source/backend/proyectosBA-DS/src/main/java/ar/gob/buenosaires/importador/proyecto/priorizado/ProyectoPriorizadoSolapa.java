package ar.gob.buenosaires.importador.proyecto.priorizado;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.collections4.ListUtils;
import org.apache.commons.collections4.Predicate;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.core.env.Environment;

public class ProyectoPriorizadoSolapa {

	public static final String PREFIJO_TEMA_TRANSVERSAL = "TT:";

	private Sheet sheet;

	Environment env;

	int numeroFilaHeader;

	private ProyectoPriorizadoFila proyectoPriorizadoFila;

	public ProyectoPriorizadoSolapa(Sheet sheet, Environment env) {
		this.sheet = sheet;
		this.env = env;
		numeroFilaHeader = Integer.valueOf(this.env.getProperty("proyecto.priorizado.num.fila.headers"));
		proyectoPriorizadoFila = new ProyectoPriorizadoFila(sheet.getRow(numeroFilaHeader), env, this);
	}

	public ProyectoPriorizadoFila getFilaHeader() {
		return proyectoPriorizadoFila;
	}

	public List<ProyectoPriorizadoFila> getFilas() {
		List<ProyectoPriorizadoFila> filas = new ArrayList<>();
		for (Iterator<Row> rows = getSheet().rowIterator(); rows.hasNext();) {
			ProyectoPriorizadoFila fila = new ProyectoPriorizadoFila(rows.next(), env, this);
			if (!fila.getIdProyecto().isEmpty() && NumberUtils.isNumber(fila.getIdProyecto())) {
				filas.add(fila);
			}
		}
		return filas;
	}

	/**
	 * @return the sheet
	 */
	public Sheet getSheet() {
		return sheet;
	}

	public List<Integer> getTodosLosIdProyectos() {
		return getFilas().parallelStream().map(s -> Integer.valueOf(Double.valueOf(s.getIdProyecto()).intValue()))
				.collect(Collectors.toList());
	}

	public List<String> getTodosLosEstados() {
		return getFilas().parallelStream().map(s -> s.getEstadoAprobacion()).collect(Collectors.toList());
	}

	public List<String> getNombresTemaTransversalFilaHeader() {
		List<ProyectoPriorizadoCelda> celdaTemasTransversales = getCeldasTemasTransversales();

		return celdaTemasTransversales.parallelStream()
				.map(s -> s.getSringValueParaCualquierTipo().replaceAll(PREFIJO_TEMA_TRANSVERSAL, ""))
				.collect(Collectors.toList());
	}

	public List<ProyectoPriorizadoCelda> getCeldasTemasTransversales() {
		List<ProyectoPriorizadoCelda> celdaTemasTransversales = ListUtils.select(this.getFilaHeader().getCeldas(),
				new Predicate<ProyectoPriorizadoCelda>() {

					@Override
					public boolean evaluate(ProyectoPriorizadoCelda celda) {
						return celda.getSringValueParaCualquierTipo().contains(PREFIJO_TEMA_TRANSVERSAL);
					}
				});
		return celdaTemasTransversales;
	}
}
