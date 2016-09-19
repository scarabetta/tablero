package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "jurisdiccion", "codigo", "usuario" })
@XmlRootElement(name = "JurisdiccionReqMsg")
public class JurisdiccionReqMsg extends EsbBaseMsg {
	
    public static final String JURISDICCION_TYPE =
            "JurisdiccionReqMsg";
    
    private Long id;
    private String name;
    private Jurisdiccion jurisdiccion;
    private String codigo;
    private Usuario usuario;

	@Override
	public String getEventType() {
		return JURISDICCION_TYPE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Jurisdiccion getJurisdiccion() {
		return jurisdiccion;
	}

	public void setJurisdiccion(Jurisdiccion jurisdiccion) {
		this.jurisdiccion = jurisdiccion;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
}
