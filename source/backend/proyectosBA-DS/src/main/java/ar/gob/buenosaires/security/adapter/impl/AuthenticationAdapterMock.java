package ar.gob.buenosaires.security.adapter.impl;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.security.adapter.AuthenticationAdapter;
import ar.gob.buenosaires.security.jwt.domain.Payload;

@Service
@Profile({ "dev", "mock" })
public class AuthenticationAdapterMock implements AuthenticationAdapter {

	private static final String VALID_PASSWORD = "troquel";

	private static final String VALID_EMAIL = "acampos@buenosaires.gob.ar";

	private final static Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationAdapterMock.class);

	private static final String LDAP_NOT_WORKING = "-2";

	private static final String USER_NOT_EXIST = "-1";

	private static final String INVALID_PASSWORD = "0";

	private static final String VALID_USER = "1";

	private static final String JAXB_CONTEXT_PATH_REQUEST = "ar.gob.buenosaires.security.adapter.domain.request";
	private static final String JAXB_CONTEXT_PATH_RESPONSE = "ar.gob.buenosaires.security.adapter.domain.response";

	@Value("${security.endpoint}")
	private String endpointURL;

	@Value("${security.request.email}")
	private String email;

	@Value("${security.request.password}")
	private String password;

	@Value("${security.request.operation}")
	private String operation;
	
	private Jaxb2Marshaller marshaller;
	private Jaxb2Marshaller unmarshaller;

	@Override
	public boolean validUser(Payload payload) {
			return true;
	}

	public Jaxb2Marshaller getMarshaller() {
		if(marshaller == null){
			marshaller = new Jaxb2Marshaller();
			marshaller.setContextPaths(JAXB_CONTEXT_PATH_REQUEST);
		}
		return marshaller;
	}
	
	public Jaxb2Marshaller getUnMarshaller() {
		if(unmarshaller == null){
			unmarshaller = new Jaxb2Marshaller();
			unmarshaller.setContextPaths(JAXB_CONTEXT_PATH_RESPONSE);
		}
		return unmarshaller;
	}
	
	public static Object unmarshall(final String string, Jaxb2Marshaller unmarshaller) {								
		return unmarshaller.unmarshal(new StreamSource(new StringReader(string)));
	}

	public static String marshall(final Object message, Jaxb2Marshaller marshaller) {		
		StringWriter out = new StringWriter();
		marshaller.marshal(message, new StreamResult(out));
		return out.toString();
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	@Override
	public Usuario validMail(String email) {
		Usuario usuario = null;
		String[] parts = email.split("@");
		String part1 = parts[1];
		if (part1.equalsIgnoreCase("buenosaires.gob.ar")) {
			usuario = new Usuario();
			usuario.setNombre("Adrian Diego");
			usuario.setApellido("Campos");
			usuario.setEmail(email);
		}
		return usuario;
	}
}
