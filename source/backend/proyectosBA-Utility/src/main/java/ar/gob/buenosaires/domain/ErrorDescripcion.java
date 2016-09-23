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


@Entity
@Table(name = "error_descripcion")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "codigoError", "descripcionError" })
@XmlRootElement(name = "ErrorDescripcion")
public class ErrorDescripcion implements Serializable {

	private static final long serialVersionUID = 8640076807579383938L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "codigo_error")
	private Long codigoError;

	@Column(name = "descripcion_error", nullable = false)
	private String descripcionError;

	public Long getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(Long codigoError) {
		this.codigoError = codigoError;
	}

	public String getDescripcionError() {
		return descripcionError;
	}

	public void setDescripcionError(String descripcionError) {
		this.descripcionError = descripcionError;
	}	
}
