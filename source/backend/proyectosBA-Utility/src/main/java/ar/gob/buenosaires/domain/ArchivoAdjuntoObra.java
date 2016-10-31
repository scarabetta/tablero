package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.util.Date;

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

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "archivos_obra")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idArchivoObra", "obra", "nombre", "descripcion", "fuente", "fecha" })
@XmlRootElement(name = "ArchivoAdjuntoObra")
public class ArchivoAdjuntoObra implements Serializable {

	private static final long serialVersionUID = -5700687342611622192L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idarchivoobra", nullable = false)
	private Long idArchivoObra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idobra")
	@JsonBackReference
	private Obra obra;

	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "descripcion")
	private String descripcion;

	@Column(name = "fuente")
	private String fuente;
	
	@Column(name = "fecha")
	private Date fecha;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(final String nombre) {
		this.nombre = nombre;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(final Obra obra) {
		this.obra = obra;
	}

	public Long getIdArchivoObra() {
		return idArchivoObra;
	}

	public void setIdArchivoObra(Long idArchivoObra) {
		this.idArchivoObra = idArchivoObra;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFuente() {
		return fuente;
	}

	public void setFuente(String fuente) {
		this.fuente = fuente;
	}

	public Date getFecha() {
		return fecha;
	}

	public void setFecha(Date fecha) {
		this.fecha = fecha;
	}
}
