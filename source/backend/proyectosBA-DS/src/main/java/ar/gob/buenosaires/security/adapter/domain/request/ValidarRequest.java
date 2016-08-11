package ar.gob.buenosaires.security.adapter.domain.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "validar")
@XmlType(propOrder = { "email", "clave" })
public class ValidarRequest implements Serializable {

	private static final long serialVersionUID = -434775451854601015L;

	private String email;
	private String clave;

	public ValidarRequest() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getClave() {
		return clave;
	}

	public void setClave(String clave) {
		this.clave = clave;
	}

}
