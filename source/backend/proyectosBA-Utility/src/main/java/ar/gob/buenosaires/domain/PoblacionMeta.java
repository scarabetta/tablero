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
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "poblacion_meta")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idPoblacionMeta", "nombre", "proyectosPoblacionMeta" })
public class PoblacionMeta implements Serializable {
	
	private static final long serialVersionUID = 6065646101212243564L;
	
	@Id 	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idpoblacionmeta", nullable = false)
	private Long idPoblacionMeta;		

	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "poblacion_meta_por_proyecto", 
		joinColumns = { @JoinColumn(name = "idproyecto") }, 
	    inverseJoinColumns = { @JoinColumn(name = "idpoblacionmeta") })
	@XmlElement(name = "proyectosPoblacionMeta")
	private List<Proyecto> proyectosPoblacionMeta;

	public Long getIdPoblacionMeta() {
		return idPoblacionMeta;
	}

	public void setIdPoblacionMeta(Long idPoblacionMeta) {
		this.idPoblacionMeta = idPoblacionMeta;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

//	public List<Proyecto> getProyectos() {
//		return proyectosPoblacionMeta;
//	}
//
//	public void setProyectos(List<Proyecto> proyectos) {
//		this.proyectosPoblacionMeta = proyectos;
//	}			

}
