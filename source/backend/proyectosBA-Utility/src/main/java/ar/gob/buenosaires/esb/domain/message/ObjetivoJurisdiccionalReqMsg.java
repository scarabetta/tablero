package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "objetivoJurisdiccional", "codigo" })
@XmlRootElement(name = "ObjetivoJurisdiccionalReqMsg")
public class ObjetivoJurisdiccionalReqMsg extends EsbBaseMsg {
	
	public static final String OBJETIVO_JURISDICCIONAL_TYPE = "ObjetivoJurisdiccionalReqMsg";
    
    private Long id;
    private String name;
    private ObjetivoJurisdiccional objetivoJurisdiccional;
    private String codigo;

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

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public ObjetivoJurisdiccional getObjetivoJurisdiccional() {
		return objetivoJurisdiccional;
	}

	public void setObjetivoJurisdiccional(ObjetivoJurisdiccional objetivoJurisdiccional) {
		this.objetivoJurisdiccional = objetivoJurisdiccional;
	}	
}
