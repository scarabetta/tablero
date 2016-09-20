package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "compromisoPublico" })
@XmlRootElement(name = "CompromisoPublicoReqMsg")
public class CompromisoPublicoReqMsg extends EsbBaseMsg {
	
    public static final String COMPROMISO_PUBLICO_TYPE = "CompromisoPublicoReqMsg";
    
    private Long id;
    private CompromisoPublico compromisoPublico;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}	
		
	public CompromisoPublico getCompromisoPublico() {
		return compromisoPublico;
	}

	public void setCompromisoPublico(CompromisoPublico compromisoPublico) {
		this.compromisoPublico = compromisoPublico;
	}

	@Override
	public String getEventType() {
		return COMPROMISO_PUBLICO_TYPE;
	}
}
