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
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.XmlType;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "objetivo_operativo")

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idObjetivoOperativo", "codigo", "nombre", "proyectos", "idObjetivoJurisdiccionalAux"})

@XmlRootElement(name = "ObjetivoOperativo")
public class ObjetivoOperativo implements Serializable {

	private static final long serialVersionUID = -2797022741873121718L;

	@Id 	
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idobjetivooperativo", nullable = false)
	private Long idObjetivoOperativo;	
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "idobjetivojurisdiccional")
	@JsonBackReference
	@XmlTransient
	private ObjetivoJurisdiccional objetivoJurisdiccional;
	
	@OneToMany(cascade = CascadeType.ALL,mappedBy = "objetivoOperativo",fetch = FetchType.LAZY)
	@XmlElement(name = "proyectos")
	@JsonManagedReference
    private List<Proyecto> proyectos = new ArrayList<>();
	
	@Column(name="codigo", nullable = true)
    private String codigo;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@Column(name = "idobjetivojurisdiccionalaux", nullable = false)
	private long idObjetivoJurisdiccionalAux;

	public Long getIdObjetivoOperativo() {
		return idObjetivoOperativo;
	}

	public void setIdObjetivoOperativo(Long idObjetivoOperativo) {
		this.idObjetivoOperativo = idObjetivoOperativo;
	}

	@XmlTransient
	public ObjetivoJurisdiccional getObjetivoJurisdiccional() {
		return objetivoJurisdiccional;
	}

	public void setObjetivoJurisdiccional(ObjetivoJurisdiccional objetivoJurisdiccional) {
		this.objetivoJurisdiccional = objetivoJurisdiccional;
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
	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		this.proyectos = proyectos;
	}

	public long getIdObjetivoJurisdiccionalAux() {
		return idObjetivoJurisdiccionalAux;
	}

	public void setIdObjetivoJurisdiccionalAux(long idObjetivoJurisdiccionalAux) {
		this.idObjetivoJurisdiccionalAux = idObjetivoJurisdiccionalAux;
	}	
}
