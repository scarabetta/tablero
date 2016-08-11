package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "objetivosJurisdiccionales" })
@XmlRootElement(name = "ObjetivoJurisdiccionalRespMsg")
public class ObjetivoJurisdiccionalRespMsg extends EsbBaseMsg {
	
	public static final String OBJETIVO_JURISDICCIONAL_TYPE = "ObjetivoJurisdiccionalRespMsg";
    
    private Long id;
    private String name;
    @XmlElement(name = "objetivosJurisdiccionales")
    private List<ObjetivoJurisdiccional> objetivosJurisdiccionales = new ArrayList<>();

	@Override
	public String getEventType() {
		return OBJETIVO_JURISDICCIONAL_TYPE;
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

	public List<ObjetivoJurisdiccional> getObjetivosJurisdiccionales() {
		return objetivosJurisdiccionales;
	}

	public void setObjetivosJurisdiccionales(List<ObjetivoJurisdiccional> objetivosJurisdiccionales) {
		this.objetivosJurisdiccionales = objetivosJurisdiccionales;
	}
}
