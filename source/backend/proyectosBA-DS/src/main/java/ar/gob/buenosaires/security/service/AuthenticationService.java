package ar.gob.buenosaires.security.service;

import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.domain.Payload;
import ar.gob.buenosaires.security.service.exception.LoginException;

public interface AuthenticationService {

	JWToken userLogin(Payload payload) throws LoginException;

}
