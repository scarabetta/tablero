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

import org.hibernate.envers.Audited;

@Entity
@Table(name = "compromiso_publico")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idCompromisoPublico", "compromisoPublico", "activo" })
@XmlRootElement(name = "CompromisoPublico")
public class CompromisoPublico implements Serializable {

	private static final long serialVersionUID = -1801238225260107944L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcompromisopublico")
	private Long idCompromisoPublico;

	@Column(name = "compromisopublico", nullable = false)
	private String compromisoPublico;

	@Column(name = "activo", nullable = false)
	private boolean activo;

	public Long getIdCompromisoPublico() {
		return idCompromisoPublico;
	}

	public void setIdCompromisoPublico(Long idCompromisoPublico) {
		this.idCompromisoPublico = idCompromisoPublico;
	}

	public String getCompromisoPublico() {
		return compromisoPublico;
	}

	public void setCompromisoPublico(String compromisoPublico) {
		this.compromisoPublico = compromisoPublico;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}			

}
