package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "obra")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idObra", "proyecto", "estado", "nombre", "descripcion", "subtipoObra", "idSubtipoObraAux", "referenteEjecucion", "presupuestoTotal", 
		"tipoUbicacion", "direccion", "direccionDesde", "direccionHasta", "detalleUbicacion", "usigSeccion", "usigManzana", "usigParcela", 
		"usigBarrio", "usigUtiu", "usigDistritoEscolar", "usigAreaHospitalaria", "usigComisaria", "usigTransporteCercano", "usigCPU", 
		"comuna", "prioridadJefatura", "informacionRelevamiento", "publicableTableroCiudadano", "direccionUnidad", "expedientes", 
		"presupuestosPorAnio", "archivosAdjuntos", "hitos"})
@XmlRootElement(name = "Obra")
public class Obra implements Serializable {

	private static final long serialVersionUID = 806503159227686034L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idobra", nullable = false)
	private Long idObra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproyecto")
	@JsonBackReference(value = "proy-obra")
	@XmlElement(name = "proyecto")
	private Proyecto proyecto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idsubtipoobra")
	@JsonBackReference(value = "subipo-obra")
	@XmlElement(name = "subtipoObra")
	private SubtipoObra subtipoObra;
	
	@Column(name = "idsubtipoobraaux")
	private Long idSubtipoObraAux;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "obra", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "expedientes")
	@JsonManagedReference
	private List<Expediente> expedientes = new ArrayList<Expediente>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "obra", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "presupuestosPorAnio")
	@JsonManagedReference
	private List<PresupuestoPorAnioObra> presupuestosPorAnio = new ArrayList<PresupuestoPorAnioObra>();

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "obra", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "archivosAdjuntos")
	@JsonManagedReference
	private List<ArchivoAdjuntoObra> archivosAdjuntos = new ArrayList<ArchivoAdjuntoObra>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "obra", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "hitos")
	@JsonManagedReference
	private List<HitoObra> hitos = new ArrayList<HitoObra>();
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "referenteejecucion")
	private String referenteEjecucion;
	
	@Column(name = "presupuestototal")
	private Double presupuestoTotal;
	
	@Column(name = "tipoubicacion", nullable = true)
	private String tipoUbicacion;
	
	@Column(name = "direccion", nullable = true)
	private String direccion;
	
	@Column(name = "direcciondesde", nullable = true)
	private String direccionDesde;
	
	@Column(name = "direccionhasta", nullable = true)
	private String direccionHasta;
	
	@Column(name = "detalleubicacion", nullable = true)
	private String detalleUbicacion;
	
	@Column(name = "usig_seccion", nullable = true)
	private String usigSeccion;
	
	@Column(name = "usig_manzana", nullable = true)
	private String usigManzana;
	
	@Column(name = "usig_parcela", nullable = true)
	private String usigParcela;
	
	@Column(name = "usig_barrio", nullable = true)
	private String usigBarrio;
	
	@Column(name = "usig_utiu", nullable = true)
	private String usigUtiu;
	
	@Column(name = "usig_distritoescolar", nullable = true)
	private String usigDistritoEscolar;
	
	@Column(name = "usig_areahospitalaria", nullable = true)
	private String usigAreaHospitalaria;
	
	@Column(name = "usig_comisaria", nullable = true)
	private String usigComisaria;
	
	@Column(name = "usig_transportecercano", nullable = true)
	private String usigTransporteCercano;
	
	@Column(name = "usig_cpu", nullable = true)
	private String usigCPU;
	
	@OneToOne
	@JoinColumn(name = "idcomuna", nullable = true)
	@XmlElement(name = "comuna")
	private Comuna comuna;
	
	@Column(name = "prioridadjefatura", nullable = true)
	private String prioridadJefatura;
	
	@Column(name = "informacionrelevamiento", nullable = true)
	private String informacionRelevamiento;
	
	@Column(name = "publicabletablerociudadano", nullable = true)
	private Boolean publicableTableroCiudadano;
	
	@Column(name = "direccionunidad", nullable = true)
	private String direccionUnidad;

	public Long getIdObra() {
		return idObra;
	}

	public void setIdObra(Long idObra) {
		this.idObra = idObra;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public SubtipoObra getSubtipoObra() {
		return subtipoObra;
	}

	public void setSubtipoObra(SubtipoObra subtipoObra) {
		this.subtipoObra = subtipoObra;
	}

	public List<Expediente> getExpedientes() {
		return expedientes;
	}

	public void setExpedientes(List<Expediente> expedientes) {
		this.expedientes = expedientes;
	}

	public List<PresupuestoPorAnioObra> getPresupuestosPorAnio() {
		return presupuestosPorAnio;
	}

	public void setPresupuestosPorAnio(List<PresupuestoPorAnioObra> presupuestosPorAnio) {
		this.presupuestosPorAnio = presupuestosPorAnio;
	}

	public List<ArchivoAdjuntoObra> getArchivosAdjuntos() {
		return archivosAdjuntos;
	}

	public void setArchivosAdjuntos(List<ArchivoAdjuntoObra> archivosAdjuntos) {
		this.archivosAdjuntos = archivosAdjuntos;
	}

	public List<HitoObra> getHitos() {
		return hitos;
	}

	public void setHitos(List<HitoObra> hitos) {
		this.hitos = hitos;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
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

	public String getReferenteEjecucion() {
		return referenteEjecucion;
	}

	public void setReferenteEjecucion(String referenteEjecucion) {
		this.referenteEjecucion = referenteEjecucion;
	}

	public Double getPresupuestoTotal() {
		return presupuestoTotal;
	}

	public void setPresupuestoTotal(Double presupuestoTotal) {
		this.presupuestoTotal = presupuestoTotal;
	}

	public String getTipoUbicacion() {
		return tipoUbicacion;
	}

	public void setTipoUbicacion(String tipoUbicacion) {
		this.tipoUbicacion = tipoUbicacion;
	}

	public String getDireccion() {
		return direccion;
	}

	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	public String getDireccionDesde() {
		return direccionDesde;
	}

	public void setDireccionDesde(String direccionDesde) {
		this.direccionDesde = direccionDesde;
	}

	public String getDireccionHasta() {
		return direccionHasta;
	}

	public void setDireccionHasta(String direccionHasta) {
		this.direccionHasta = direccionHasta;
	}

	public String getDetalleUbicacion() {
		return detalleUbicacion;
	}

	public void setDetalleUbicacion(String detalleUbicacion) {
		this.detalleUbicacion = detalleUbicacion;
	}

	public String getUsigSeccion() {
		return usigSeccion;
	}

	public void setUsigSeccion(String usigSeccion) {
		this.usigSeccion = usigSeccion;
	}

	public String getUsigManzana() {
		return usigManzana;
	}

	public void setUsigManzana(String usigManzana) {
		this.usigManzana = usigManzana;
	}

	public String getUsigParcela() {
		return usigParcela;
	}

	public void setUsigParcela(String usigParcela) {
		this.usigParcela = usigParcela;
	}

	public String getUsigBarrio() {
		return usigBarrio;
	}

	public void setUsigBarrio(String usigBarrio) {
		this.usigBarrio = usigBarrio;
	}

	public String getUsigUtiu() {
		return usigUtiu;
	}

	public void setUsigUtiu(String usigUtiu) {
		this.usigUtiu = usigUtiu;
	}

	public String getUsigDistritoEscolar() {
		return usigDistritoEscolar;
	}

	public void setUsigDistritoEscolar(String usigDistritoEscolar) {
		this.usigDistritoEscolar = usigDistritoEscolar;
	}

	public String getUsigAreaHospitalaria() {
		return usigAreaHospitalaria;
	}

	public void setUsigAreaHospitalaria(String usigAreaHospitalaria) {
		this.usigAreaHospitalaria = usigAreaHospitalaria;
	}

	public String getUsigComisaria() {
		return usigComisaria;
	}

	public void setUsigComisaria(String usigComisaria) {
		this.usigComisaria = usigComisaria;
	}

	public String getUsigTransporteCercano() {
		return usigTransporteCercano;
	}

	public void setUsigTransporteCercano(String usigTransporteCercano) {
		this.usigTransporteCercano = usigTransporteCercano;
	}

	public String getUsigCPU() {
		return usigCPU;
	}

	public void setUsigCPU(String usigCPU) {
		this.usigCPU = usigCPU;
	}

	public Comuna getComuna() {
		return comuna;
	}

	public void setComuna(Comuna comuna) {
		this.comuna = comuna;
	}

	public String getPrioridadJefatura() {
		return prioridadJefatura;
	}

	public void setPrioridadJefatura(String prioridadJefatura) {
		this.prioridadJefatura = prioridadJefatura;
	}

	public String getInformacionRelevamiento() {
		return informacionRelevamiento;
	}

	public void setInformacionRelevamiento(String informacionRelevamiento) {
		this.informacionRelevamiento = informacionRelevamiento;
	}

	public Boolean getPublicableTableroCiudadano() {
		return publicableTableroCiudadano;
	}

	public void setPublicableTableroCiudadano(Boolean publicableTableroCiudadano) {
		this.publicableTableroCiudadano = publicableTableroCiudadano;
	}

	public String getDireccionUnidad() {
		return direccionUnidad;
	}

	public void setDireccionUnidad(String direccionUnidad) {
		this.direccionUnidad = direccionUnidad;
	}

	public Long getIdSubtipoObraAux() {
		return idSubtipoObraAux;
	}

	public void setIdSubtipoObraAux(Long idSubtipoObraAux) {
		this.idSubtipoObraAux = idSubtipoObraAux;
	}
}
