package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "jurisdicciones" })
@XmlRootElement(name = "JurisdiccionRespMsg")
public class JurisdiccionRespMsg extends EsbBaseMsg {

	public static final String JURISDICCION_TYPE =
            "JurisdiccionRespMsg";
    
    private Long id;
    private String name;
    @XmlElement(name = "jurisdicciones")
    private List<Jurisdiccion> jurisdicciones = new ArrayList<>();

	@Override
	public String getEventType() {
		return JURISDICCION_TYPE;
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

	public List<Jurisdiccion> getJurisdicciones() {
		return jurisdicciones;
	}

	public void setJurisdicciones(List<Jurisdiccion> jurisdiccion) {
		this.jurisdicciones = jurisdiccion;
	}
}
