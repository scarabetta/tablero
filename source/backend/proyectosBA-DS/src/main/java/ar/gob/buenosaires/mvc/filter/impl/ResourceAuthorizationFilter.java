package ar.gob.buenosaires.mvc.filter.impl;

import java.io.IOException;
import java.text.ParseException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.filter.GenericFilterBean;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.security.jwt.exception.TokenExpiredException;


public class ResourceAuthorizationFilter extends GenericFilterBean {

	private static final String AUTH_ERROR_MSG = "Verifique que la llamada contenga el Token en la cabecera.",
								EXPIRE_ERROR_MSG = "El Token ha expirado",
								JWT_ERROR_MSG = "No se ha podido leer correctamente el Token",
								JWT_INVALID_MSG = "El Token enviado es invalido",
								SIGNATURE_ERROR_MSG ="La firma del Token es invalida";
	
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		String authHeader = httpRequest.getHeader(HttpHeaders.AUTHORIZATION);
		
		try {
			if (!HttpMethod.OPTIONS.name().equals(httpRequest.getMethod())) {
				JWToken token = new JWToken(authHeader);
				token.validate();
			}
			chain.doFilter(request, response);
		} catch (IllegalArgumentException e) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, AUTH_ERROR_MSG);
		} catch (ParseException e) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_ERROR_MSG);
		} catch (JOSEException e) {
			httpResponse.sendError(HttpServletResponse.SC_BAD_REQUEST, JWT_INVALID_MSG);
		} catch (TokenExpiredException e) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, EXPIRE_ERROR_MSG);
		} catch (SignatureVerificationException e) {
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, SIGNATURE_ERROR_MSG);
		}
	}

}
