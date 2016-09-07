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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "area")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idArea", "nombre" })
@XmlRootElement(name = "Area")
public class Area implements Serializable {

	private static final long serialVersionUID = -3489392095395211583L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idarea", nullable = false)
	private Long idArea;

	@Column(name = "nombre", nullable = true)
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idjurisdiccion")
	@JsonBackReference
	@XmlTransient
	private Jurisdiccion jurisdiccion;

	@Column(name = "idjurisdiccion", insertable = false, updatable = false)
	@XmlTransient
	private Long idJurisdiccion;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Long getIdArea() {
		return idArea;
	}

	public void setIdArea(final Long idArchivoProyecto) {
		this.idArea = idArchivoProyecto;
	}

	public Jurisdiccion getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(Jurisdiccion jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

}
