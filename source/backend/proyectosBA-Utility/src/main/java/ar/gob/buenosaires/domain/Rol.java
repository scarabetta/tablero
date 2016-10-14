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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "rol")
@Audited
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


	@OneToMany(cascade = CascadeType.ALL, mappedBy = "rol", 
			fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "permisosEntidad")
	@JsonManagedReference
	private List<PermisoEntidad> permisosEntidad = new ArrayList<PermisoEntidad>();

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
		if(this.permisosEntidad == null){
			this.permisosEntidad = new ArrayList<PermisoEntidad>();
		}
		return permisosEntidad;
	}

	public void setPermisosEntidad(final List<PermisoEntidad> permisosEntidad) {
		if(this.permisosEntidad == null){
			this.permisosEntidad = permisosEntidad;
		} else if(permisosEntidad != null){
			this.permisosEntidad.clear();
			this.permisosEntidad.addAll(permisosEntidad);
		}
	}
}
