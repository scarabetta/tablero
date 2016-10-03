package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.domain.UsuarioResumen;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "usuarios", "usuariosResumen" })
@XmlRootElement(name = "UsuarioRespMsg")
public class UsuarioRespMsg extends EsbBaseMsg {

    public static final String USUARIO_TYPE = "UsuarioRespMsg";
    
    @XmlElement(name = "usuarios")
    private List<Usuario> usuarios;
    
    @XmlElement(name = "usuariosResumen")
    private List<UsuarioResumen> usuariosResumen;
    
	public List<Usuario> getUsuarios() {
		if (usuarios == null) {
			usuarios = new ArrayList<Usuario>();
		}
		return usuarios;
	}

	public void setUsuarios(List<Usuario> usuarios) {
		this.usuarios = usuarios;
	}

	public List<UsuarioResumen> getUsuariosResumen() {
		if(usuariosResumen == null){
			usuariosResumen = new ArrayList<UsuarioResumen> ();
		}
		return usuariosResumen;
	}

	public void setUsuariosResumen(List<UsuarioResumen> usuariosResumen) {
		this.usuariosResumen = usuariosResumen;
	}

	@Override
	public String getEventType() {
		return USUARIO_TYPE;
	}

}
