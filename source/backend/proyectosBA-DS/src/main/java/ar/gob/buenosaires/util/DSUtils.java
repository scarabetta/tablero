package ar.gob.buenosaires.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.ParseException;

import javax.servlet.http.HttpServletRequest;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;

public class DSUtils {

	/**
	 * Obtenemos el email del usuario que genero el token.
	 * @param stringToken
	 * @return el email del usuario
	 * @throws ParseException
	 * @throws JOSEException
	 * @throws SignatureVerificationException
	 */
	public static String getMailDelUsuarioDelToken(final String stringToken)
			throws ParseException, JOSEException, SignatureVerificationException {
		final JWToken token = new JWToken(stringToken);
		return token.getSubject();
	}
	
	/**
	 * Desencodeamos el nombre del archivo con UTF-8 para evitar problemas con caracteres ratos.
	 * @param request
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String obtenerNombreRequest(HttpServletRequest request) throws UnsupportedEncodingException {
		String decodedURI = URLDecoder.decode(request.getRequestURI(), "UTF-8");
		return decodedURI.substring(decodedURI.lastIndexOf("/") + 1);
	}
}
