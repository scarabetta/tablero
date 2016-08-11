package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "ejesDeGobierno" })
@XmlRootElement(name = "EjeDeGobiernoRespMsg")
public class EjeDeGobiernoRespMsg extends EsbBaseMsg {

	public static final String EJE_DE_GOBIERNO_TYPE = "EjeDeGobiernoRespMsg";
    
    private Long id;
    private String name;
    @XmlElement(name = "ejesDeGobierno")
    private List<EjeDeGobierno> ejesDeGobierno = new ArrayList<>();

	@Override
	public String getEventType() {
		return EJE_DE_GOBIERNO_TYPE;
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

	public List<EjeDeGobierno> getEjesDeGobierno() {
		return ejesDeGobierno;
	}

	public void setEjesDeGobierno(List<EjeDeGobierno> ejesDeGobierno) {
		this.ejesDeGobierno = ejesDeGobierno;
	}
}
