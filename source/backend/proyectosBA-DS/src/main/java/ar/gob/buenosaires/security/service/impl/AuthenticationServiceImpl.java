package ar.gob.buenosaires.security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.security.adapter.AuthenticationAdapter;
import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.JWTokenUtils;
import ar.gob.buenosaires.security.jwt.domain.Payload;
import ar.gob.buenosaires.security.service.AuthenticationService;
import ar.gob.buenosaires.security.service.exception.LoginException;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Value("${security.jwt.expirationTime.hours}")
	private int expirationTime;

	@Value("${security.jwt.issuer}")
	private String issuer;

	@Autowired
	AuthenticationAdapter authAdapter;
	
	@Override
	public JWToken userLogin(Payload payload) throws LoginException {
		JWToken token = null;

		if (authAdapter.validUser(payload)) {
			try {
				token = JWTokenUtils.newInstanceHS256Bearer(payload.getEmail(), expirationTime, issuer);
			} catch (Exception e) {
				getLogger().info("Se ha producido un error al querer generar el token para el usuario: {}",
						payload.getEmail());
				e.printStackTrace();
			}
		} else {
			throw new LoginException("El usuario y/o contraseña no son válidos");
		}

		return token;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
