package ar.gob.buenosaires.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name = "rol_por_usuario")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "rol", "usuario" })
@XmlRootElement(name = "RolPorUsuario")
public class RolPorUsuario implements Serializable{

	private static final long serialVersionUID = -2891869888185331573L;

	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "rol_idrol")
	private Rol rol;
	
	@Id
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "usuario_idusuario")
	private Usuario usuario;

	public Rol getRol() {
		return rol;
	}

	public void setRol(Rol rol) {
		this.rol = rol;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
