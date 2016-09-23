package ar.gob.buenosaires.importador.proyecto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.function.Predicate;

import javax.jms.JMSException;

import org.springframework.util.StringUtils;

import ar.gob.buenosaires.domain.Comuna;
import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.domain.PresupuestoPorAnio;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.EjeDeGobiernoService;
import ar.gob.buenosaires.service.IServiceFactory;
import ar.gob.buenosaires.service.ObjetivoJurisdiccionalService;
import ar.gob.buenosaires.service.ObjetivoOperativoService;
import ar.gob.buenosaires.service.ProyectoService;

/**
 * Builder para la contruir las relaciones en base a la informacion de una fila
 * del Excel de importacion de proyectos.
 */
public class ImportadorProyectoBuilder {

	private Jurisdiccion jurisdiccion;

	private Proyecto proyectoTransient;

	private Proyecto proyecto;

	private ObjetivoJurisdiccional objetivoJurisdiccional;

	private ObjetivoOperativo objetivoOperativo;

	private List<Comuna> comunas = new ArrayList<>();

	private List<EjeDeGobierno> ejeDeGobiernos = new ArrayList<>();

	// fatory de servicios
	private IServiceFactory serviceFactory;

	private List<PoblacionMeta> poblacionMetas = new ArrayList<>();

	private List<PresupuestoPorAnio> presupuestoPorAnio = new ArrayList<>();

	private String codigoObjJuri;

	private String nombreObjJuri;

	private String codigoObjOper;

	private String nombreObjOper;

	public ImportadorProyectoBuilder(IServiceFactory service) {
		serviceFactory = service;
	}

	public ImportadorProyectoBuilder cargarProyecto(String nombreProyecto) {
		proyectoTransient = new Proyecto();
		proyectoTransient.setNombre(nombreProyecto);
		proyectoTransient.setEstado(EstadoProyecto.COMPLETO.getName());

		return this;
	}

	public ImportadorProyectoBuilder cargarObjetivoJurisdiccional(String nombreObjetivoJurisdiccional,
			String codigoObjetivoJurisdiccional) {
		codigoObjJuri = codigoObjetivoJurisdiccional;
		nombreObjJuri = nombreObjetivoJurisdiccional;

		return this;

	}

	public ImportadorProyectoBuilder cargarObjetivoOperativo(String nombreObjetivoOperativo,
			String codigoObjetivoOperativo) {
		codigoObjOper = codigoObjetivoOperativo;
		nombreObjOper = nombreObjetivoOperativo;

		return this;
	}

	public ImportadorProyectoBuilder cargarPoblacionMeta(String segmento) {
		try {
			List<PoblacionMeta> poblacionMetas = serviceFactory.getPoblacionMetaService().getPoblacionesMeta();
			for (Iterator<PoblacionMeta> iterator = poblacionMetas.iterator(); iterator.hasNext();) {
				PoblacionMeta poblacionMeta = iterator.next();
				if (poblacionMeta.getNombre().equalsIgnoreCase(segmento)) {
					this.poblacionMetas.add(poblacionMeta);
				}
			}
		} catch (ESBException | JMSException e) {
			e.printStackTrace();
		}

		return this;
	}

