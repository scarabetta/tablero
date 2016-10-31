package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.TipoObra;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "tipoObra" })
@XmlRootElement(name = "TipoObraReqMsg")
public class TipoObraReqMsg extends EsbBaseMsg {
	
    public static final String TIPO_OBRA_TYPE =
            "TipoObraReqMsg";
    
    private Long id;
    private TipoObra tipoObra;

	@Override
	public String getEventType() {
		return TIPO_OBRA_TYPE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public TipoObra getTipoObra() {
		return tipoObra;
	}

	public void setTipoObra(TipoObra tipoObra) {
		this.tipoObra = tipoObra;
	}
}
