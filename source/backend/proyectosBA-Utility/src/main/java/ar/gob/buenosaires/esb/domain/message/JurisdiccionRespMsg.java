package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.JurisdiccionResumen;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "jurisdicciones", "jurisdiccionesResumen" })
@XmlRootElement(name = "JurisdiccionRespMsg")
public class JurisdiccionRespMsg extends EsbBaseMsg {

	public static final String JURISDICCION_TYPE = "JurisdiccionRespMsg";
    
    private Long id;
    private String name;
    @XmlElement(name = "jurisdicciones")
    private List<Jurisdiccion> jurisdicciones;
    
    @XmlElement(name = "jurisdiccionesResumen")
    private List<JurisdiccionResumen> jurisdiccionesResumen;      

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
		if (jurisdicciones == null) {
			jurisdicciones = new ArrayList<>();
		}
		return jurisdicciones;
	}

	public void setJurisdicciones(List<Jurisdiccion> jurisdiccion) {
		this.jurisdicciones = jurisdiccion;
	}

	public List<JurisdiccionResumen> getJurisdiccionesResumen() {
		if (jurisdiccionesResumen == null) {
			jurisdiccionesResumen = new ArrayList<>();
		}
		return jurisdiccionesResumen;
	}

	public void setJurisdiccionesResumen(
			List<JurisdiccionResumen> jurisdiccionesResumen) {
		this.jurisdiccionesResumen = jurisdiccionesResumen;
	}		
}
