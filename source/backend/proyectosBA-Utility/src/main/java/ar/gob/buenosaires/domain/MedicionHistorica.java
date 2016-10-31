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
@Table(name = "medicion_historica_indicador")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idMedicionHistoricaIndicador", "indicadorEstrategico", "anio", "medicion"})

@XmlRootElement(name = "MedicionHistorica")
public class MedicionHistorica implements Serializable {

	private static final long serialVersionUID = -5345951136538436405L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idmedicionhistoricaindicador", nullable = false)
	private Long idMedicionHistoricaIndicador;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idindicadorestrategico", nullable = false)
	@JsonBackReference
	@XmlElement(name = "indicadorEstrategico")
	private IndicadorEstrategico indicadorEstrategico;

	@Column(name = "anio", nullable = false)
	private Integer anio;
	
	@Column(name = "medicion")
	private String medicion;

	public Long getIdMedicionHistoricaIndicador() {
		return idMedicionHistoricaIndicador;
	}

	public void setIdMedicionHistoricaIndicador(Long idMedicionHistoricaIndicador) {
		this.idMedicionHistoricaIndicador = idMedicionHistoricaIndicador;
	}

	public IndicadorEstrategico getIndicadorEstrategico() {
		return indicadorEstrategico;
	}

	public void setIndicadorEstrategico(IndicadorEstrategico indicadorEstrategico) {
		this.indicadorEstrategico = indicadorEstrategico;
	}

	public Integer getAnio() {
		return anio;
	}

	public void setAnio(Integer anio) {
		this.anio = anio;
	}

	public String getMedicion() {
		return medicion;
	}

	public void setMedicion(String medicion) {
		this.medicion = medicion;
	}
}
