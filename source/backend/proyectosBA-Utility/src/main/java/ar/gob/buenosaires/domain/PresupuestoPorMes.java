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
@Table(name = "presupuesto_proyecto_por_mes")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idPresupuestoPorMes", "proyecto", "idProyectoAux", "anio", "mes", "presupuesto"})
@XmlRootElement(name = "PresupuestoPorMes")
public class PresupuestoPorMes implements Serializable {

	private static final long serialVersionUID = -4809233859592960882L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idpresupuestoproyectopormes", nullable = false)
	private Long idPresupuestoPorMes;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproyecto")
	@JsonBackReference
	@XmlElement(name = "proyecto")
	private Proyecto proyecto;
	
	@Column(name = "idproyectoaux", nullable = false)
	private Long idProyectoAux;
	
	@Column(name = "anio", nullable = false)
	private Integer anio;
	
	@Column(name = "mes", nullable = false)
	private Integer mes;
	
	@Column(name = "presupuesto")
	private Double presupuesto;

	public Long getIdPresupuestoPorMes() {
		return idPresupuestoPorMes;
	}

	public void setIdPresupuestoPorMes(Long idPresupuestoPorMes) {
		this.idPresupuestoPorMes = idPresupuestoPorMes;
	}

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
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

	public Long getIdProyectoAux() {
		return idProyectoAux;
	}

	public void setIdProyectoAux(Long idProyectoAux) {
		this.idProyectoAux = idProyectoAux;
	}
}
