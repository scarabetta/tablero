package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id"})
@XmlRootElement(name = "ErroresReqMsg")
public class ErroresReqMsg extends EsbBaseMsg {
	
    public static final String ERRORES_TYPE = "ErroresReqMsg";
    
    private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	@Override
	public String getEventType() {
		return ERRORES_TYPE;
	}
}
