package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "poblacionMeta", "codigo" })
@XmlRootElement(name = "PoblacionMetaReqMsg")
public class PoblacionMetaReqMsg extends EsbBaseMsg {
	
    public static final String POBLACION_META_TYPE ="PoblacionMetaReqMsg";
    
    private Long id;
    private String name;
    private PoblacionMeta poblacionMeta;
    private String codigo;

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

	public PoblacionMeta getPoblacionMeta() {
		return poblacionMeta;
	}

	public void setPoblacionMeta(PoblacionMeta poblacionMeta) {
		this.poblacionMeta = poblacionMeta;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}		
}
