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
@Table(name = "indicador_semaforo")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idIndicadorSemaforo", "indicadorEstrategico", "anio", "mes", "valor" })
@XmlRootElement(name = "Semaforizacion")
public class Semaforizacion implements Serializable {

	private static final long serialVersionUID = -6378511233861936891L;

	@Id 	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idindicadorsemaforo", nullable = false)
	private Long idIndicadorSemaforo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idindicadorestrategico", nullable = false)
	@JsonBackReference
	@XmlElement(name = "indicadorEstrategico")
	private IndicadorEstrategico indicadorEstrategico;
	
	@Column(name = "anio", nullable = false)
	private Integer anio;
	
	@Column(name = "mes", nullable = false)
	private Integer mes;
	
	@Column(name = "valor")
	private String valor;

	public Long getIdIndicadorSemaforo() {
		return idIndicadorSemaforo;
	}

	public void setIdIndicadorSemaforo(Long idIndicadorSemaforo) {
		this.idIndicadorSemaforo = idIndicadorSemaforo;
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

	public Integer getMes() {
		return mes;
	}

	public void setMes(Integer mes) {
		this.mes = mes;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
