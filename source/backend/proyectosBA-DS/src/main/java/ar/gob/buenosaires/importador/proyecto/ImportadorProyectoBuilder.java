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

	private List<Comuna> comunas = new ArrayList<Comuna>();

	private List<EjeDeGobierno> ejeDeGobiernos = new ArrayList<EjeDeGobierno>();

	// fatory de servicios
	private IServiceFactory serviceFactory;

	private List<PoblacionMeta> poblacionMetas = new ArrayList<PoblacionMeta>();

	private List<PresupuestoPorAnio> presupuestoPorAnio = new ArrayList<PresupuestoPorAnio>();

	private String codigoObjJuri;

	private String nombreObjJuri;

	private String codigoObjOper;

	private String nombreObjOper;

	public ImportadorProyectoBuilder(IServiceFactory service) {
		this.serviceFactory = service;
	}

	public ImportadorProyectoBuilder cargarProyecto(String nombreProyecto) {
		this.proyectoTransient = new Proyecto();
		this.proyectoTransient.setNombre(nombreProyecto);
		this.proyectoTransient.setEstado("Borrador");

		return this;
	}

	public ImportadorProyectoBuilder cargarObjetivoJurisdiccional(String nombreObjetivoJurisdiccional,
			String codigoObjetivoJurisdiccional) {
		this.codigoObjJuri = codigoObjetivoJurisdiccional;
		this.nombreObjJuri = nombreObjetivoJurisdiccional;

		return this;

	}

	public ImportadorProyectoBuilder cargarObjetivoOperativo(String nombreObjetivoOperativo,
			String codigoObjetivoOperativo) {
		this.codigoObjOper = codigoObjetivoOperativo;
		this.nombreObjOper = nombreObjetivoOperativo;

		return this;
	}

	public ImportadorProyectoBuilder cargarPoblacionMeta(String segmento) {
		try {
			List<PoblacionMeta> poblacionMetas = this.serviceFactory.getPoblacionMetaService().getPoblacionesMeta();
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
		this.proyectoTransient.setDescripcion(descripcion);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoLider(String nombreLider) {
		this.proyectoTransient.setLiderProyecto(nombreLider);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoPoblacionAfectada(Double poblacionAfectada) {
		this.proyectoTransient.setPoblacionAfectada(poblacionAfectada.intValue());
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoArea(String area) {
		this.proyectoTransient.setArea(area);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoUnidadMeta(String unidadMeta) {
		this.proyectoTransient.setUnidadMeta(unidadMeta);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoMeta(Double meta) {
		this.proyectoTransient.setMeta(new BigDecimal(meta).setScale(2, BigDecimal.ROUND_HALF_EVEN));
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoTipoUbucacion(String tipoUbicacion) {
		this.proyectoTransient.setTipoUbicacionGeografica(tipoUbicacion);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoDireccion(String direccion) {
		this.proyectoTransient.setDireccion(direccion);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoFechaInicio(Date fechaInicio) {
		this.proyectoTransient.setFechaInicio(fechaInicio);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoFechaFin(Date fechaFin) {
		this.proyectoTransient.setFechaFin(fechaFin);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoTipoProyecto(String tipoProyecto) {
		this.proyectoTransient.setTipoProyecto(tipoProyecto);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoImplicaCambioLegislativo(String implicaCambioLegislativo) {
		if (!StringUtils.isEmpty(implicaCambioLegislativo)) {
			this.proyectoTransient.setCambioLegislativo(getBooleanFromString(implicaCambioLegislativo));
		} else {
			this.proyectoTransient.setCambioLegislativo(null);
		}
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoPrioridadJurisdiccional(String prioridadJurisdiccional) {
		this.proyectoTransient.setPrioridadJurisdiccional(prioridadJurisdiccional);
		return this;
	}

	public ImportadorProyectoBuilder cargarProyectoCorresponsable(String corresponsable) {
		this.proyectoTransient.setOrganismosCorresponsables(corresponsable);
		return this;
	}

	public ImportadorProyectoBuilder cargarPresupuestoPorAnio(Double anio, Double importe) {
		if (!StringUtils.isEmpty(anio)) {
			PresupuestoPorAnio presu = new PresupuestoPorAnio();
			presu.setAnio(anio.intValue());
			presu.setPresupuesto(importe);
			this.presupuestoPorAnio.add(presu);
		}
		return this;
	}

	public ImportadorProyectoBuilder cargarComuna(String nombreComuna) {

		try {
			List<Comuna> comunas = this.serviceFactory.getComunaService().getComunas();
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
			EjeDeGobiernoService ejeDeGobiernoService = this.serviceFactory.getEjeDeGobiernoService();

			try {
				unEjeDeGobierno = ejeDeGobiernoService.getEjeDeGobiernoByName(nombreEjeDeGobierno);
				if (unEjeDeGobierno != null) {
					this.ejeDeGobiernos.add(unEjeDeGobierno);
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
	public Jurisdiccion build() throws ESBException, JMSException {
		this.buildObjetivoJurisdiccional();
		this.buildObjetivoOperativo();
		this.crearYCargarProyecto();
		this.serviceFactory.getProyectoService().updateProyecto(this.proyecto);

		return this.getJurisdiccion();
	}

	private boolean getBooleanFromString(String unString) {
		return "si".equalsIgnoreCase(unString);
	}

	private void crearYCargarProyecto() {
		ProyectoService proyectoService = this.serviceFactory.getProyectoService();

		try {
			this.proyecto = proyectoService.getProyectoByName(this.proyectoTransient.getNombre());
		} catch (ESBException | JMSException e) {
			e.printStackTrace();
		}

		if (this.proyecto == null) {

			try {
				this.proyecto = this.proyectoTransient;
				this.setearColleccionesAlProyecto(this.proyecto);
				this.proyecto = proyectoService.createProyecto(this.proyecto);
			} catch (ESBException | JMSException e) {

				e.printStackTrace();
			}
		} else {
			this.setearColleccionesAlProyecto(this.proyecto);
			this.actualizarEstadoAlProyecto();
		}
	}

	private void setearColleccionesAlProyecto(Proyecto unProyecto) {

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

		for (Iterator<PresupuestoPorAnio> iterator = this.presupuestoPorAnio.iterator(); iterator.hasNext();) {
			PresupuestoPorAnio presupuestoPorAnio = iterator.next();
			if (!unProyecto.getPresupuestosPorAnio().stream().anyMatch(new Predicate<PresupuestoPorAnio>() {

				@Override
				public boolean test(PresupuestoPorAnio t) {
					return presupuestoPorAnio.getIdPresupuestoPorAnio() == t.getIdPresupuestoPorAnio();
				}
			})) {
				unProyecto.getPresupuestosPorAnio().add(presupuestoPorAnio);
			}
		}

		for (Iterator<PoblacionMeta> iterator = this.poblacionMetas.iterator(); iterator.hasNext();) {
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
		unProyecto.setObjetivoOperativo(this.objetivoOperativo);
		unProyecto.setIdJurisdiccion2(this.jurisdiccion.getIdJurisdiccion());
		unProyecto.setIdObjetivoJurisdiccional2(this.objetivoJurisdiccional.getIdObjetivoJurisdiccional());
		unProyecto.setIdObjetivoOperativo2(this.objetivoOperativo.getIdObjetivoOperativo());

	}

	private void actualizarEstadoAlProyecto() {
		this.proyecto.setNombre(this.proyectoTransient.getNombre());
		this.proyecto.setArea(this.proyectoTransient.getArea());
		this.proyecto.setCambioLegislativo(this.proyectoTransient.getCambioLegislativo());
		this.proyecto.setCodigo(this.proyectoTransient.getCodigo());
		this.proyecto.setDescripcion(this.proyectoTransient.getDescripcion());
		this.proyecto.setDireccion(this.proyectoTransient.getDireccion());
		this.proyecto.setEstado(this.proyectoTransient.getEstado());
		this.proyecto.setFechaInicio(this.proyectoTransient.getFechaInicio());
		this.proyecto.setFechaFin(this.proyectoTransient.getFechaFin());
		this.proyecto.setLiderProyecto(this.proyectoTransient.getLiderProyecto());
		this.proyecto.setMeta(this.proyectoTransient.getMeta());
		this.proyecto.setObjetivoOperativo(this.proyectoTransient.getObjetivoOperativo());
		this.proyecto.setOrganismosCorresponsables(this.proyectoTransient.getOrganismosCorresponsables());
		this.proyecto.setPoblacionAfectada(this.proyectoTransient.getPoblacionAfectada());
		this.proyecto.setPoblacionesMeta(this.proyectoTransient.getPoblacionesMeta());
		this.proyecto.setPresupuestosPorAnio(this.proyectoTransient.getPresupuestosPorAnio());
		this.proyecto.setPrioridadJurisdiccional(this.proyectoTransient.getPrioridadJurisdiccional());
		this.proyecto.setTipoProyecto(this.proyectoTransient.getTipoProyecto());
		this.proyecto.setTipoUbicacionGeografica(this.proyectoTransient.getTipoUbicacionGeografica());
		this.proyecto.setUnidadMeta(this.proyectoTransient.getUnidadMeta());

	}

	private String getProximoCodigoObjJurisdiccional() {
		String codigoJurisdiccion = this.getJurisdiccion().getCodigo();
		Integer codigoObjetivoJurisdiccional = 0;

		if (!this.getJurisdiccion().getObjetivosJurisdiccionales().isEmpty()) {
			codigoObjetivoJurisdiccional = this.getJurisdiccion().getObjetivosJurisdiccionales().parallelStream()
					.map(oj -> Integer.parseInt(oj.getCodigo().split("\\.")[1])).max(Integer::compare).get();
		}
		return codigoJurisdiccion.concat(".").concat(String.valueOf(codigoObjetivoJurisdiccional + 1));
	}

	private String getProximoCodigoObjOperativo(ObjetivoJurisdiccional objetivoJurisdiccional) {
		Integer codigoObjetivoOperativo = 0;

		if (!objetivoJurisdiccional.getObjetivosOperativos().isEmpty()) {
			codigoObjetivoOperativo = objetivoJurisdiccional.getObjetivosOperativos().parallelStream()
					.map(oj -> Integer.parseInt(oj.getCodigo().split("\\.")[2])).max(Integer::compare).get();
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
		ObjetivoJurisdiccionalService objetivoJurisdiccionalService = this.serviceFactory
				.getObjetivoJurisdiccionalService();

		this.objetivoJurisdiccional = objetivoJurisdiccionalService
				.getObjetivoJurisdiccionalPorCodigo(this.codigoObjJuri);

		if (this.objetivoJurisdiccional == null) {
			this.objetivoJurisdiccional = new ObjetivoJurisdiccional();
			this.objetivoJurisdiccional.setCodigo(this.getProximoCodigoObjJurisdiccional());
			this.objetivoJurisdiccional.setJurisdiccion(this.jurisdiccion);
			this.objetivoJurisdiccional.setNombre(this.nombreObjJuri);
			this.objetivoJurisdiccional.getObjetivosOperativos().add(this.objetivoOperativo);
			this.objetivoJurisdiccional.setIdJurisdiccionAux(this.jurisdiccion.getIdJurisdiccion());
			this.objetivoJurisdiccional = objetivoJurisdiccionalService
					.createObjetivoJurisdiccional(this.objetivoJurisdiccional);
			this.getJurisdiccion().getObjetivosJurisdiccionales().add(this.objetivoJurisdiccional);

		}
	}

	private void buildObjetivoOperativo() throws ESBException, JMSException {
		ObjetivoOperativoService objetivoOperativoService = this.serviceFactory.getObjetivoOperativoService();

		this.objetivoOperativo = objetivoOperativoService.getObjetivoOperativoPorCodigo(this.codigoObjOper);

		if (this.objetivoOperativo == null) {
			this.objetivoOperativo = new ObjetivoOperativo();
			this.objetivoOperativo.setCodigo(this.getProximoCodigoObjOperativo(this.objetivoJurisdiccional));
			this.objetivoOperativo.setNombre(this.nombreObjOper);
			this.objetivoOperativo
					.setIdObjetivoJurisdiccionalAux(this.objetivoJurisdiccional.getIdObjetivoJurisdiccional());
			this.objetivoOperativo = objetivoOperativoService.createObjetivoOperativo(this.objetivoOperativo);
			this.objetivoJurisdiccional.getObjetivosOperativos().add(this.objetivoOperativo);
		}

	}

}
