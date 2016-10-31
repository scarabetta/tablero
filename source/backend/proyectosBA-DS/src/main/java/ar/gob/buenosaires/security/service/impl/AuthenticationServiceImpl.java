package ar.gob.buenosaires.security.service.impl;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.UsuarioResumen;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.adapter.AuthenticationAdapter;
import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.JWTokenUtils;
import ar.gob.buenosaires.security.jwt.domain.Payload;
import ar.gob.buenosaires.security.service.AuthenticationService;
import ar.gob.buenosaires.security.service.exception.LoginException;
import ar.gob.buenosaires.service.UsuarioService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

	private final static Logger LOGGER = LoggerFactory.getLogger(AuthenticationServiceImpl.class);

	@Value("${security.jwt.expirationTime.minutes}")
	private int expirationTimeInMinutes;

	@Value("${security.jwt.issuer}")
	private String issuer;

	@Autowired
	AuthenticationAdapter authAdapter;
	
	@Autowired
	UsuarioService usuarioService;
	
	@Override
	public JWToken userLogin(Payload payload) throws LoginException, ESBException, JMSException {
		JWToken token = null;

		if (authAdapter.validUser(payload)) {
			if(usuarioEstaActivo(payload)){
				try {
					token = JWTokenUtils.newInstanceHS256Bearer(payload.getEmail(), expirationTimeInMinutes, issuer);
				} catch (Exception e) {
					getLogger().info("Se ha producido un error al querer generar el token para el usuario: {}",
							payload.getEmail());
					e.printStackTrace();
				}
			} else {
				getLogger().info("El usuario esta inactivo");
				throw new ESBException(CodigoError.USUARIO_INACTIVO.getCodigo(), "El usuario esta inactivo");
			}
			
		} else {
			getLogger().info("El usuario y/o contraseña no son válidos");
			throw new ESBException(CodigoError.PASS_INVALIDO.getCodigo(), "El usuario y/o contraseña no son válidos");
		}

		return token;
	}

	private boolean usuarioEstaActivo(Payload payload) throws ESBException, JMSException, LoginException {
		UsuarioResumen usuario = usuarioService.getUsuarioResumenByEmail(payload.getEmail());
		if(usuario != null) {
			return usuario.getActivo();			
		}else {
			getLogger().info("El usuario no existe. Contactese con un administrador");
			throw new ESBException(CodigoError.USUARIO_INEXISTENTE.getCodigo(),"El usuario no existe. Contactese con un administrador");
		}
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
