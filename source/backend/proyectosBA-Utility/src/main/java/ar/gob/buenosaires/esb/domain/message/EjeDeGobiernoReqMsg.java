package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "ejeDeGobierno", "codigo" })
@XmlRootElement(name = "EjeDeGobiernoReqMsg")
public class EjeDeGobiernoReqMsg extends EsbBaseMsg {
	
    public static final String EJE_DE_GOBIERNO_TYPE = "EjeDeGobiernoReqMsg";
    
    private Long id;
    private String name;
    private EjeDeGobierno ejeDeGobierno;
    private String codigo;

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

	public EjeDeGobierno getEjeDeGobierno() {
		return ejeDeGobierno;
	}

	public void setEjeDeGobierno(EjeDeGobierno ejeDeGobierno) {
		this.ejeDeGobierno = ejeDeGobierno;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}		
}
