package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "poblacionesMeta" })
@XmlRootElement(name = "PoblacionMetaRespMsg")
public class PoblacionMetaRespMsg extends EsbBaseMsg {

	public static final String POBLACION_META_TYPE = "PoblacionMetaRespMsg";
    
    private Long id;
    private String name;
    @XmlElement(name = "poblacionesMeta")
    private List<PoblacionMeta> poblacionesMeta;

	@Override
	public String getEventType() {
		return POBLACION_META_TYPE;
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

	public List<PoblacionMeta> getPoblacionesMeta() {
		if (poblacionesMeta == null) {
			poblacionesMeta = new ArrayList<>();
		}
		return poblacionesMeta;
	}

	public void setPoblacionesMeta(List<PoblacionMeta> poblacionesMeta) {
		this.poblacionesMeta = poblacionesMeta;
	}
}
