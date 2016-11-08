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
@Table(name = "presupuesto_obra_por_anio")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idPresupuestoPorAnio", "obra", "anio", "presupuesto"})

@XmlRootElement(name = "PresupuestoPorAnioObra")
public class PresupuestoPorAnioObra implements Serializable {

	private static final long serialVersionUID = -1770846664731989725L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idpresupuestoobraporanio", nullable = false)
	private Long idPresupuestoPorAnio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idobra", nullable = false)
	@JsonBackReference
	@XmlElement(name = "obra")
	private Obra obra;

	@Column(name = "anio", nullable = false)
	private Integer anio;

	@Column(name = "presupuesto")
	private Double presupuesto;

	public Long getIdPresupuestoPorAnio() {
		return idPresupuestoPorAnio;
	}

	public void setIdPresupuestoPorAnio(Long idPresupuestoPorAnio) {
		this.idPresupuestoPorAnio = idPresupuestoPorAnio;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Double getPresupuesto() {
		if(presupuesto == null){
			presupuesto = new Double(0);
		}
		return presupuesto;
	}

	public void setPresupuesto(Double presupuesto) {
		this.presupuesto = presupuesto;
	}
}