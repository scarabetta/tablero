package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.EtiquetaResponse;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "etiquetas" })
@XmlRootElement(name = "EtiquetasRespMsg")
public class EtiquetasRespMsg extends EsbBaseMsg {

    public static final String ETIQUETAS_TYPE = "EtiquetasRespMsg";
        
    private EtiquetaResponse etiquetas; 
    	
	public EtiquetaResponse getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(EtiquetaResponse etiquetas) {
		this.etiquetas = etiquetas;
	}

	@Override
	public String getEventType() {
		return ETIQUETAS_TYPE;
	}

}
