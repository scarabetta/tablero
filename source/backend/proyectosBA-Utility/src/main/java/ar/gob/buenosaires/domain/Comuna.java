package ar.gob.buenosaires.domain;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.exportador.IExportableAExcel;

@Entity
@Table(name = "comuna")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idComuna", "abreviatura", "nombre", "proyectosComuna" })
public class Comuna implements Serializable, IExportableAExcel {

	private static final long serialVersionUID = 6040466436345783676L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcomuna", nullable = false)
	private Long idComuna;

	@Column(name = "abreviatura", nullable = false)
	private String abreviatura;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "comuna_por_proyecto", joinColumns = { @JoinColumn(name = "idproyecto") }, inverseJoinColumns = {
			@JoinColumn(name = "idcomuna") })
	@XmlElement(name = "proyectosComuna")
	private List<Proyecto> proyectosComuna;

	public Long getIdComuna() {
		return idComuna;
	}

	public void setIdComuna(Long idComuna) {
		this.idComuna = idComuna;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String getNombreParaExportacion() {
		return "COM:" + getNombre();
	}
}
