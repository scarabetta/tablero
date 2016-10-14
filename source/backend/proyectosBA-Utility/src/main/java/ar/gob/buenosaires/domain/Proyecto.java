package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "proyecto")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idProyecto", "nombre", "descripcion", "objetivoOperativo", "tipoProyecto", "meta", "unidadMeta",
		"poblacionAfectada", "liderProyecto", "area", "tipoUbicacionGeografica", "direccion", "cambioLegislativo",
		"fechaInicio", "fechaFin", "prioridadJurisdiccional", "estado", "ejesDeGobierno", "poblacionesMeta", "comunas",
		"codigo", "idJurisdiccion2", "idObjetivoJurisdiccional2", "idObjetivoOperativo2", "organismosCorresponsables",
		"presupuestosPorAnio", "coordenadaX", "coordenadaY", "archivos", "verificado", "temasTransversales",
		"compromisosPublicos", "otrasEtiquetas", "totalPresupuestoAprobado", "prioridadJefatura" })

@XmlRootElement(name = "Proyecto")
public class Proyecto implements Serializable {

	private static final String TIPO_UBICACION_DIRECCION = "Dirección";

	private static final String TIPO_UBICACION_COMUNAS = "Comunas";

	private static final String DOUBLE_VACIO = "0.00";

	private static final long serialVersionUID = 5655878555825633471L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idproyecto", nullable = false)
	private Long idProyecto;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "codigo", nullable = true)
	private String codigo;

	@Column(name = "descripcion", nullable = true)
	private String descripcion;

	@OneToOne
	@JoinColumn(name = "idarea")
	@XmlElement(name = "area")
	private Area area;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idobjetivooperativo")
	@JsonBackReference
	private ObjetivoOperativo objetivoOperativo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "presupuestosPorAnio")
	@JsonManagedReference(value = "proy-presu")
	private List<PresupuestoPorAnio> presupuestosPorAnio = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "proyecto", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "archivoPoryecto")
	@JsonManagedReference
	private List<ArchivoProyecto> archivos = new ArrayList<>();

	@Column(name = "tipoproyecto", nullable = true)
	private String tipoProyecto;

	@Column(name = "meta", nullable = true)
	private BigDecimal meta;

	@Column(name = "unidadmeta", nullable = true)
	private String unidadMeta;

	@Column(name = "poblacionafectada", nullable = true)
	private Long poblacionAfectada;

	@Column(name = "liderproyecto", nullable = true)
	private String liderProyecto;

	// @Column(name = "area", nullable = true)
	// private String area;

	@Column(name = "tipoubicaciongeografica", nullable = true)
	private String tipoUbicacionGeografica;

	@Column(name = "direccion", nullable = true)
	private String direccion;

	@Column(name = "cambiolegislativo", nullable = true, columnDefinition = "TINYINT(1)")
	private Boolean cambioLegislativo = null;

	@Column(name = "fechainicio", nullable = false)
	private Date fechaInicio;

	@Column(name = "fechafin", nullable = true)
	private Date fechaFin;

	@Column(name = "prioridadjurisdiccional", nullable = true)
	private String prioridadJurisdiccional;

	@Column(name = "idjurisdiccion2", nullable = true)
	private Long idJurisdiccion2;

	@Column(name = "idobjetivojurisdiccional2", nullable = true)
	private Long idObjetivoJurisdiccional2;

	@Column(name = "idobjetivooperativo2", nullable = false)
	private Long idObjetivoOperativo2;

	@Column(name = "organismoscorresponsables", nullable = true)
	private String organismosCorresponsables;

	@Column(name = "estado", nullable = false)
	private String estado;

	@Column(name = "verificado", nullable = true, columnDefinition = "TINYINT(1)")
	private Boolean verificado = false;

	@Column(name = "coordenadax", nullable = true)
	private String coordenadaX;

	@Column(name = "coordenaday", nullable = true)
	private String coordenadaY;

	@Column(name = "prioridad_jefatura", nullable = true)
	private String prioridadJefatura;

	@Column(name = "total_presu_aprobado", nullable = true)
	private Double totalPresupuestoAprobado;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "eje_de_gobierno_por_proyecto", joinColumns = {
			@JoinColumn(name = "id_proyecto") }, inverseJoinColumns = { @JoinColumn(name = "id_ejedegobierno") })
	@XmlElement(name = "ejesDeGobierno")
	private List<EjeDeGobierno> ejesDeGobierno;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "poblacion_meta_por_proyecto", joinColumns = {
			@JoinColumn(name = "idproyecto") }, inverseJoinColumns = { @JoinColumn(name = "idpoblacionmeta") })
	@XmlElement(name = "poblacionesMeta")
	private List<PoblacionMeta> poblacionesMeta;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "comuna_por_proyecto", joinColumns = { @JoinColumn(name = "idproyecto") }, inverseJoinColumns = {
			@JoinColumn(name = "idcomuna") })
	@XmlElement(name = "comunas")
	private List<Comuna> comunas;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "tema_transversal_por_proyecto", joinColumns = {
			@JoinColumn(name = "idproyecto") }, inverseJoinColumns = { @JoinColumn(name = "idtematransversal") })
	@XmlElement(name = "temasTransversales")
	private List<TemaTransversal> temasTransversales;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "compromiso_publico_por_proyecto", joinColumns = {
			@JoinColumn(name = "idproyecto") }, inverseJoinColumns = { @JoinColumn(name = "idcompromisopublico") })
	@XmlElement(name = "compromisosPublicos")
	private List<CompromisoPublico> compromisosPublicos;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinTable(name = "otras_etiquetas_por_proyecto", joinColumns = {
			@JoinColumn(name = "idproyecto") }, inverseJoinColumns = { @JoinColumn(name = "idetiqueta") })
	@XmlElement(name = "otrasEtiquetas")
	private List<OtraEtiqueta> otrasEtiquetas;

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(final Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(final String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public BigDecimal getMeta() {
		return meta;
	}

	public void setMeta(final BigDecimal meta) {
		this.meta = meta;
	}

	public String getUnidadMeta() {
		return unidadMeta;
	}

	public void setUnidadMeta(final String unidadMeta) {
		this.unidadMeta = unidadMeta;
	}

	public Long getPoblacionAfectada() {
		return poblacionAfectada;
	}

	public void setPoblacionAfectada(final Long poblacionAfectada) {
		this.poblacionAfectada = poblacionAfectada;
	}

	public String getLiderProyecto() {
		return liderProyecto;
	}

	public void setLiderProyecto(final String liderProyecto) {
		this.liderProyecto = liderProyecto;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(final Area area) {
		this.area = area;
	}

	public String getTipoUbicacionGeografica() {
		return tipoUbicacionGeografica;
	}

	public void setTipoUbicacionGeografica(final String tipoUbicacionGeografica) {
		this.tipoUbicacionGeografica = tipoUbicacionGeografica;
	}

	public String getDireccion() {
		return direccion;
	}

	public String getOrganismosCorresponsables() {
		return organismosCorresponsables;
	}

	public void setOrganismosCorresponsables(final String organismosCorresponsables) {
		this.organismosCorresponsables = organismosCorresponsables;
	}

	public void setDireccion(final String direccion) {
		this.direccion = direccion;
	}

	public Boolean getCambioLegislativo() {
		return cambioLegislativo;
	}

	public void setCambioLegislativo(final Boolean cambioLegislativo) {
		this.cambioLegislativo = cambioLegislativo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(final Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(final Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPrioridadJurisdiccional() {
		return prioridadJurisdiccional;
	}

	public void setPrioridadJurisdiccional(final String prioridadJurisdiccional) {
		this.prioridadJurisdiccional = prioridadJurisdiccional;
	}

	public String getEstado() {
		if (estado == null) {
			estado = EstadoProyecto.INCOMPLETO.getName();
		}
		return estado;
	}

	public void setEstado(final String estado) {
		this.estado = estado;
	}

	public Boolean getVerificado() {
		return verificado;
	}

	public void setVerificado(Boolean verificado) {
		this.verificado = verificado;
	}

	public ObjetivoOperativo getObjetivoOperativo() {
		return objetivoOperativo;
	}

	public void setObjetivoOperativo(final ObjetivoOperativo objetivoOperativo) {
		this.objetivoOperativo = objetivoOperativo;
	}

	public List<EjeDeGobierno> getEjesDeGobierno() {
		if (ejesDeGobierno == null) {
			ejesDeGobierno = new ArrayList<>();
		}
		return ejesDeGobierno;
	}

	public void setEjesDeGobierno(final List<EjeDeGobierno> ejesDeGobierno) {
		this.ejesDeGobierno = ejesDeGobierno;
	}

	public List<PoblacionMeta> getPoblacionesMeta() {
		if (poblacionesMeta == null) {
			poblacionesMeta = new ArrayList<>();
		}
		return poblacionesMeta;
	}

	public void setPoblacionesMeta(final List<PoblacionMeta> poblacionesMeta) {
		this.poblacionesMeta = poblacionesMeta;
	}

	public List<Comuna> getComunas() {
		if (comunas == null) {
			comunas = new ArrayList<>();
		}
		return comunas;
	}

	public List<TemaTransversal> getTemasTransversales() {
		if (temasTransversales == null) {
			temasTransversales = new ArrayList<>();
		}
		return temasTransversales;
	}

	public void setTemasTransversales(List<TemaTransversal> temasTransversales) {
		this.temasTransversales = temasTransversales;
	}

	public List<CompromisoPublico> getCompromisosPublicos() {
		if (compromisosPublicos == null) {
			compromisosPublicos = new ArrayList<>();
		}
		return compromisosPublicos;
	}

	public void setCompromisosPublicos(List<CompromisoPublico> compromisosPublicos) {
		this.compromisosPublicos = compromisosPublicos;
	}

	public List<OtraEtiqueta> getOtrasEtiquetas() {
		if (otrasEtiquetas == null) {
			otrasEtiquetas = new ArrayList<>();
		}
		return otrasEtiquetas;
	}

	public void setOtrasEtiquetas(List<OtraEtiqueta> otrasEtiquetas) {
		this.otrasEtiquetas = otrasEtiquetas;
	}

	public void setComunas(final List<Comuna> comunas) {
		this.comunas = comunas;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(final String codigo) {
		this.codigo = codigo;
	}

	public Long getIdJurisdiccion2() {
		return idJurisdiccion2;
	}

	public void setIdJurisdiccion2(final Long idJurisdiccion2) {
		this.idJurisdiccion2 = idJurisdiccion2;
	}

	public Long getIdObjetivoJurisdiccional2() {
		return idObjetivoJurisdiccional2;
	}

	public void setIdObjetivoJurisdiccional2(final Long idObjetivoJurisdiccional2) {
		this.idObjetivoJurisdiccional2 = idObjetivoJurisdiccional2;
	}

	public Long getIdObjetivoOperativo2() {
		return idObjetivoOperativo2;
	}

	public void setIdObjetivoOperativo2(final Long idObjetivoOperativo2) {
		this.idObjetivoOperativo2 = idObjetivoOperativo2;
	}

	public List<PresupuestoPorAnio> getPresupuestosPorAnio() {
		if (presupuestosPorAnio == null) {
			presupuestosPorAnio = new ArrayList<>();
		}
		return presupuestosPorAnio;
	}

	public void setPresupuestosPorAnio(final List<PresupuestoPorAnio> presupuestosPorAnio) {
		if (this.presupuestosPorAnio == null) {
			this.presupuestosPorAnio = presupuestosPorAnio;
		} else if (presupuestosPorAnio != null) {
			this.presupuestosPorAnio.clear();
			this.presupuestosPorAnio.addAll(presupuestosPorAnio);
		}
	}

	public String getCoordenadaX() {
		return coordenadaX;
	}

	public void setCoordenadaX(final String coordenadaX) {
		this.coordenadaX = coordenadaX;
	}

	public String getCoordenadaY() {
		return coordenadaY;
	}

	public void setCoordenadaY(final String coordenadaY) {
		this.coordenadaY = coordenadaY;
	}

	@JsonIgnore
	public String getEstadoActualizado() {
		final Object[] propiedades = getPropiedadesAValidar();

		if (necesitaCalcularEstado()) {
			return obtenerEstado(propiedades);
		} else {
			return estado;
		}
	}

	private boolean necesitaCalcularEstado() {
		// Solo debemos calcular el estado si esta incompleto, completo o vacio
		List<String> estadosParaCalcular = Arrays.asList(EstadoProyecto.INCOMPLETO.getName(),
				EstadoProyecto.COMPLETO.getName());
		return estadosParaCalcular.contains(estado) || StringUtils.isEmpty(estado);

	}

	private String obtenerEstado(final Object[] propiedades) {
		for (final Object obj : propiedades) {
			if (obj == null) {
				return EstadoProyecto.INCOMPLETO.getName();
			} else if (String.valueOf(obj).isEmpty() || String.valueOf(obj).equals(DOUBLE_VACIO)) {
				return EstadoProyecto.INCOMPLETO.getName();
			}
		}
		return validarUbicacion();
	}

	private String validarUbicacion() {
		if (TIPO_UBICACION_COMUNAS.equalsIgnoreCase(tipoUbicacionGeografica)) {
			if (comunas == null || comunas.isEmpty()) {
				return EstadoProyecto.INCOMPLETO.getName();
			}
		} else if (TIPO_UBICACION_DIRECCION.equalsIgnoreCase(tipoUbicacionGeografica)) {
			if (direccion == null || direccion.isEmpty()) {
				// || coordenadaX == null || coordenadaX.isEmpty()
				// || coordenadaY == null || coordenadaY.isEmpty()) {

				return EstadoProyecto.INCOMPLETO.getName();
			}
		}
		return EstadoProyecto.COMPLETO.getName();
	}

	private Object[] getPropiedadesAValidar() {
		return new Object[] { getNombre(), getDescripcion(), getObjetivoOperativo(), getTipoProyecto(), getMeta(),
				getUnidadMeta(), getPoblacionAfectada(), getLiderProyecto(), getArea(), getCambioLegislativo(),
				getFechaInicio(), getFechaFin(), getTipoUbicacionGeografica(), getPrioridadJurisdiccional(),
				getEstado(), getPoblacionesMeta(), getIdObjetivoOperativo2(), getPresupuestosPorAnio() };
	}
	
	@JsonIgnore
	public Double getTotalPedido() {
		Double totalPedido = new Double(0);
		for (PresupuestoPorAnio presu: getPresupuestosPorAnio()) {
			totalPedido += presu.getPresupuesto();
		}
		return totalPedido;
	}

	public List<ArchivoProyecto> getArchivos() {
		if (archivos == null) {
			archivos = new ArrayList<>();
		}
		return archivos;
	}

	public void setArchivos(final List<ArchivoProyecto> archivos) {
		if (this.archivos == null) {
			this.archivos = archivos;
		} else if (archivos != null) {
			this.archivos.clear();
			this.archivos.addAll(archivos);
		}
	}

	public String getPrioridadJefatura() {
		return prioridadJefatura;
	}

	public void setPrioridadJefatura(String prioridadJefatura) {
		this.prioridadJefatura = prioridadJefatura;
	}

	public Double getTotalPresupuestoAprobado() {
		return totalPresupuestoAprobado;
	}

	public void setTotalPresupuestoAprobado(Double totalPresupuestoAprobado) {
		this.totalPresupuestoAprobado = totalPresupuestoAprobado;
	}

}
