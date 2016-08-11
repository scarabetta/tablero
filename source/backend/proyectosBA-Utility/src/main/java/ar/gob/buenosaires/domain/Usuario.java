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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "usuario")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idUsuario", "nombre", "email", "descripcion", "roles" })
@XmlRootElement(name = "Usuario")
public class Usuario implements Serializable {

	private static final long serialVersionUID = -4092862591595801120L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusuario", nullable = false)
	private Long idUsuario;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "email")
	private String email;

	@Column(name = "descripcion")
	private String descripcion;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rol_por_usuario", joinColumns = {
			@JoinColumn(name = "usuario_idusuario") }, 
			inverseJoinColumns = { @JoinColumn(name = "rol_idrol") })
	@XmlElement(name = "roles")
	public List<Rol> roles;

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Rol> getRoles() {
		return roles;
	}

	public void setRoles(List<Rol> roles) {
		this.roles = roles;
	}
}
