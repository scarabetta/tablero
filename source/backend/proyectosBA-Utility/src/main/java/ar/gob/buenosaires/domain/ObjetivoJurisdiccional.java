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
@Table(name = "objetivo_jurisdiccional")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idObjetivoJurisdiccional", "jurisdiccion", "codigo", "nombre", "objetivosOperativos", "indicadoresEstrategicos",
		"idJurisdiccionAux" })

@XmlRootElement(name = "ObjetivoJurisdiccional")
public class ObjetivoJurisdiccional implements Serializable {

	private static final long serialVersionUID = -5903950670629696522L;

	@Id 	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idobjetivojurisdiccional", nullable = false)
	private Long idObjetivoJurisdiccional;	
	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idjurisdiccion")
	@JsonBackReference
//	@XmlTransient
	private Jurisdiccion jurisdiccion;
	
	@Column(name="codigo", nullable = true)
    private String codigo;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "objetivoJurisdiccional",fetch = FetchType.LAZY)
	@XmlElement(name = "objetivosOperativos")
	@JsonManagedReference
    private List<ObjetivoOperativo> objetivosOperativos = new ArrayList<>();
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "objetivoJurisdiccional", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "indicadoresEstrategicos")
	@JsonManagedReference
    private List<IndicadorEstrategico> indicadoresEstrategicos = new ArrayList<IndicadorEstrategico>();
	
	@Column(name = "idjurisdiccionaux", nullable = false)
	private long idJurisdiccionAux;

	public Long getIdObjetivoJurisdiccional() {
		return idObjetivoJurisdiccional;
	}

	public void setIdObjetivoJurisdiccional(Long idObjetivoJurisdiccional) {
		this.idObjetivoJurisdiccional = idObjetivoJurisdiccional;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}	
	
//	@XmlTransient
    public Jurisdiccion getJurisdiccion() {
        return jurisdiccion;
    }
    
	public void setJurisdiccion(Jurisdiccion jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	public List<ObjetivoOperativo> getObjetivosOperativos() {
		if(objetivosOperativos == null){
			objetivosOperativos = new ArrayList<ObjetivoOperativo>();
		}
		return objetivosOperativos;
	}

	public void setObjetivosOperativos(List<ObjetivoOperativo> objetivosOperativos) {
		this.objetivosOperativos = objetivosOperativos;
	}

	public List<IndicadorEstrategico> getIndicadoresEstrategicos() {
		if(indicadoresEstrategicos == null){
			indicadoresEstrategicos = new ArrayList<IndicadorEstrategico>();
		}
		return indicadoresEstrategicos;
	}

	public void setIndicadoresEstrategicos(
			List<IndicadorEstrategico> indicadoresEstrategicos) {
		if(this.indicadoresEstrategicos == null){
			this.indicadoresEstrategicos = indicadoresEstrategicos;
		} else if (indicadoresEstrategicos != null){
			this.indicadoresEstrategicos.clear();
			this.indicadoresEstrategicos.addAll(indicadoresEstrategicos);
		}
	}

	public long getIdJurisdiccionAux() {
		return idJurisdiccionAux;
	}

	public void setIdJurisdiccionAux(long idJurisdiccionAux) {
		this.idJurisdiccionAux = idJurisdiccionAux;
	}
	
}
