package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "objetivoOperativo", "codigo" })
@XmlRootElement(name = "ObjetivoOperativoReqMsg")
public class ObjetivoOperativoReqMsg extends EsbBaseMsg {
	
	public static final String OBJETIVO_OPERATIVO_TYPE = "ObjetivoOperativoReqMsg";
    
    private Long id;
    private String name;
    private ObjetivoOperativo objetivoOperativo;
    private String codigo;

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ObjetivoOperativo getObjetivoOperativo() {
		return objetivoOperativo;
	}

	public void setObjetivoOperativo(ObjetivoOperativo objetivoOperativo) {
		this.objetivoOperativo = objetivoOperativo;
	}	
}
