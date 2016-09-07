package ar.gob.buenosaires.security.service;

import javax.jms.JMSException;

import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.domain.Payload;
import ar.gob.buenosaires.security.service.exception.LoginException;

public interface AuthenticationService {

	JWToken userLogin(Payload payload) throws LoginException, ESBException, JMSException;

}
