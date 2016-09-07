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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.exportador.IExportableAExcel;

@Entity
@Table(name = "eje_de_gobierno")
@XmlType(propOrder = { "idEjeDeGobierno", "nombre", "descripcion", "ejemplos", "proyectosEjeDeGobierno" })
public class EjeDeGobierno implements Serializable, IExportableAExcel  {

	private static final long serialVersionUID = -3297460062606797267L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name = "idejedegobierno", nullable = false)
	private Long idEjeDeGobierno;

	@Column(name = "nombre", nullable = false)
	private String nombre;

	@Column(name = "descripcion", nullable = false)
	private String descripcion;

	@Column(name = "ejemplos", nullable = false)
	private String ejemplos;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "eje_de_gobierno_por_proyecto",
	joinColumns = { @JoinColumn(name = "id_proyecto") },
	inverseJoinColumns = { @JoinColumn(name = "id_ejedegobierno") })
	@XmlElement(name = "proyectosEjeDeGobierno")
	private List<Proyecto> proyectosEjeDeGobierno;

	public Long getIdEjeDeGobierno() {
		return idEjeDeGobierno;
	}

	public void setIdEjeDeGobierno(Long idEjeDeGobierno) {
		this.idEjeDeGobierno = idEjeDeGobierno;
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

	public String getEjemplos() {
		return ejemplos;
	}

	public void setEjemplos(String ejemplos) {
		this.ejemplos = ejemplos;
	}

	@Override
	public String getNombreParaExportacion() {
		return "EG:" + getNombre();
	}

}
