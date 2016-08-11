
package ar.gob.buenosaires.security.adapter.domain.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "validarResponse", namespace = "urn:validacionldap")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "rta" })
public class ValidarResponse implements Serializable {

	private static final long serialVersionUID = -434775451854601015L;

	@XmlElement(name = "return")
	private String rta;

	public String getRta() {
		return rta;
	}

	public void setRta(String rta) {
		this.rta = rta;
	}		
	
}
