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
import org.springframework.ws.client.core.WebServiceTemplate;

import ar.gob.buenosaires.security.adapter.AuthenticationAdapter;
import ar.gob.buenosaires.security.adapter.domain.request.ValidarRequest;
import ar.gob.buenosaires.security.adapter.domain.response.ValidarResponse;
import ar.gob.buenosaires.security.jwt.domain.Payload;

@Service
@Profile("prod")
public class AuthenticationAdapterImpl implements AuthenticationAdapter {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationAdapterImpl.class);

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
		ValidarRequest validarRequest = new ValidarRequest();		
		validarRequest.setEmail(payload.getEmail());
		validarRequest.setClave(payload.getPassword());
		
		StreamSource source = new StreamSource(new StringReader(marshall(validarRequest, getMarshaller())));
		StringWriter result = new StringWriter();
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.sendSourceAndReceiveToResult(endpointURL, source, new StreamResult(result));
		
		ValidarResponse response = (ValidarResponse) unmarshall(result.getBuffer().toString(), getUnMarshaller());
		
		return VALID_USER.equalsIgnoreCase(response.getRta());
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
}
