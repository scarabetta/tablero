package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Comuna;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "comunas" })
@XmlRootElement(name = "ComunaRespMsg")
public class ComunaRespMsg extends EsbBaseMsg {

	public static final String COMUNA_TYPE = "ComunaRespMsg";
    
    private Long id;
    private String name;
    @XmlElement(name = "comunas")
    private List<Comuna> comunas = new ArrayList<>();

	@Override
	public String getEventType() {
		return COMUNA_TYPE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Comuna> getComunas() {
		return comunas;
	}

	public void setComunas(List<Comuna> comunas) {
		this.comunas = comunas;
	}
}
