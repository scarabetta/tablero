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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "hito_obra")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idHito", "obra", "nombre", "fechaInicio", "fechaFin", "estado", "esImportante"})
@XmlRootElement(name = "HitoObra")
public class HitoObra implements Serializable {

	private static final long serialVersionUID = -6063864483868772560L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idhito", nullable = false)
	private Long idHito;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idobra")
	@JsonBackReference
	@XmlElement(name = "obra")
	private Obra obra;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "fechainicio")
	private Date fechaInicio;

	@Column(name = "fechafin")
	private Date fechaFin;
	
	@Column(name = "estado", nullable = false)
	private String estado;
	
	@Column(name = "esimportante")
	private Boolean esImportante;

	public Long getIdHito() {
		return idHito;
	}

	public void setIdHito(Long idHito) {
		this.idHito = idHito;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Date getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(Date fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	public Date getFechaFin() {
		return fechaFin;
	}

	public void setFechaFin(Date fechaFin) {
		this.fechaFin = fechaFin;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public Boolean getEsImportante() {
		return esImportante;
	}

	public void setEsImportante(Boolean esImportante) {
		this.esImportante = esImportante;
	}
}
