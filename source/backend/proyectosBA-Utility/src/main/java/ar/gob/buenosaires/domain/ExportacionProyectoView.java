package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "exportacion_proyectos_view")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "nombreJurisidiccion", "nombreProyecto", "idProyecto", "estado", "objetivoEstrategico",
		"objetivoOperativo", "descripcionProyecto", "responsable", "areaNombre", "organismosCorresponsables",
		"fechaInicio", "fechaFin", "tipoProyecto", "implicaCambioLegislativo", "prioridadJurisdiccional", "meta",
		"unidadMeta", "cantidadPoblacionImpactada", "prioridadJefatura", "estadoAprobacion", "poblacionesMeta",
		"ejesDeGobierno", "comunas", "presupuestosPorAnio" })
@XmlRootElement(name = "exportacion_proyectos")
public class ExportacionProyectoView implements Serializable {

	private static final long serialVersionUID = 5288370273454371558L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idproyecto", nullable = false)
	Long idProyecto;

	@Column(name = "nombrejurisidiccion")
	String nombreJurisidiccion;

	@Column(name = "nombreproyecto")
	String nombreProyecto;

	@Column(name = "estado")
	String estado;

	@Column(name = "objetivoestrategico")
	String objetivoEstrategico;

	@Column(name = "objetivooperativo")
	String objetivoOperativo;

	@Column(name = "descripcionproyecto")
	String descripcionProyecto;

	@Column(name = "responsable")
	String responsable;

	@Column(name = "area")
	String areaNombre;

	@Column(name = "organismoscorresponsables")
	String organismosCorresponsables;

	@Column(name = "fechainicio")
	Date fechaInicio;

	@Column(name = "fechafin")
	Date fechaFin;

	@Column(name = "tipoproyecto")
	String tipoProyecto;

	@Column(name = "implicacambiolegislativo")
	Boolean implicaCambioLegislativo;

	@Column(name = "prioridadjurisdiccional")
	String prioridadJurisdiccional;

	@Column(name = "meta")
	BigDecimal meta;

	@Column(name = "unidadmeta")
	String unidadMeta;

	@Column(name = "cantidadpoblacionimpactada")
	Integer cantidadPoblacionImpactada;

	@Column(name = "prioridadjefatura")
	Boolean prioridadJefatura;

