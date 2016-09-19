package ar.gob.buenosaires.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.exportador.IExportableAExcel;

@Entity
@Table(name = "otras_etiquetas")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idEtiqueta", "etiqueta" })
@XmlRootElement(name = "OtraEtiqueta")
public class OtraEtiqueta implements Serializable {

	private static final long serialVersionUID = 3013803033304092256L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idetiqueta")
	private Long idEtiqueta;

	@Column(name = "etiqueta", nullable = false)
	private String etiqueta;

	public Long getIdEtiqueta() {
		return idEtiqueta;
	}

	public void setIdEtiqueta(Long idEtiqueta) {
		this.idEtiqueta = idEtiqueta;
	}

	public String getEtiqueta() {
		return etiqueta;
	}

	public void setEtiqueta(String etiqueta) {
		this.etiqueta = etiqueta;
	}		

}
