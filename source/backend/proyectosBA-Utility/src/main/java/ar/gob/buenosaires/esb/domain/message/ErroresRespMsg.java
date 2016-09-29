package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ErrorDescripcion;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "errores" })
@XmlRootElement(name = "ErroresRespMsg")
public class ErroresRespMsg extends EsbBaseMsg {

    public static final String ERRORES_TYPE = "ErroresRespMsg";
    
    @XmlElement(name = "errores")
    private List<ErrorDescripcion> errores;        	
	
	public List<ErrorDescripcion> getErrores() {
		if (errores == null) {
			return new ArrayList<>();
		}
		return errores;
	}

	public void setErrores(List<ErrorDescripcion> errores) {
		this.errores = errores;
	}

	@Override
	public String getEventType() {
		return ERRORES_TYPE;
	}

}
