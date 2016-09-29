package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Area;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "areas" })
@XmlRootElement(name = "AreaRespMsg")
public class AreaRespMsg extends EsbBaseMsg {

	public static final String AREA_TYPE = "AreaRespMsg";
    
    private Long id;
    private String name;
    @XmlElement(name = "areas")
    private List<Area> areas;

	@Override
	public String getEventType() {
		return AREA_TYPE;
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

	public List<Area> getAreas() {
		if (areas == null) {
			areas = new ArrayList<>();
		}
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}
}
