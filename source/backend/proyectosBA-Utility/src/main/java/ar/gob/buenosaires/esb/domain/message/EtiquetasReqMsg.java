package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id"})
@XmlRootElement(name = "EtiquetasReqMsg")
public class EtiquetasReqMsg extends EsbBaseMsg {
	
    public static final String ETIQUETAS_TYPE = "EtiquetasReqMsg";
    
    private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	

	@Override
	public String getEventType() {
		return ETIQUETAS_TYPE;
	}
}
