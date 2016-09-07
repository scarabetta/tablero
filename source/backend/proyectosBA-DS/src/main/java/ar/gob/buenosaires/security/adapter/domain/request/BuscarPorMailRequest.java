package ar.gob.buenosaires.security.adapter.domain.request;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "buscarporemail")
@XmlType(propOrder = "email")
public class BuscarPorMailRequest implements Serializable {

	private static final long serialVersionUID = 6737717193269145419L;
	
	private String email;	

	public BuscarPorMailRequest() {
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
}
