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
@Table(name = "presupuesto_por_anio")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idPresupuestoPorAnio", "anio", "presupuesto", "otrasFuentes" })

@XmlRootElement(name = "PresupuestoPorAnio")
public class PresupuestoPorAnio implements Serializable {

	private static final long serialVersionUID = -1770846664731989725L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idpresupuestoporanio", nullable = false)
	private Long idPresupuestoPorAnio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproyecto")
	@JsonBackReference(value = "proy-presu")
	@XmlTransient
	private Proyecto proyectoPresupuestoPorAnio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproyecto", insertable = false, updatable = false)
	@JsonBackReference(value = "exp-presu")
	@XmlTransient
	private ExportacionProyectoView exportacionProyectoViewPresupuestoPorAnio;

	@Column(name = "anio")
	private int anio;

	@Column(name = "presupuesto")
	private Double presupuesto;

	@Column(name = "otrasfuentes")
	private Double otrasFuentes;

	public Long getIdPresupuestoPorAnio() {
		return idPresupuestoPorAnio;
	}

	public void setIdPresupuestoPorAnio(Long idPresupuestoPorAnio) {
		this.idPresupuestoPorAnio = idPresupuestoPorAnio;
	}

	public Proyecto getProyecto() {
		return proyectoPresupuestoPorAnio;
	}

	public void setProyecto(Proyecto proyectoPresupuestoPorAnio) {
		this.proyectoPresupuestoPorAnio = proyectoPresupuestoPorAnio;
	}

	public int getAnio() {
		return anio;
	}

	public void setAnio(int anio) {
		this.anio = anio;
	}

	public Double getPresupuesto() {
		return presupuesto;
	}

	public void setPresupuesto(Double presupuesto) {
		this.presupuesto = presupuesto;
	}

	public Double getOtrasFuentes() {
		return otrasFuentes;
	}

	public void setOtrasFuentes(Double otrasFuentes) {
		this.otrasFuentes = otrasFuentes;
	}

	/**
	 * @return the exportacionProyectoViewPresupuestoPorAnio
	 */
	public ExportacionProyectoView getExportacionProyectoViewPresupuestoPorAnio() {
		return exportacionProyectoViewPresupuestoPorAnio;
	}

	/**
	 * @param exportacionProyectoViewPresupuestoPorAnio the exportacionProyectoViewPresupuestoPorAnio to set
	 */
	public void setExportacionProyectoViewPresupuestoPorAnio(ExportacionProyectoView exportacionProyectoViewPresupuestoPorAnio) {
		this.exportacionProyectoViewPresupuestoPorAnio = exportacionProyectoViewPresupuestoPorAnio;
	}
}