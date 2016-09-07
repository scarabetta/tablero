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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "archivos_proyecto")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idArchivoProyecto", "nombre" })
@XmlRootElement(name = "ArchivoProyecto")
public class ArchivoProyecto implements Serializable {

	private static final long serialVersionUID = -5700687342611622192L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idarchivoproyecto", nullable = false)
	private Long idArchivoProyecto;

	@Column(name = "nombre", nullable = true)
	private String nombre;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "proyecto_idproyecto")
	@JsonBackReference
	@XmlTransient
	private Proyecto proyectoDelArchivo;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Long getIdArchivoProyecto() {
		return idArchivoProyecto;
	}

	public void setIdArchivoProyecto(final Long idArchivoProyecto) {
		this.idArchivoProyecto = idArchivoProyecto;
	}

	public Proyecto getProyecto() {
		return proyectoDelArchivo;
	}

	public void setProyecto(final Proyecto proyecto) {
		proyectoDelArchivo = proyecto;
	}

}
