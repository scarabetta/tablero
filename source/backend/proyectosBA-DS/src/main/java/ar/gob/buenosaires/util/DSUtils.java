package ar.gob.buenosaires.util;

import java.text.ParseException;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;

public class DSUtils {

	public static String getMailDelUsuarioDelToken(final String stringToken)
			throws ParseException, JOSEException, SignatureVerificationException {
		Usuario user = null;
		final JWToken token = new JWToken(stringToken);
		return token.getSubject();
	}
}
