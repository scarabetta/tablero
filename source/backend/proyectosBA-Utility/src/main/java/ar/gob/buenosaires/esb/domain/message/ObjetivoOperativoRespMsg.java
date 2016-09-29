package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "objetivosOperativos" })
@XmlRootElement(name = "ObjetivoOperativoRespMsg")
public class ObjetivoOperativoRespMsg extends EsbBaseMsg {
	
	public static final String OBJETIVO_OPERATIVO_TYPE = "ObjetivoOperativoRespMsg";
    
    private Long id;
    private String name;
    @XmlElement(name = "objetivosOperativos")
    private List<ObjetivoOperativo> objetivosOperativos;

	@Override
	public String getEventType() {
		return OBJETIVO_OPERATIVO_TYPE;
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

	public List<ObjetivoOperativo> getObjetivosOperativos() {
		if (objetivosOperativos == null) {
			objetivosOperativos = new ArrayList<>();
		}
		return objetivosOperativos;
	}

	public void setObjetivosOperativos(List<ObjetivoOperativo> objetivosOperativoes) {
		this.objetivosOperativos = objetivosOperativoes;
	}
}
