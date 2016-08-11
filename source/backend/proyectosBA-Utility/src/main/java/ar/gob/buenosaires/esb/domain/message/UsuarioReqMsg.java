package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "email", "usuario" })
@XmlRootElement(name = "UsuarioReqMsg")
public class UsuarioReqMsg extends EsbBaseMsg {

    public static final String USUARIO_TYPE =
            "UsuarioReqMsg";
    
    private Long id;
    private String email;
    private Usuario usuario;
	
	@Override
	public String getEventType() {
		return USUARIO_TYPE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
