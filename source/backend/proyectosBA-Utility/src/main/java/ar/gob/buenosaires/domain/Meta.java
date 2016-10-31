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
@Table(name = "meta_indicador")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idMeta", "indicadorEstrategico", "anio", "meta", "origen", "referente", "metodoRecoleccion",
		"sistemaRecoleccion", "pesoRelativo", "justificacionParametorIntern", "justificacionParametroNac", "justificacionResultadoHistorico",
		"justificacionPresupuesto", "justificacionInstituciones"})
@XmlRootElement(name = "Meta")
public class Meta implements Serializable {

	private static final long serialVersionUID = 7275963383084927795L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmetaindicador", nullable = false)
	private Long idMeta;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idindicadorestrategico", nullable = false)
	@JsonBackReference
	@XmlElement(name = "indicadorEstrategico")
	private IndicadorEstrategico indicadorEstrategico;

	@Column(name = "anio", nullable = false)
	private Integer anio;
	
	@Column(name = "meta")
	private String meta;

	@Column(name = "origen")
	private String origen;

	@Column(name = "referente")
	private String referente;

	@Column(name = "metodorecoleccion")
	private String metodoRecoleccion;

	@Column(name = "sistemarecoleccion")
	private String sistemaRecoleccion;

	@Column(name = "pesorelativo")
	private Integer pesoRelativo;
	
	@Column(name = "justificacionparametrointernacional")
	private Integer justificacionParametorIntern;
	
	@Column(name = "justificacionparametronacional")
	private Integer justificacionParametroNac;
	
	@Column(name = "justificacionresultadoshistoricos")
	private Integer justificacionResultadoHistorico;
	
	@Column(name = "justificacionpresupuesto")
	private Integer justificacionPresupuesto;
	
	@Column(name = "justificacioninstituciones")
	private Integer justificacionInstituciones;

	public Long getIdMeta() {
		return idMeta;
	}

	public void setIdMeta(Long idMeta) {
		this.idMeta = idMeta;
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

	public String getMeta() {
		return meta;
	}

	public void setMeta(String meta) {
		this.meta = meta;
	}

	public String getOrigen() {
		return origen;
	}

	public void setOrigen(String origen) {
		this.origen = origen;
	}

	public String getReferente() {
		return referente;
	}

	public void setReferente(String referente) {
		this.referente = referente;
	}

	public String getMetodoRecoleccion() {
		return metodoRecoleccion;
	}

	public void setMetodoRecoleccion(String metodoRecoleccion) {
		this.metodoRecoleccion = metodoRecoleccion;
	}

	public String getSistemaRecoleccion() {
		return sistemaRecoleccion;
	}

	public void setSistemaRecoleccion(String sistemaRecoleccion) {
		this.sistemaRecoleccion = sistemaRecoleccion;
	}

	public Integer getPesoRelativo() {
		return pesoRelativo;
	}

	public void setPesoRelativo(Integer pesoRelativo) {
		this.pesoRelativo = pesoRelativo;
	}

	public Integer getJustificacionParametorIntern() {
		return justificacionParametorIntern;
	}

	public void setJustificacionParametorIntern(Integer justificacionParametorIntern) {
		this.justificacionParametorIntern = justificacionParametorIntern;
	}

	public Integer getJustificacionParametroNac() {
		return justificacionParametroNac;
	}

	public void setJustificacionParametroNac(Integer justificacionParametroNac) {
		this.justificacionParametroNac = justificacionParametroNac;
	}

	public Integer getJustificacionResultadoHistorico() {
		return justificacionResultadoHistorico;
	}

	public void setJustificacionResultadoHistorico(Integer justificacionResultadoHistorico) {
		this.justificacionResultadoHistorico = justificacionResultadoHistorico;
	}

	public Integer getJustificacionPresupuesto() {
		return justificacionPresupuesto;
	}

	public void setJustificacionPresupuesto(Integer justificacionPresupuesto) {
		this.justificacionPresupuesto = justificacionPresupuesto;
	}

	public Integer getJustificacionInstituciones() {
		return justificacionInstituciones;
	}

	public void setJustificacionInstituciones(Integer justificacionInstituciones) {
		this.justificacionInstituciones = justificacionInstituciones;
	}
}
