package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "obra" })
@XmlRootElement(name = "ObraReqMsg")
public class ObraReqMsg extends EsbBaseMsg {
	
    public static final String OBRA_TYPE =
            "ObraReqMsg";
    
    private Long id;
    private Obra obra;
    
	@Override
	public String getEventType() {
		return OBRA_TYPE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Obra getObra() {
		return obra;
	}

	public void setObra(Obra obra) {
		this.obra = obra;
	}
}
