package ar.gob.buenosaires.security.jwt.domain;

import java.io.Serializable;

import ar.gob.buenosaires.security.jwt.JWToken;

public class AccessToken implements Serializable {

	private static final long serialVersionUID = 189617266513633377L;

	private String access_token;

	public AccessToken() {
	}

	public String getAccessToken() {
		return access_token;
	}

	public void setAccessToken(String access_token) {
		this.access_token = access_token;
	}
	
	public String getBearerAuthHeader() {
		return JWToken.BEARER + " " + this.access_token;
	}
}