	// Metodos para la carga de proyecto
	public ImportadorProyectoBuilder cargarProyectoDescripcion(String descripcion) {
		proyectoTransient.setDescripcion(descripcion);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoLider(String nombreLider) {
		proyectoTransient.setLiderProyecto(nombreLider);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoPoblacionAfectada(Double poblacionAfectada) {
		if (!StringUtils.isEmpty(poblacionAfectada.toString()) && poblacionAfectada != 0) {
			proyectoTransient.setPoblacionAfectada(poblacionAfectada.intValue());
		}
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoArea(String area) {
		try {
			proyectoTransient.setArea(serviceFactory.getAreaService().getAreasByNameAndIdJurisdiccion(area,
					jurisdiccion.getIdJurisdiccion()));

		} catch (ESBException | JMSException e) {
			e.printStackTrace();
		}
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoUnidadMeta(String unidadMeta) {
		proyectoTransient.setUnidadMeta(unidadMeta);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoMeta(Double meta) {
		if (!StringUtils.isEmpty(meta.toString()) && meta != 0) {
			proyectoTransient.setMeta(new BigDecimal(meta).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		}
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoTipoUbucacion(String tipoUbicacion) {
		proyectoTransient.setTipoUbicacionGeografica(tipoUbicacion);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoDireccion(String direccion) {
		proyectoTransient.setDireccion(direccion);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoFechaInicio(Date fechaInicio) {
		proyectoTransient.setFechaInicio(fechaInicio);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoFechaFin(Date fechaFin) {
		proyectoTransient.setFechaFin(fechaFin);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoTipoProyecto(String tipoProyecto) {
		proyectoTransient.setTipoProyecto(tipoProyecto);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoImplicaCambioLegislativo(String implicaCambioLegislativo) {
		if (!StringUtils.isEmpty(implicaCambioLegislativo)) {
			proyectoTransient.setCambioLegislativo(getBooleanFromString(implicaCambioLegislativo));
		} else {
			proyectoTransient.setCambioLegislativo(null);
		}
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoPrioridadJurisdiccional(String prioridadJurisdiccional) {
		proyectoTransient.setPrioridadJurisdiccional(prioridadJurisdiccional);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoCorresponsable(String corresponsable) {
		proyectoTransient.setOrganismosCorresponsables(corresponsable);
		return this;
	}

	public ImportadorProyectoBuilder cargarPresupuestoPorAnio(Double anio, Double importe, Double importeOtrasFuentes) {
		if (!StringUtils.isEmpty(anio.toString()) && anio != 0) {
			PresupuestoPorAnio presu = new PresupuestoPorAnio();
			presu.setAnio(anio.intValue());
			presu.setPresupuesto(importe);
			presu.setOtrasFuentes(importeOtrasFuentes);
			presupuestoPorAnio.add(presu);
		}
		return this;
	}

	public ImportadorProyectoBuilder cargarComuna(String nombreComuna) {

		try {
			List<Comuna> comunas = serviceFactory.getComunaService().getComunas();
			for (Iterator<Comuna> iterator = comunas.iterator(); iterator.hasNext();) {
				Comuna comuna = iterator.next();
				if (comuna.getNombre().equalsIgnoreCase(nombreComuna)) {
					this.comunas.add(comuna);
				}
			}
		} catch (ESBException | JMSException e) {
			e.printStackTrace();
		}

		return this;
	}

	public ImportadorProyectoBuilder cargarEjeDeGobierno(String nombreEjeDeGobierno) {
		if (!StringUtils.isEmpty(nombreEjeDeGobierno)) {
			EjeDeGobierno unEjeDeGobierno = null;
			EjeDeGobiernoService ejeDeGobiernoService = serviceFactory.getEjeDeGobiernoService();

			try {
				unEjeDeGobierno = ejeDeGobiernoService.getEjeDeGobiernoByName(nombreEjeDeGobierno);
				if (unEjeDeGobierno != null) {
					ejeDeGobiernos.add(unEjeDeGobierno);
				}
			} catch (ESBException | JMSException e) {
				e.printStackTrace();
			}
		}
		return this;
	}

	/**
	 * Construye el grafo desde la jurisdiccion y relaciona todos los objetos.
	 *
	 * @throws JMSException
	 * @throws ESBException
	 */
	public Proyecto build() throws ESBException, JMSException {
		buildObjetivoJurisdiccional();
		buildObjetivoOperativo();
		crearYCargarProyecto();

		return serviceFactory.getProyectoService().updateProyecto(proyecto);
	}

	private boolean getBooleanFromString(String unString) {
		return "si".equalsIgnoreCase(unString);
	}

	private void crearYCargarProyecto() {
		ProyectoService proyectoService = serviceFactory.getProyectoService();

		try {
			proyecto = proyectoService.getProyectoPorNombreIdJurisdiccion(proyectoTransient.getNombre(),
					getJurisdiccion().getIdJurisdiccion());
		} catch (ESBException | JMSException e) {
			e.printStackTrace();
		}

		if (proyecto == null) {

			try {
				proyecto = proyectoTransient;
				setearColleccionesAlProyecto(proyecto);
				proyecto = proyectoService.createProyecto(proyecto);
			} catch (ESBException | JMSException e) {

				e.printStackTrace();
			}
		} else {
			setearColleccionesAlProyecto(proyecto);
			actualizarEstadoAlProyecto();
		}
	}

	private void setearColleccionesAlProyecto(Proyecto unProyecto) {
		unProyecto.getComunas().clear();
		unProyecto.getEjesDeGobierno().clear();
		unProyecto.getPresupuestosPorAnio().clear();
		unProyecto.getPoblacionesMeta().clear();

		for (Iterator<Comuna> iterator = comunas.iterator(); iterator.hasNext();) {
			Comuna comuna = iterator.next();
			if (!unProyecto.getComunas().stream().anyMatch(new Predicate<Comuna>() {

				@Override
				public boolean test(Comuna value) {
					return comuna.getIdComuna() == value.getIdComuna();
				}
			})) {
				unProyecto.getComunas().add(comuna);
			}
		}

		for (Iterator<EjeDeGobierno> iterator = ejeDeGobiernos.iterator(); iterator.hasNext();) {
			EjeDeGobierno ejeDeGobierno = iterator.next();
			if (!unProyecto.getEjesDeGobierno().stream().anyMatch(new Predicate<EjeDeGobierno>() {

				@Override
				public boolean test(EjeDeGobierno t) {
					return ejeDeGobierno.getIdEjeDeGobierno() == t.getIdEjeDeGobierno();
				}
			})) {
				unProyecto.getEjesDeGobierno().add(ejeDeGobierno);
			}
		}

		for (Iterator<PresupuestoPorAnio> iterator = presupuestoPorAnio.iterator(); iterator.hasNext();) {
			PresupuestoPorAnio presupuestoPorAnio = iterator.next();
			if (!unProyecto.getPresupuestosPorAnio().stream().anyMatch(new Predicate<PresupuestoPorAnio>() {

				@Override
				public boolean test(PresupuestoPorAnio t) {
					boolean noAgregar = true;
					if (t.getAnio() == presupuestoPorAnio.getAnio()) {
						t.setPresupuesto(presupuestoPorAnio.getPresupuesto());
						t.setOtrasFuentes(presupuestoPorAnio.getOtrasFuentes());
					} else {
						noAgregar = false;
					}
					return noAgregar;
				}
			})) {
				unProyecto.getPresupuestosPorAnio().add(presupuestoPorAnio);
				presupuestoPorAnio.setProyecto(unProyecto);
			}
		}

		for (Iterator<PoblacionMeta> iterator = poblacionMetas.iterator(); iterator.hasNext();) {
			PoblacionMeta poblacionMeta = iterator.next();
			if (!unProyecto.getPoblacionesMeta().stream().anyMatch(new Predicate<PoblacionMeta>() {

				@Override
				public boolean test(PoblacionMeta t) {
					return poblacionMeta.getIdPoblacionMeta() == t.getIdPoblacionMeta();
				}
			})) {
				unProyecto.getPoblacionesMeta().add(poblacionMeta);
			}
		}

		// Metemos los Ids a manopla, ya que la libreria que convierte los
		// objetos a XML, rompe las relaciones.
		unProyecto.setObjetivoOperativo(objetivoOperativo);
		unProyecto.setIdJurisdiccion2(jurisdiccion.getIdJurisdiccion());
		unProyecto.setIdObjetivoJurisdiccional2(objetivoJurisdiccional.getIdObjetivoJurisdiccional());
		unProyecto.setIdObjetivoOperativo2(objetivoOperativo.getIdObjetivoOperativo());

	}

	private void actualizarEstadoAlProyecto() {
		proyecto.setNombre(proyectoTransient.getNombre());
		proyecto.setArea(proyectoTransient.getArea());
		proyecto.setCambioLegislativo(proyectoTransient.getCambioLegislativo());
		proyecto.setCodigo(proyectoTransient.getCodigo());
		proyecto.setDescripcion(proyectoTransient.getDescripcion());
		proyecto.setDireccion(proyectoTransient.getDireccion());
		proyecto.setEstado(proyectoTransient.getEstado());
		proyecto.setFechaInicio(proyectoTransient.getFechaInicio());
		proyecto.setFechaFin(proyectoTransient.getFechaFin());
		proyecto.setLiderProyecto(proyectoTransient.getLiderProyecto());
		proyecto.setMeta(proyectoTransient.getMeta());
		proyecto.setObjetivoOperativo(proyectoTransient.getObjetivoOperativo());
		proyecto.setOrganismosCorresponsables(proyectoTransient.getOrganismosCorresponsables());
		proyecto.setPoblacionAfectada(proyectoTransient.getPoblacionAfectada());
		proyecto.setPoblacionesMeta(proyectoTransient.getPoblacionesMeta());
		proyecto.setPrioridadJurisdiccional(proyectoTransient.getPrioridadJurisdiccional());
		proyecto.setTipoProyecto(proyectoTransient.getTipoProyecto());
		proyecto.setTipoUbicacionGeografica(proyectoTransient.getTipoUbicacionGeografica());
		proyecto.setUnidadMeta(proyectoTransient.getUnidadMeta());

	}

	private String getProximoCodigoObjJurisdiccional() {
		String codigoJurisdiccion = getJurisdiccion().getCodigo();
		Integer codigoObjetivoJurisdiccional = 0;

		if (!getJurisdiccion().getObjetivosJurisdiccionales().isEmpty()) {
			codigoObjetivoJurisdiccional = getJurisdiccion().getObjetivosJurisdiccionales().parallelStream()
					.filter(o -> o != null).map(oj -> Integer.parseInt(oj.getCodigo().split("\\.")[1]))
					.max(Integer::compare).get();
		}
		return codigoJurisdiccion.concat(".").concat(String.valueOf(codigoObjetivoJurisdiccional + 1));
	}

	private String getProximoCodigoObjOperativo(ObjetivoJurisdiccional objetivoJurisdiccional) {
		Integer codigoObjetivoOperativo = 0;

		if (!objetivoJurisdiccional.getObjetivosOperativos().isEmpty()) {
			codigoObjetivoOperativo = objetivoJurisdiccional.getObjetivosOperativos().parallelStream()
					.filter(o -> o != null).map(oj -> Integer.parseInt(oj.getCodigo().split("\\.")[2]))
					.max(Integer::compare).get();
		}
		return objetivoJurisdiccional.getCodigo().concat(".").concat(String.valueOf(codigoObjetivoOperativo + 1));
	}

	public Jurisdiccion getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(Jurisdiccion jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	private void buildObjetivoJurisdiccional() throws ESBException, JMSException {
		ObjetivoJurisdiccionalService objetivoJurisdiccionalService = serviceFactory.getObjetivoJurisdiccionalService();
		if (codigoObjJuri != null && !codigoObjJuri.isEmpty()) {
			objetivoJurisdiccional = objetivoJurisdiccionalService.getObjetivoJurisdiccionalPorCodigo(codigoObjJuri);
		}
		if (objetivoJurisdiccional == null && nombreObjJuri != null && !nombreObjJuri.isEmpty()) {
			objetivoJurisdiccional = objetivoJurisdiccionalService.getObjetivoJurisdiccionalPorNombre(nombreObjJuri);
		}
		if (objetivoJurisdiccional == null) {
			objetivoJurisdiccional = new ObjetivoJurisdiccional();
			objetivoJurisdiccional.setJurisdiccion(jurisdiccion);
			objetivoJurisdiccional.setNombre(nombreObjJuri);
			objetivoJurisdiccional.getObjetivosOperativos().add(objetivoOperativo);
			objetivoJurisdiccional.setIdJurisdiccionAux(jurisdiccion.getIdJurisdiccion());
			objetivoJurisdiccional = objetivoJurisdiccionalService.createObjetivoJurisdiccional(objetivoJurisdiccional);
			getJurisdiccion().getObjetivosJurisdiccionales().add(objetivoJurisdiccional);
			objetivoJurisdiccional.setCodigo(getProximoCodigoObjJurisdiccional());

		}
	}

	private void buildObjetivoOperativo() throws ESBException, JMSException {
		ObjetivoOperativoService objetivoOperativoService = serviceFactory.getObjetivoOperativoService();
		if (codigoObjOper != null && !codigoObjOper.isEmpty()) {
			objetivoOperativo = objetivoOperativoService.getObjetivoOperativoPorCodigo(codigoObjOper);
		}
		if (objetivoOperativo == null && nombreObjOper != null && !nombreObjOper.isEmpty()) {
			objetivoOperativo = objetivoOperativoService.getObjetivoOperativoPorNombre(nombreObjOper);
		}
		if (objetivoOperativo == null) {
			objetivoOperativo = new ObjetivoOperativo();
			objetivoOperativo.setNombre(nombreObjOper);
			objetivoOperativo.setIdObjetivoJurisdiccionalAux(objetivoJurisdiccional.getIdObjetivoJurisdiccional());
			objetivoOperativo = objetivoOperativoService.createObjetivoOperativo(objetivoOperativo);
			objetivoJurisdiccional.getObjetivosOperativos().add(objetivoOperativo);
			objetivoOperativo.setCodigo(getProximoCodigoObjOperativo(objetivoJurisdiccional));
		}

	}

}
