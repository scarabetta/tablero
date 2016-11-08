package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "indicador_estrategico")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idIndicadorEstrategico", "objetivoJurisdiccional", "proyectos", "metas", "nombre", "descripcion", "semaforizaciones",
		"metodoCalculo", "tipoIndicador", "sentido", "frecuencia", "formatoNumero", "unidadDeMedida", "estado", "medicionesHistoricas" })

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
	private ObjetivoJurisdiccional objetivoJurisdiccional;
	
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "proyecto_por_indicador_estrategico", joinColumns = { @JoinColumn(name = "idproyecto") }, 
		inverseJoinColumns = { @JoinColumn(name = "idindicadorestrategico") })
	@XmlElement(name = "proyectos")
	private List<Proyecto> proyectos;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "indicadorEstrategico",fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "metas")
	@JsonManagedReference
    private List<Meta> metas = new ArrayList<Meta>();
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "indicadorEstrategico",fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "medicionesHistoricas")
	@JsonManagedReference
    private List<MedicionHistorica> medicionesHistoricas = new ArrayList<MedicionHistorica>();
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "indicadorEstrategico",fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "semaforizaciones")
	@JsonManagedReference
    private List<Semaforizacion> semaforizaciones = new ArrayList<Semaforizacion>();
		
	@Column(name = "indicador")
	private String nombre;

	@Column(name = "descripcion")
	private String descripcion;
	
	@Column(name = "metodocalculo")
	private String metodoCalculo;
	
	@Column(name = "tipoindicador")
	private String tipoIndicador;
	
	@Column(name = "sentido")
	private String sentido;
	
	@Column(name = "frecuencia")
	private String frecuencia;
	
	@Column(name = "formatonumero")
	private String formatoNumero;
	
	@Column(name = "unidaddemedida")
	private String unidadDeMedida;
	
	@Column(name = "estado", nullable = false)
	private String estado;

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

	public String getMetodoCalculo() {
		return metodoCalculo;
	}

	public void setMetodoCalculo(String metodoCalculo) {
		this.metodoCalculo = metodoCalculo;
	}

	public String getTipoIndicador() {
		return tipoIndicador;
	}

	public void setTipoIndicador(String tipoIndicador) {
		this.tipoIndicador = tipoIndicador;
	}

	public String getSentido() {
		return sentido;
	}

	public void setSentido(String sentido) {
		this.sentido = sentido;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getFormatoNumero() {
		return formatoNumero;
	}

	public void setFormatoNumero(String formatoNumero) {
		this.formatoNumero = formatoNumero;
	}

	public String getUnidadDeMedida() {
		return unidadDeMedida;
	}

	public void setUnidadDeMedida(String unidadDeMedida) {
		this.unidadDeMedida = unidadDeMedida;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public List<Meta> getMetas() {
		if(metas == null){
			metas = new ArrayList<Meta>();
		}
		return metas;
	}

	public void setMetas(List<Meta> metas) {
		if(this.metas == null){
			this.metas = metas;
		} else if(metas != null){
			this.metas.clear();
			this.metas.addAll(metas);
		}
	}

	public List<MedicionHistorica> getMedicionesHistoricas() {
		if(medicionesHistoricas == null){
			medicionesHistoricas = new ArrayList<MedicionHistorica>();
		}
		return medicionesHistoricas;
	}

	public void setMedicionesHistoricas(List<MedicionHistorica> medicionesHistoricas) {
		if(this.medicionesHistoricas == null){
			this.medicionesHistoricas = medicionesHistoricas;
		} else if(medicionesHistoricas != null){
			this.medicionesHistoricas.clear();
			this.medicionesHistoricas.addAll(medicionesHistoricas);
		}
	}

	public List<Semaforizacion> getSemaforizaciones() {
		if(semaforizaciones == null){
			semaforizaciones = new ArrayList<Semaforizacion>();
		}
		return semaforizaciones;
	}

	public void setSemaforizaciones(List<Semaforizacion> semaforizaciones) {
		if(this.semaforizaciones == null){
			this.semaforizaciones = semaforizaciones;
		} else if(semaforizaciones != null){
			this.semaforizaciones.clear();
			this.semaforizaciones.addAll(semaforizaciones);
		}
	}

	public List<Proyecto> getProyectos() {
		if(proyectos == null){
			proyectos = new ArrayList<Proyecto>();
		}
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}
}
