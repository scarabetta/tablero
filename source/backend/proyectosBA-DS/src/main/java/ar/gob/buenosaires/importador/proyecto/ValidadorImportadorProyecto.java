package ar.gob.buenosaires.importador.proyecto;

import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import javax.jms.JMSException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.importador.MensajeError;
import ar.gob.buenosaires.service.JurisdiccionService;

@Component
@Scope("prototype")
public class ValidadorImportadorProyecto {

	private List<MensajeError> problemasSolapa;

	private Map<Integer, List<MensajeError>> problemasFilas = new HashMap<Integer, List<MensajeError>>();

	private Jurisdiccion jurisdiccion;

	@Autowired
	private JurisdiccionService jurisdiccionService;

	@Autowired
	Environment env;

	public boolean validarFila(Row unaFila) {

		int numeroCeldaFechaInicio = Integer.parseInt(env.getProperty("proyecto.col.fecha.inicio.numero"));
		int numeroCeldaFechaFin = Integer.parseInt(env.getProperty("proyecto.col.fecha.fin.numero"));

		this.validarCampoVacio(unaFila, unaFila.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue(), "Proyecto");
		this.validarCampoVacio(unaFila, unaFila.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
				"Objetivo estratégico");
		this.validarCampoVacio(unaFila, unaFila.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
				"Objetivo operativo");
		this.validarCampoVacio(unaFila,
				unaFila.getCell(numeroCeldaFechaInicio, Row.CREATE_NULL_AS_BLANK).getDateCellValue(), "Fecha inicio");

		this.validarFechaMismoOMayorAnio(unaFila.getRowNum(),
				unaFila.getCell(numeroCeldaFechaInicio, Row.CREATE_NULL_AS_BLANK).getDateCellValue(), "Fecha inicio");
		this.validarFechaMismoOMayorAnio(unaFila.getRowNum(),
				unaFila.getCell(numeroCeldaFechaFin, Row.CREATE_NULL_AS_BLANK).getDateCellValue(), "Fecha fin");
		return !this.getProblemasFilas().containsKey(unaFila.getRowNum());
	}

	public List<MensajeError> validarSolapa(SolapaProyecto solpaAValidar) {
		this.problemasSolapa = new ArrayList<MensajeError>();
		this.validarEsTemplate(solpaAValidar);
		this.validarJurisdiccion(
				solpaAValidar.getSolapa().getRow(1).getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
				solpaAValidar.getSolapa().getRow(0).getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue());
		this.validarPermisoDelUsuario();
		this.existeMuchasVecesElMismoProyecto(solpaAValidar);

		return getProblemasSolapa();
	}

	private void validarCampoVacio(Row fila, Object contenidoAValidar, String campoAValidar) {
		if (contenidoAValidar == null || (contenidoAValidar != null && contenidoAValidar.toString().isEmpty())) {
			agregarMensajeParaFila(fila.getRowNum(), "El campo " + campoAValidar + " no puede estar vacio.",
					MensajeError.TIPO_ERROR);
		}
	}

	private void validarPermisoDelUsuario() {
		if (this.jurisdiccion != null) {
			String mensajeError = "No tenés permisos para importar proyectos de " + this.jurisdiccion.getNombre();
			// TODO Validar los permisos del usuario para la jurisdiccion
			// ingresada.
		}
	}

	private void validarEsTemplate(SolapaProyecto solpaAValidar) {

		String nombreAValidar;
		String nombreValido = "";
		boolean esTemplateValido = true;

		for (Iterator<Cell> celdas = solpaAValidar.getCeldasDeFila(solpaAValidar.getNumeroFilaNombreColumnas()); celdas.hasNext()
				&& esTemplateValido && (nombreValido != null);) {

			Cell unaCelda = (Cell) celdas.next();
			nombreValido = env.getProperty("proyecto.col." + solpaAValidar.getNumeroFilaNombreColumnas() + "." + unaCelda.getColumnIndex() + ".nombre");
			nombreAValidar = unaCelda.getStringCellValue();

			if (nombreValido != null && !nombreValido.equalsIgnoreCase(nombreAValidar)) {
				esTemplateValido = false;
				this.problemasSolapa.add(new MensajeError(MensajeError.TIPO_ERROR,
						"El template ingresado no es válido. No se encontró la columna " + nombreValido + "."));
			}
		}

	}

	public void existeMuchasVecesElMismoProyecto(SolapaProyecto solpaAValidar) {

		List<String> nombresProyecto = new ArrayList<String>();
		String nombreProyecto;

		for (int i = solpaAValidar.getNumeroFilaInicioImportacion(); i < solpaAValidar.getNumeroUltimaFila(); i++) {
			nombreProyecto = solpaAValidar.getFilaNumero(i).getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue();
			nombresProyecto.add(nombreProyecto);			
		}
		
		nombresProyecto.stream().collect(groupingBy(identity(), counting())).entrySet().stream()
				.filter(t -> t.getValue() > 1).map(Map.Entry::getKey).collect(toList()).forEach(new Consumer<String>() {

					@Override
					public void accept(String nombreProtectoRepetido) {

						ValidadorImportadorProyecto.this.problemasSolapa.add(new MensajeError(MensajeError.TIPO_ERROR,
								"Hay más de un proyecto con el nombre " + nombreProtectoRepetido
										+ ". Verificá que el nombre de los proyectos sea único y volvé a intentar."));

					}

				});

	}

	private void validarJurisdiccion(String codigoJurisdiccion, String nombreJurisdiccion) {
		this.jurisdiccion = null;

		try {
			this.jurisdiccion = this.jurisdiccionService.getJurisdiccionesByName(nombreJurisdiccion);
		} catch (ESBException | JMSException e) {
		}

		if (getJurisdiccion() == null) {
			getProblemasSolapa().add(
					new MensajeError(MensajeError.TIPO_ERROR, "La jurisdicci\u00f3n ingresada en el Excel no es válida."));
		}
	}

	private void validarFechaMismoOMayorAnio(int numeroFila, Date fechaAValidar, String tipoFecha) {

		if (fechaAValidar != null) {
			Calendar dateAsCalendar = Calendar.getInstance();
			dateAsCalendar.setTime(fechaAValidar);

			if (dateAsCalendar.get(Calendar.YEAR) < Calendar.getInstance().get(Calendar.YEAR)) {
				agregarMensajeParaFila(numeroFila, tipoFecha + " no es válido.", MensajeError.TIPO_ERROR);
			}
		}
	}

	private void agregarMensajeParaFila(Integer numeroFila, String msg, String tipoMsg) {

		if (this.getProblemasFilas().containsKey(numeroFila)) {
			this.getProblemasFilas().get(numeroFila).add(new MensajeError(tipoMsg, msg));

		} else {
			List<MensajeError> msgList = new ArrayList<MensajeError>();
			msgList.add(new MensajeError(tipoMsg, msg));
			this.getProblemasFilas().put(numeroFila, msgList);
		}
	}

	public List<MensajeError> getProblemasSolapa() {
		return problemasSolapa;
	}

	public Map<Integer, List<MensajeError>> getProblemasFilas() {
		return problemasFilas;
	}

	public Jurisdiccion getJurisdiccion() {
		return jurisdiccion;
	}

}
