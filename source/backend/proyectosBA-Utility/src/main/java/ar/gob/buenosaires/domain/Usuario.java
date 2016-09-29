package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.util.ArrayList;
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

import org.apache.commons.lang3.StringUtils;
import org.hibernate.envers.Audited;

@Entity
@Table(name = "usuario")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idUsuario", "nombre", "apellido", "email", "descripcion", "roles", "jurisdicciones", "activo" })
@XmlRootElement(name = "Usuario")
public class Usuario implements Serializable {

	private static final String SECRETARIA = "Secretaria";
	private static final long serialVersionUID = -4092862591595801120L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idusuario", nullable = false)
	private Long idUsuario;

	@Column(name = "nombre")
	private String nombre;

	@Column(name = "apellido")
	private String apellido;

	@Column(name = "email")
	private String email;

	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "activo")
	private Boolean activo;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "rol_por_usuario", 
			joinColumns = { @JoinColumn(name = "usuario_idusuario") }, 
			inverseJoinColumns = { @JoinColumn(name = "rol_idrol") })
	@XmlElement(name = "roles")
	private List<Rol> roles = new ArrayList<Rol>();

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "usuario_por_jurisdiccion", 
			joinColumns = { @JoinColumn(name = "usuario_idusuario") }, 
			inverseJoinColumns = { @JoinColumn(name = "jurisdiccion_idjurisdiccion") })
	@XmlElement(name = "jurisdicciones")
	private List<Jurisdiccion> jurisdicciones = new ArrayList<Jurisdiccion>();

	public Long getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(final Long idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(final String descripcion) {
		this.descripcion = descripcion;
	}

	public List<Rol> getRoles() {
		if(roles == null){
			roles = new ArrayList<Rol>();
		}
		return roles;
	}

	public void setRoles(final List<Rol> roles) {
		this.roles = roles;
	}

	public List<Jurisdiccion> getJurisdicciones() {
		if(jurisdicciones == null){
			jurisdicciones = new ArrayList<Jurisdiccion>();
		}
		return jurisdicciones;
	}

	public void setJurisdicciones(final List<Jurisdiccion> jurisdicciones) {
		this.jurisdicciones = jurisdicciones;
	}

	public String getApellido() {
		return apellido;
	}

	public void setApellido(String apellido) {
		this.apellido = apellido;
	}

	public Boolean getActivo() {
		return activo;
	}

	public void setActivo(Boolean activo) {
		this.activo = activo;
	}

	public boolean tienePerfilSecretaria(){
		if(this.getRoles() != null){
			for (Rol rol : this.getRoles()) {
				if(SECRETARIA.equalsIgnoreCase(StringUtils.stripAccents(rol.getNombre()))){
					return Boolean.TRUE;
				}
			}
		}
		return Boolean.FALSE;
	}
}
