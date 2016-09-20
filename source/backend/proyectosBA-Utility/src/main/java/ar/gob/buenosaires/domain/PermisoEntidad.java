package ar.gob.buenosaires.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "permiso_entidad")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idPermisoEntidad", "rol", "alta", "baja", "modificacion", "gestion", "nombre" })
@XmlRootElement(name = "PermisoEntidad")
public class PermisoEntidad implements Serializable{

	private static final long serialVersionUID = 334803224125648983L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idpermisoentidad", nullable = false)
	private Long idPermisoEntidad;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idrol")
	@JsonBackReference
	private Rol rol;

	@Column(name = "alta", nullable = false)
	private Boolean alta;

	@Column(name = "baja", nullable = false)
	private Boolean baja;

	@Column(name = "gestion", nullable = false)
	private Boolean gestion;

	@Column(name = "modificacion", nullable = false)
	private Boolean modificacion;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	public Long getIdPermisoEntidad() {
		return idPermisoEntidad;
	}

	public void setIdPermisoEntidad(final Long idPermisoEntidad) {
		this.idPermisoEntidad = idPermisoEntidad;
	}

	public Boolean getAlta() {
		return alta;
	}

	public void setAlta(final Boolean alta) {
		this.alta = alta;
	}

	public Boolean getBaja() {
		return baja;
	}

	public void setBaja(final Boolean baja) {
		this.baja = baja;
	}

	public Boolean getModificacion() {
		return modificacion;
	}

	public void setModificacion(final Boolean modificacion) {
		this.modificacion = modificacion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Boolean getGestion() {
		return gestion;
	}

	public void setGestion(Boolean gestion) {
		this.gestion = gestion;
	}

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}
}
