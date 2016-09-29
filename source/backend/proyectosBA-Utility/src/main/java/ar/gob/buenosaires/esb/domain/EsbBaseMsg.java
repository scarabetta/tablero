package ar.gob.buenosaires.esb.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class EsbBaseMsg {
	private String userTokenId;
	private String emailUsuario;

	public abstract String getEventType();

	@XmlAttribute(name = "userTokenId")
	public String getUserTokenId() {
		return userTokenId;
	}

	public void setUserTokenId(final String userTokenId) {
		this.userTokenId = userTokenId;
	}

	@XmlAttribute(name = "emailUsuario")
	public String getEmailUsuario() {
		if(emailUsuario == null){
			emailUsuario = "";
		}
		return emailUsuario;
	}

	public void setEmailUsuario(String emailUsuario) {
		this.emailUsuario = emailUsuario;
	}
}
