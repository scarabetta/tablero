package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "temaTransversal" })
@XmlRootElement(name = "TemaTransversalReqMsg")
public class TemaTransversalReqMsg extends EsbBaseMsg {
	
    public static final String TEMA_TRANSVERSAL_TYPE = "TemaTransversalReqMsg";
    
    private Long id;
    private TemaTransversal temaTransversal;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public TemaTransversal getTemaTransversal() {
		return temaTransversal;
	}

	public void setTemaTransversal(TemaTransversal temaTransversal) {
		this.temaTransversal = temaTransversal;
	}

	@Override
	public String getEventType() {
		return TEMA_TRANSVERSAL_TYPE;
	}
}