	@Column(name = "estadoaprobacion")
	String estadoAprobacion;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "exportacionProyectoViewPresupuestoPorAnio", fetch = FetchType.LAZY)
	@XmlElement(name = "presupuestosPorAnio")
	@JsonManagedReference(value = "exp-presu")
	private List<PresupuestoPorAnio> presupuestosPorAnio = new ArrayList<>();

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

	/**
	 * @return the nombreJurisidiccion
	 */
	public final String getNombreJurisidiccion() {
		return nombreJurisidiccion;
	}

	/**
	 * @param nombreJurisidiccion
	 *            the nombreJurisidiccion to set
	 */
	public final void setNombreJurisidiccion(String nombreJurisidiccion) {
		this.nombreJurisidiccion = nombreJurisidiccion;
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

	/**
	 * @return the objetivoEstrategico
	 */
	public final String getObjetivoEstrategico() {
		return objetivoEstrategico;
	}

	/**
	 * @param objetivoEstrategico
	 *            the objetivoEstrategico to set
	 */
	public final void setObjetivoEstrategico(String objetivoEstrategico) {
		this.objetivoEstrategico = objetivoEstrategico;
	}

	/**
	 * @return the objetivoOperativo
	 */
	public final String getObjetivoOperativo() {
		return objetivoOperativo;
	}

	/**
	 * @param objetivoOperativo
	 *            the objetivoOperativo to set
	 */
	public final void setObjetivoOperativo(String objetivoOperativo) {
		this.objetivoOperativo = objetivoOperativo;
	}

	/**
	 * @return the descripcionProyecto
	 */
	public final String getDescripcionProyecto() {
		return descripcionProyecto;
	}

	/**
	 * @param descripcionProyecto
	 *            the descripcionProyecto to set
	 */
	public final void setDescripcionProyecto(String descripcionProyecto) {
		this.descripcionProyecto = descripcionProyecto;
	}

	/**
	 * @return the responsable
	 */
	public final String getResponsable() {
		return responsable;
	}

	/**
	 * @param responsable
	 *            the responsable to set
	 */
	public final void setResponsable(String responsable) {
		this.responsable = responsable;
	}

	/**
	 * @return the area
	 */

	public final String getAreaNombre() {
		return areaNombre;
	}

	/**
	 * @return the organismosCorresponsables
	 */

	public final String getOrganismosCorresponsables() {
		return organismosCorresponsables;
	}

	/**
	 * @param organismosCorresponsables
	 *            the organismosCorresponsables to set
	 */

	public final void setOrganismosCorresponsables(String organismosCorresponsables) {
		this.organismosCorresponsables = organismosCorresponsables;
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
	 * @return the tipoProyecto
	 */

	public final String getTipoProyecto() {
		return tipoProyecto;
	}

	/**
	 * @param tipoProyecto
	 *            the tipoProyecto to set
	 */

	public final void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	/**
	 * @return the implicaCambioLegislativo
	 */
	public final Boolean isImplicaCambioLegislativo() {
		return implicaCambioLegislativo;
	}

	/**
	 * @param implicaCambioLegislativo
	 *            the implicaCambioLegislativo to set
	 */
	public final void setImplicaCambioLegislativo(Boolean implicaCambioLegislativo) {
		this.implicaCambioLegislativo = implicaCambioLegislativo;
	}

	/**
	 * @return the prioridadJurisdiccional
	 */
	public final String getPrioridadJurisdiccional() {
		return prioridadJurisdiccional;
	}

	/**
	 * @param prioridadJurisdiccional
	 *            the prioridadJurisdiccional to set
	 */
	public final void setPrioridadJurisdiccional(String prioridadJurisdiccional) {
		this.prioridadJurisdiccional = prioridadJurisdiccional;
	}

	/**
	 * @return the meta
	 */

	public final BigDecimal getMeta() {
		return meta;
	}

	/**
	 * @param meta
	 *            the meta to set
	 */

	public final void setMeta(BigDecimal meta) {
		this.meta = meta;
	}

	/**
	 * @return the unidadMeta
	 */

	public final String getUnidadMeta() {
		return unidadMeta;
	}

	/**
	 * @param unidadMeta
	 *            the unidadMeta to set
	 */

	public final void setUnidadMeta(String unidadMeta) {
		this.unidadMeta = unidadMeta;
	}

	/**
	 * @return the cantidadPoblacionImpactada
	 */
	public final Integer getCantidadPoblacionImpactada() {
		return cantidadPoblacionImpactada;
	}

	/**
	 * @param cantidadPoblacionImpactada
	 *            the cantidadPoblacionImpactada to set
	 */
	public final void setCantidadPoblacionImpactada(Integer cantidadPoblacionImpactada) {
		this.cantidadPoblacionImpactada = cantidadPoblacionImpactada;
	}

	/**
	 * @return the prioridadJefatura
	 */
	public final Boolean isPrioridadJefatura() {
		return prioridadJefatura;
	}

	/**
	 * @param prioridadJefatura
	 *            the prioridadJefatura to set
	 */
	public final void setPrioridadJefatura(Boolean prioridadJefatura) {
		this.prioridadJefatura = prioridadJefatura;
	}

	/**
	 * @return the estadoAprobacion
	 */
	public final String getEstadoAprobacion() {
		return estadoAprobacion;
	}

	/**
	 * @param estadoAprobacion
	 *            the estadoAprobacion to set
	 */
	public final void setEstadoAprobacion(String estadoAprobacion) {
		this.estadoAprobacion = estadoAprobacion;
	}

	/**
	 * @return the presupuestosPorAnio
	 */
	public final List<PresupuestoPorAnio> getPresupuestosPorAnio() {
		return presupuestosPorAnio;
	}

	/**
	 * @param presupuestosPorAnio
	 *            the presupuestosPorAnio to set
	 */
	public final void setPresupuestosPorAnio(List<PresupuestoPorAnio> presupuestosPorAnio) {
		this.presupuestosPorAnio = presupuestosPorAnio;
	}

	/**
	 * @return the ejesDeGobierno
	 */
	public final List<EjeDeGobierno> getEjesDeGobierno() {
		return ejesDeGobierno;
	}

	/**
	 * @param ejesDeGobierno
	 *            the ejesDeGobierno to set
	 */
	public final void setEjesDeGobierno(List<EjeDeGobierno> ejesDeGobierno) {
		this.ejesDeGobierno = ejesDeGobierno;
	}

	/**
	 * @return the poblacionesMeta
	 */
	public final List<PoblacionMeta> getPoblacionesMeta() {
		return poblacionesMeta;
	}

	/**
	 * @param poblacionesMeta
	 *            the poblacionesMeta to set
	 */
	public final void setPoblacionesMeta(List<PoblacionMeta> poblacionesMeta) {
		this.poblacionesMeta = poblacionesMeta;
	}

	/**
	 * @return the comunas
	 */
	public final List<Comuna> getComunas() {
		return comunas;
	}

	/**
	 * @param comunas
	 *            the comunas to set
	 */
	public final void setComunas(List<Comuna> comunas) {
		this.comunas = comunas;
	}

	/**
	 * @param areaNombre
	 *            the areaNombre to set
	 */
	public final void setAreaNombre(String areaNombre) {
		this.areaNombre = areaNombre;
	}

	public Double getPresupuestoPrimerAnio() {
		Calendar dateAsCalendar = Calendar.getInstance();
		dateAsCalendar.setTime(getFechaInicio());
		int anio = dateAsCalendar.get(Calendar.YEAR);
		Double importeAñoFechaInicio = new Double(0);

		if (getPresupuestosPorAnio() != null && !getPresupuestosPorAnio().isEmpty()) {
			for (PresupuestoPorAnio ppa : getPresupuestosPorAnio()) {
				if (anio == ppa.getAnio()) {
					importeAñoFechaInicio += ppa.getPresupuesto();
				}
			}
		}
		return importeAñoFechaInicio;
	}

	public Double getPresupuestoTotal() {
		Double total = new Double(0);
		if (getPresupuestosPorAnio() != null && !getPresupuestosPorAnio().isEmpty()) {
			for (PresupuestoPorAnio ppa : getPresupuestosPorAnio()) {
				total += ppa.getPresupuesto();
			}
		}
		return total;
	}

}
