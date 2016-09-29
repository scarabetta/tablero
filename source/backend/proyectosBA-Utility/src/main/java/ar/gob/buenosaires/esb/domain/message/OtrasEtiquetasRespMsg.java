package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "otrasEtiquetas" })
@XmlRootElement(name = "OtrasEtiquetasRespMsg")
public class OtrasEtiquetasRespMsg extends EsbBaseMsg {
	
    public static final String OTRAS_ETIQUETAS_TYPE = "OtrasEtiquetasRespMsg";
    
    private List<OtraEtiqueta> otrasEtiquetas;
                    		
	public List<OtraEtiqueta> getOtrasEtiquetas() {
		if (otrasEtiquetas == null) {
			otrasEtiquetas = new ArrayList<>();
		}
		return otrasEtiquetas;
	}

	public void setOtrasEtiquetas(List<OtraEtiqueta> otrasEtiquetas) {
		this.otrasEtiquetas = otrasEtiquetas;
	}

	@Override
	public String getEventType() {
		return OTRAS_ETIQUETAS_TYPE;
	}
}
