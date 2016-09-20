package ar.gob.buenosaires.esb.domain.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "compromisosPublicos" })
@XmlRootElement(name = "CompromisoPublicoRespMsg")
public class CompromisoPublicoRespMsg extends EsbBaseMsg {
	
    public static final String COMPROMISO_PUBLICO_TYPE = "CompromisoPublicoRespMsg";
    
    private List<CompromisoPublico> compromisosPublicos;
                    		
	public List<CompromisoPublico> getCompromisosPublicos() {
		return compromisosPublicos;
	}

	public void setCompromisosPublicos(List<CompromisoPublico> compromisosPublicos) {
		this.compromisosPublicos = compromisosPublicos;
	}

	@Override
	public String getEventType() {
		return COMPROMISO_PUBLICO_TYPE;
	}
}
