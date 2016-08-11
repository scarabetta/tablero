package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.util.List;

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

@Entity
@Table(name = "rol")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idRol", "nombre", "descripcion", "permisosEntidad" })
@XmlRootElement(name = "Rol")
public class Rol implements Serializable {

	private static final long serialVersionUID = 8568616576961220696L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idrol", nullable = false)
	private Long idRol;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rol_por_usuario", joinColumns = {
			@JoinColumn(name = "usuario_idusuario") }, 
			inverseJoinColumns = { @JoinColumn(name = "rol_idrol") })
	@XmlTransient
	private List<Usuario> usuarios;

	@OneToMany(mappedBy = "rol", fetch = FetchType.LAZY)
	@XmlElement(name = "permisosEntidad")
	private List<PermisoEntidad> permisosEntidad;

	public Long getIdRol() {
		return idRol;
	}

	public void setIdRol(final Long idRol) {
		this.idRol = idRol;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public List<PermisoEntidad> getPermisosEntidad() {
		return permisosEntidad;
	}

	public void setPermisosEntidad(final List<PermisoEntidad> permisosEntidad) {
		this.permisosEntidad = permisosEntidad;
	}

	public List<Usuario> getUsuarios() {
		return usuarios;
	}

	public void setUsuarios(final List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}
}
