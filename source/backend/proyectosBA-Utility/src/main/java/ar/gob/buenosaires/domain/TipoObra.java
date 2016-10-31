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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.hibernate.envers.Audited;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "tipo_obra")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idTipoObra", "nombre", "subtiposObra"})
@XmlRootElement(name = "TipoObra")
public class TipoObra implements Serializable {

	private static final long serialVersionUID = 5796712585561387644L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idtipoobra", nullable = false)
	private Long idTipoObra;
	
	@Column(name = "nombre", nullable = false)
	private String nombre;
	
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "tipoObra", fetch = FetchType.LAZY, orphanRemoval = true)
	@XmlElement(name = "subtiposObra")
	@JsonManagedReference
	private List<SubtipoObra> subtiposObra = new ArrayList<SubtipoObra>();

	public Long getIdTipoObra() {
		return idTipoObra;
	}

	public void setIdTipoObra(Long idTipoObra) {
		this.idTipoObra = idTipoObra;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public List<SubtipoObra> getSubtiposObra() {
		if(subtiposObra == null){
			subtiposObra = new ArrayList<SubtipoObra>();
		}
		return subtiposObra;
	}

	public void setSubtiposObra(List<SubtipoObra> subtiposObra) {
		if(this.subtiposObra == null){
			this.subtiposObra = subtiposObra;
		} else if(subtiposObra != null){
			this.subtiposObra.clear();
			this.subtiposObra.addAll(subtiposObra);
		}
	}
}
