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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "expediente_obra")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idExpediente", "obra", "descripcion", "sigla", "numero", "anio"})
@XmlRootElement(name = "Expediente")
public class Expediente implements Serializable {

	private static final long serialVersionUID = 4603816178701385912L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idexpediente", nullable = false)
	private Long idExpediente;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idobra")
	@JsonBackReference
	@XmlElement(name = "obra")
	private Obra obra;
	
	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "sigla")
	private String sigla;
	
	@Column(name = "numero")
	private Integer numero;
	
	@Column(name = "anio")
	private Integer anio;

	public Long getIdExpediente() {
		return idExpediente;
	}

	public void setIdExpediente(Long idExpediente) {
		this.idExpediente = idExpediente;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public Integer getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}
}
