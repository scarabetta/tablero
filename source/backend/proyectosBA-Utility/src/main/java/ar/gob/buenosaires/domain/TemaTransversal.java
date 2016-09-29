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

import com.fasterxml.jackson.annotation.JsonIgnore;

import ar.gob.buenosaires.exportador.IExportableAExcel;

@Entity
@Table(name = "tema_transversal")
@Audited
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "idTemaTransversal", "temaTransversal", "activo" })
@XmlRootElement(name = "TemaTransversal")
public class TemaTransversal implements Serializable, IExportableAExcel {

	private static final long serialVersionUID = 6439495534306422608L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idtematransversal")
	private Long idTemaTransversal;

	@Column(name = "tematransversal", nullable = false)
	private String temaTransversal;

	@Column(name = "activo", nullable = false)
	private boolean activo;

	public Long getIdTemaTransversal() {
		return idTemaTransversal;
	}

	public void setIdTemaTransversal(Long idTemaTransversal) {
		this.idTemaTransversal = idTemaTransversal;
	}

	public String getTemaTransversal() {
		return temaTransversal;
	}

	public void setTemaTransversal(String temaTransversal) {
		this.temaTransversal = temaTransversal;
	}

	public boolean isActivo() {
		return activo;
	}

	public void setActivo(boolean activo) {
		this.activo = activo;
	}

	@JsonIgnore
	@Override
	public String getNombreParaExportacion() {
		return "TT:" + getTemaTransversal();
	}

}
