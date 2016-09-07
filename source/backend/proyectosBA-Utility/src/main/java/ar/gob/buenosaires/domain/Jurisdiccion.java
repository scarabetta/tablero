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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "jurisdiccion")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idJurisdiccion", "nombre", "abreviatura", "mision", "objetivosJurisdiccionales", "areas", "codigo" })

@XmlRootElement(name = "Jurisdiccion")
public class Jurisdiccion implements Serializable {

	private static final long serialVersionUID = -5543742654277501123L;
	
	@Id 	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idjurisdiccion", nullable = false)
	private Long idJurisdiccion;
	
	@Column(name = "nombre")
	private String nombre;

	@Column(name = "abreviatura")
	private String abreviatura;

	@Column(name = "mision")
	private String mision;
	
	@Column(name = "codigo")
	private String codigo;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "jurisdiccion",fetch = FetchType.LAZY)
	@XmlElement(name = "areas")
	@JsonManagedReference
    private List<Area> areas = new ArrayList<>();

	@OneToMany(cascade = CascadeType.ALL,mappedBy = "jurisdiccion",fetch = FetchType.LAZY)
	@XmlElement(name = "objetivosJurisdiccionales")
	@JsonManagedReference
    private List<ObjetivoJurisdiccional> objetivosJurisdiccionales = new ArrayList<>();
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_por_jurisdiccion", joinColumns = {
			@JoinColumn(name = "usuario_idusuario") }, 
			inverseJoinColumns = { @JoinColumn(name = "jurisdiccion_idjurisdiccion") })
	@XmlTransient
	private List<Usuario> usuarios;

	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}
	
	public Long setIdJurisdiccion(Long idJurisdiccion) {
		return this.idJurisdiccion = idJurisdiccion;
	}
	
	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getMision() {
		return mision;
	}

	public void setMision(String mision) {
		this.mision = mision;
	}

	public List<ObjetivoJurisdiccional> getObjetivosJurisdiccionales() {
		return objetivosJurisdiccionales;
	}
	
	public void setObjetivosJurisdiccionales(List<ObjetivoJurisdiccional> objetivosJurisdiccionales) {
		this.objetivosJurisdiccionales = objetivosJurisdiccionales;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}	

}
