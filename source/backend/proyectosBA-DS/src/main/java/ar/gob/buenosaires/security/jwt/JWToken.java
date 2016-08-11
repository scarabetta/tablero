package ar.gob.buenosaires.security.jwt;

import java.io.Serializable;
import java.text.ParseException;

import org.joda.time.DateTime;
import org.springframework.util.Assert;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.crypto.MACVerifier;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.security.jwt.exception.TokenExpiredException;
import ar.gob.buenosaires.security.jwt.jsonview.JWTokenAuthJsonView;


public class JWToken implements JWTokenAuthJsonView, Serializable {
	
	public static final String BEARER = "Bearer";

	private static final long serialVersionUID = -2765871640930269137L;
	
	private String token;
	private String authSchemaType;

	public JWTClaimsSet getJWTClaimsSet() throws ParseException, JOSEException, SignatureVerificationException {
		SignedJWT signedJWT = SignedJWT.parse(this.token);
		if (signedJWT.verify(new MACVerifier(JWTokenUtils.TOKEN_SECRET_SBA))) {
			return signedJWT.getJWTClaimsSet();
		}	
		throw new SignatureVerificationException();
	}
	
	public void validate() throws ParseException, JOSEException, TokenExpiredException, SignatureVerificationException {
		JWTClaimsSet claimSet = this.getJWTClaimsSet();
		if (new DateTime(claimSet.getExpirationTime()).isBefore(DateTime.now())) {
			throw new TokenExpiredException();
		}
	}	
	
	public JWToken(String authHeader) {
		Assert.hasText(authHeader);
		Assert.isTrue(authHeader.split(" ").length == 2);
		this.setTokenDetail(authHeader);
	}

	public String getToken() {
		return this.token;
	}
	
	public String getAuthHeader() {
		return this.authSchemaType + " " + this.token;
	}
	
	private void setTokenDetail(String authHeader) {
		String[] headervalues = authHeader.split(" ");
		this.authSchemaType = BEARER;
		this.token = headervalues[1];
	}

	public String getSubject() throws ParseException, JOSEException, SignatureVerificationException {
		JWTClaimsSet claimSet = this.getJWTClaimsSet();
		return claimSet.getSubject();
	}
	
}