package ar.gob.buenosaires.security.jwt.jsonview;

import com.fasterxml.jackson.annotation.JsonView;

import ar.gob.buenosaires.security.jwt.jsonview.configuration.JWTokenAuthViewConfiguration;

public interface JWTokenAuthJsonView {

	@JsonView(JWTokenAuthViewConfiguration.class)
	public String getToken();

}
