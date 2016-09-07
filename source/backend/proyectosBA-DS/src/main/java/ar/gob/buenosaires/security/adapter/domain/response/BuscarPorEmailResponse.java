
package ar.gob.buenosaires.security.adapter.domain.response;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "buscarporemailResponse", namespace = "urn:validacionldap")
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "rta" })
public class BuscarPorEmailResponse implements Serializable {
	
	private static final long serialVersionUID = 6555549936178029733L;
	
	@XmlElement(name = "return")
	private UserValidarPorMail rta;

	public UserValidarPorMail getRta() {
		return rta;
	}

	public void setRta(UserValidarPorMail rta) {
		this.rta = rta;
	}		

}
