package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "proyecto")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idProyecto", "nombre", "descripcion", "objetivoOperativo", "tipoProyecto",
		"meta","unidadMeta","poblacionAfectada","liderProyecto","area","tipoUbicacionGeografica",
		"direccion","cambioLegislativo","fechaInicio","fechaFin","prioridadJurisdiccional","estado", "ejesDeGobierno", 
		"poblacionesMeta", "comunas", "codigo", "idJurisdiccion2", "idObjetivoJurisdiccional2", "idObjetivoOperativo2",
		"organismosCorresponsables", "presupuestosPorAnio" })

@XmlRootElement(name = "Proyecto")
public class Proyecto implements Serializable {

	private static final long serialVersionUID = 5655878555825633471L;

	@Id 	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idproyecto", nullable = false)
	private Long idProyecto;		

	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "codigo", nullable = true)
	private String codigo;	
	
	@Column(name = "descripcion", nullable = true)
	private String descripcion;
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idobjetivooperativo")
	@JsonBackReference
	private ObjetivoOperativo objetivoOperativo;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "proyectoPresupuestoPorAnio",fetch = FetchType.LAZY)
	@XmlElement(name = "presupuestosPorAnio")
	@JsonManagedReference	
    private List<PresupuestoPorAnio> presupuestosPorAnio;
	
	@Column(name = "tipoproyecto", nullable = true)
	private String tipoProyecto;
	
	@Column(name = "meta", nullable = true)
	private BigDecimal meta = BigDecimal.ZERO.setScale(2, BigDecimal.ROUND_HALF_EVEN);	
	
	@Column(name = "unidadmeta", nullable = true)
	private String unidadMeta;
	
	@Column(name = "poblacionafectada", nullable = true)
	private Integer poblacionAfectada;
	
	@Column(name = "liderproyecto", nullable = true)
	private String liderProyecto;
	
	@Column(name = "area", nullable = true)
	private String area;
	
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
	
	@ManyToMany(fetch = FetchType.LAZY) 
	@JoinTable(name = "eje_de_gobierno_por_proyecto", 
		joinColumns = { @JoinColumn(name = "id_proyecto") }, 
	    inverseJoinColumns = { @JoinColumn(name = "id_ejedegobierno") })
	@XmlElement(name = "ejesDeGobierno")	
	private List<EjeDeGobierno> ejesDeGobierno;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "poblacion_meta_por_proyecto", 
		joinColumns = { @JoinColumn(name = "idproyecto") }, 
	    inverseJoinColumns = { @JoinColumn(name = "idpoblacionmeta") })
	@XmlElement(name = "poblacionesMeta")	
	private List<PoblacionMeta> poblacionesMeta;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "comuna_por_proyecto", 
		joinColumns = { @JoinColumn(name = "idproyecto") }, 
	    inverseJoinColumns = { @JoinColumn(name = "idcomuna") })
	@XmlElement(name = "comunas")	
	private List<Comuna> comunas;

	public Long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(Long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getTipoProyecto() {
		return tipoProyecto;
	}

	public void setTipoProyecto(String tipoProyecto) {
		this.tipoProyecto = tipoProyecto;
	}

	public BigDecimal getMeta() {
		return meta;
	}

	public void setMeta(BigDecimal meta) {
		this.meta = meta;
	}

	public String getUnidadMeta() {
		return unidadMeta;
	}

	public void setUnidadMeta(String unidadMeta) {
		this.unidadMeta = unidadMeta;
	}

	public Integer getPoblacionAfectada() {
		return poblacionAfectada;
	}

	public void setPoblacionAfectada(Integer poblacionAfectada) {
		this.poblacionAfectada = poblacionAfectada;
	}

	public String getLiderProyecto() {
		return liderProyecto;
	}

	public void setLiderProyecto(String liderProyecto) {
		this.liderProyecto = liderProyecto;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getTipoUbicacionGeografica() {
		return tipoUbicacionGeografica;
	}

	public void setTipoUbicacionGeografica(String tipoUbicacionGeografica) {
		this.tipoUbicacionGeografica = tipoUbicacionGeografica;
	}

	public String getDireccion() {
		return direccion;
	}	

	public String getOrganismosCorresponsables() {
		return organismosCorresponsables;
	}

	public void setOrganismosCorresponsables(String organismosCorresponsables) {
		this.organismosCorresponsables = organismosCorresponsables;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public Boolean getCambioLegislativo() {
		return cambioLegislativo;
	}

	public void setCambioLegislativo(Boolean cambioLegislativo) {
		this.cambioLegislativo = cambioLegislativo;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getPrioridadJurisdiccional() {
		return prioridadJurisdiccional;
	}

	public void setPrioridadJurisdiccional(String prioridadJurisdiccional) {
		this.prioridadJurisdiccional = prioridadJurisdiccional;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	
	public ObjetivoOperativo getObjetivoOperativo() {
		return objetivoOperativo;
	}

	public void setObjetivoOperativo(ObjetivoOperativo objetivoOperativo) {
		this.objetivoOperativo = objetivoOperativo;
	}

	public List<EjeDeGobierno> getEjesDeGobierno() {
		if(ejesDeGobierno == null) {
			ejesDeGobierno = new ArrayList<EjeDeGobierno>();
		}
		return ejesDeGobierno;
	}

	public void setEjesDeGobierno(List<EjeDeGobierno> ejesDeGobierno) {
		this.ejesDeGobierno = ejesDeGobierno;
	}

	public List<PoblacionMeta> getPoblacionesMeta() {
		if(poblacionesMeta == null) {
			poblacionesMeta = new ArrayList<PoblacionMeta>();
		}
		return poblacionesMeta;
	}

	public void setPoblacionesMeta(List<PoblacionMeta> poblacionesMeta) {
		this.poblacionesMeta = poblacionesMeta;
	}

	public List<Comuna> getComunas() {
		if(comunas == null) {
			comunas = new ArrayList<Comuna>();
		}
		return comunas;
	}

	public void setComunas(List<Comuna> comunas) {
		this.comunas = comunas;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Long getIdJurisdiccion2() {
		return idJurisdiccion2;
	}

	public void setIdJurisdiccion2(Long idJurisdiccion2) {
		this.idJurisdiccion2 = idJurisdiccion2;
	}

	public Long getIdObjetivoJurisdiccional2() {
		return idObjetivoJurisdiccional2;
	}

	public void setIdObjetivoJurisdiccional2(Long idObjetivoJurisdiccional2) {
		this.idObjetivoJurisdiccional2 = idObjetivoJurisdiccional2;
	}

	public Long getIdObjetivoOperativo2() {
		return idObjetivoOperativo2;
	}

	public void setIdObjetivoOperativo2(Long idObjetivoOperativo2) {
		this.idObjetivoOperativo2 = idObjetivoOperativo2;
	}

	public List<PresupuestoPorAnio> getPresupuestosPorAnio() {
		if(presupuestosPorAnio == null) {
			presupuestosPorAnio = new ArrayList<PresupuestoPorAnio>();
		}
		return presupuestosPorAnio;
	}

	public void setPresupuestosPorAnio(List<PresupuestoPorAnio> presupuestosPorAnio) {
		this.presupuestosPorAnio = presupuestosPorAnio;
	}
	
}
