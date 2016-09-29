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
import javax.xml.bind.annotation.XmlType;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Entity
@Table(name = "indicador_estrategico")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idIndicadorEstrategico", "objetivoJurisdiccional", "nombre", "descripcion" })

@XmlRootElement(name = "IndicadorEstrategico")
public class IndicadorEstrategico implements Serializable {
	
	private static final long serialVersionUID = -1770846664731989725L;

	@Id 	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idindicadorestrategico", nullable = false)
	private Long idIndicadorEstrategico;

	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idobjetivojurisdiccional")
	@JsonBackReference
//	@XmlTransient
	private ObjetivoJurisdiccional objetivoJurisdiccional;
		
	@Column(name = "indicador")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;

	public Long getIdIndicadorEstrategico() {
		return idIndicadorEstrategico;
	}

	public void setIdIndicadorEstrategico(Long idIndicadorEstrategico) {
		this.idIndicadorEstrategico = idIndicadorEstrategico;
	}

	public ObjetivoJurisdiccional getObjetivoJurisdiccional() {
		return objetivoJurisdiccional;
	}

	public void setObjetivoJurisdiccional(
			ObjetivoJurisdiccional objetivoJurisdiccional) {
		this.objetivoJurisdiccional = objetivoJurisdiccional;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}		
}
