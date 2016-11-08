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
import org.hibernate.envers.NotAudited;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "presupuesto_por_anio")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idPresupuestoPorAnio", "proyecto", "anio", "presupuesto", "otrasFuentes",
		"exportacionProyectoViewPresupuestoPorAnio", "reporteProyectosViewPresupuestoPorAnio" })

@XmlRootElement(name = "PresupuestoPorAnio")
public class PresupuestoPorAnio implements Serializable {

	private static final long serialVersionUID = -1770846664731989725L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idpresupuestoporanio", nullable = false)
	private Long idPresupuestoPorAnio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproyecto")
	@JsonBackReference(value = "proy-presu")
	// @XmlTransient
	@XmlElement(name = "proyecto")
	private Proyecto proyecto;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproyecto", insertable = false, updatable = false)
	@NotAudited
	@JsonBackReference(value = "exp-presu")
	// @XmlTransient
	@XmlElement(name = "exportacionProyectoViewPresupuestoPorAnio")
	private ExportacionProyectoView exportacionProyectoViewPresupuestoPorAnio;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idproyecto", insertable = false, updatable = false)
	@NotAudited
	@JsonBackReference(value = "rep-presu")
	// @XmlTransient
	@XmlElement(name = "reporteProyectosViewPresupuestoPorAnio")
	private ReporteProyectosView reporteProyectosViewPresupuestoPorAnio;

	@Column(name = "anio")
	private Integer anio;

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
		return proyecto;
	}

	public void setProyecto(Proyecto proyectoPresupuestoPorAnio) {
		this.proyecto = proyectoPresupuestoPorAnio;
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

	public Double getOtrasFuentes() {
		if(otrasFuentes == null){
			otrasFuentes = new Double(0);
		}
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
	 * @param exportacionProyectoViewPresupuestoPorAnio
	 *            the exportacionProyectoViewPresupuestoPorAnio to set
	 */
	public void setExportacionProyectoViewPresupuestoPorAnio(
			ExportacionProyectoView exportacionProyectoViewPresupuestoPorAnio) {
		this.exportacionProyectoViewPresupuestoPorAnio = exportacionProyectoViewPresupuestoPorAnio;
	}
}