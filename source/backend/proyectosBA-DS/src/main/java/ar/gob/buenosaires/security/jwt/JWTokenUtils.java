package ar.gob.buenosaires.security.jwt;

import java.text.ParseException;

import org.joda.time.DateTime;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.JWSSigner;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;

import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;


public class JWTokenUtils {

	public static final String TOKEN_SECRET_SBA = "h2pr92mfhssy60lksrs56wer23345991";
	private static final JWSHeader JWT_HEADER = new JWSHeader(JWSAlgorithm.HS256);
	
	public static JWToken newInstanceHS256Bearer(String email, int hours, String issuer) throws JOSEException {
		DateTime now = DateTime.now();
		JWTClaimsSet claimsSet = new JWTClaimsSet.Builder()
			     .subject(email)
			     .issueTime(now.toDate())
			     .notBeforeTime(now.toDate())
			     .expirationTime(now.plusHours(hours).toDate())
			     .issuer(issuer)
			     .build();
		
		JWSSigner signer = new MACSigner(TOKEN_SECRET_SBA);
		SignedJWT jwt = new SignedJWT(JWT_HEADER, claimsSet);
		jwt.sign(signer);
		String authHeader = crearBearerHeader(jwt.serialize());
		return new JWToken(authHeader);
	}

	public static String getSubFromJwTokenString(String jwtString) throws ParseException, JOSEException, SignatureVerificationException {
		JWToken jwt = new JWToken(jwtString);
		return jwt.getSubject();
	}

	private static String crearBearerHeader(String token) {
		return JWToken.BEARER + " " + token; 
	}

}
