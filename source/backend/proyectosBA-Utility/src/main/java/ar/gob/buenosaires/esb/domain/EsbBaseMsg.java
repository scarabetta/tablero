package ar.gob.buenosaires.esb.domain;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public abstract class EsbBaseMsg {
	private String userTokenId;

	public abstract String getEventType();

	@XmlAttribute(name = "userTokenId")
	public String getUserTokenId() {
		return userTokenId;
	}

	public void setUserTokenId(final String userTokenId) {
		this.userTokenId = userTokenId;
	}
}
