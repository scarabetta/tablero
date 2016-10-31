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
@Table(name = "subtipo_obra")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idSubtipoObra", "tipoObra", "nombre", "obras"})
@XmlRootElement(name = "SubtipoObra")
public class SubtipoObra implements Serializable {

	private static final long serialVersionUID = 3748822334520176989L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idsubtipoobra", nullable = false)
	private Long idSubtipoObra;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idtipoobra")
	@JsonBackReference
	@XmlElement(name = "tipoObra")
	private TipoObra tipoObra;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "subtipoObra", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "obras")
	@JsonManagedReference(value = "subipo-obra")
	private List<Obra> obras = new ArrayList<Obra>();

	public Long getIdSubtipoObra() {
		return idSubtipoObra;
	}

	public void setIdSubtipoObra(Long idSubtipoObra) {
		this.idSubtipoObra = idSubtipoObra;
	}

	public TipoObra getTipoObra() {
		return tipoObra;
	}

	public void setTipoObra(TipoObra tipoObra) {
		this.tipoObra = tipoObra;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<Obra> getObras() {
		if(obras == null){
			obras = new ArrayList<Obra>();
		}
		return obras;
	}

	public void setObras(List<Obra> obras) {
		if(this.obras == null){
			this.obras = obras;
		} else if(obras != null){
			this.obras.clear();
			this.obras.addAll(obras);
		}
	}
}
