package ar.gob.buenosaires.security.adapter;

import ar.gob.buenosaires.security.jwt.domain.Payload;

public interface AuthenticationAdapter {

	boolean validUser(Payload payload);

}