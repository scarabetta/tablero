package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "otraEtiqueta" })
@XmlRootElement(name = "OtraEtiquetaReqMsg")
public class OtrasEtiquetasReqMsg extends EsbBaseMsg {
	
    public static final String OTRAS_ETIQUETAS_TYPE = "OtrasEtiquetasReqMsg";
    
    private Long id;
    private OtraEtiqueta otraEtiqueta;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
	
	public OtraEtiqueta getOtraEtiqueta() {
		return otraEtiqueta;
	}

	public void setOtraEtiqueta(OtraEtiqueta otraEtiqueta) {
		this.otraEtiqueta = otraEtiqueta;
	}

	@Override
	public String getEventType() {
		return OTRAS_ETIQUETAS_TYPE;
	}
}
