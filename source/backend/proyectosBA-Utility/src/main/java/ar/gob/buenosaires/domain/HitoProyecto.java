package ar.gob.buenosaires.domain;

import java.io.Serializable;
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
@Table(name = "hito_proyecto")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idHito", "proyecto", "hitoPadre", "hitosHijos", "hitoPredecesor", "nombre", "fechaInicio", 
		"fechaFin", "estado", "presupuesto", "esImportante", "idHitoPadreAux", "idProyectoAux"})
@XmlRootElement(name = "HitoProyecto")
public class HitoProyecto implements Serializable {

	private static final long serialVersionUID = -6063864483868772560L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idhito", nullable = false)
	private Long idHito;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproyecto")
	@JsonBackReference(value = "proy-hito")
	private Proyecto proyecto;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idpadre")
	@JsonBackReference(value = "padre-hijo")
	private HitoProyecto hitoPadre;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "hitoPadre", fetch = FetchType.LAZY)
	@JsonManagedReference(value = "padre-hijo")
	@XmlElement(name = "hitosHijos")
	private List<HitoProyecto> hitosHijos;
	
	@OneToOne
	@JoinColumn(name = "idhitopredecesor")
	private HitoProyecto hitoPredecesor;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "fechainicio")
	private Date fechaInicio;

	@Column(name = "fechafin")
	private Date fechaFin;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "esimportante")
	private Boolean esImportante;
	
	@Column(name = "presupuesto")
	private Double presupuesto;
	
	@Column(name = "idhitopadreaux")
	private Long idHitoPadreAux;

	@Column(name = "idproyectoaux")
	private Long idProyectoAux;
	
	public Long getIdHito() {
		return idHito;
	}

	public void setIdHito(Long idHito) {
		this.idHito = idHito;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public HitoProyecto getHitoPadre() {
		return hitoPadre;
	}

	public void setHitoPadre(HitoProyecto hitoPadre) {
		this.hitoPadre = hitoPadre;
	}

	public List<HitoProyecto> getHitosHijos() {
		if(hitosHijos == null){
			hitosHijos = new ArrayList<HitoProyecto>();
		}
		return hitosHijos;
	}

	public void setHitosHijos(List<HitoProyecto> hitosHijos) {
		if(this.hitosHijos == null){
			this.hitosHijos = hitosHijos;
		} else if(hitosHijos != null){
			this.hitosHijos.clear();
			this.hitosHijos.addAll(hitosHijos);
		}
	}

	public HitoProyecto getHitoPredecesor() {
		return hitoPredecesor;
	}

	public void setHitoPredecesor(HitoProyecto hitoPredecesor) {
		this.hitoPredecesor = hitoPredecesor;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
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

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Double getPresupuesto() {
		if(presupuesto == null){
			presupuesto = new Double(0);
		}
		return presupuesto;
	}

	public void setPresupuesto(Double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Boolean getEsImportante() {
		return esImportante;
	}

	public void setEsImportante(Boolean esImportante) {
		this.esImportante = esImportante;
	}

	public Long getIdHitoPadreAux() {
		return idHitoPadreAux;
	}

	public void setIdHitoPadreAux(Long idHitoPadreAux) {
		this.idHitoPadreAux = idHitoPadreAux;
	}

	public Long getIdProyectoAux() {
		return idProyectoAux;
	}

	public void setIdProyectoAux(Long idProyectoAux) {
		this.idProyectoAux = idProyectoAux;
	}
}
