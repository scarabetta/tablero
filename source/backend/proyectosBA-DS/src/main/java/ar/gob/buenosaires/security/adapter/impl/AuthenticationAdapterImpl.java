package ar.gob.buenosaires.security.adapter.impl;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.ws.client.core.WebServiceTemplate;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.security.adapter.AuthenticationAdapter;
import ar.gob.buenosaires.security.adapter.domain.request.BuscarPorMailRequest;
import ar.gob.buenosaires.security.adapter.domain.request.ValidarRequest;
import ar.gob.buenosaires.security.adapter.domain.response.BuscarPorEmailResponse;
import ar.gob.buenosaires.security.adapter.domain.response.ValidarResponse;
import ar.gob.buenosaires.security.jwt.domain.Payload;

@Service
@Profile("prod")
public class AuthenticationAdapterImpl implements AuthenticationAdapter {

	private static final String VALIDAR_RESPONSE = "return";

	private final static Logger LOGGER = LoggerFactory
			.getLogger(AuthenticationAdapterImpl.class);

	private static final String LDAP_NOT_WORKING = "-2";

	private static final String USER_NOT_EXIST = "-1";

	private static final String INVALID_PASSWORD = "0";

	private static final String VALID_USER = "1";

	private static final String JAXB_CONTEXT_PATH_REQUEST = "ar.gob.buenosaires.security.adapter.domain.request";
	private static final String JAXB_CONTEXT_PATH_RESPONSE = "ar.gob.buenosaires.security.adapter.domain.response";

	private static final String BUSCAR_POR_EMAIL_NOMBRE = "nombre";

	private static final String BUSCAR_POR_EMAIL_APELLIDO = "apellido";

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
		getLogger().info("Enviando mensaje al LDAP: " + endpointURL);
		webServiceTemplate.sendSourceAndReceiveToResult(endpointURL, source, new StreamResult(result));
		getLogger().info("Se recibio la respuesta del LDAP: " + endpointURL);
		
		String respXML = result.getBuffer().toString();
		
		ValidarResponse response = (ValidarResponse) unmarshall(respXML, getUnMarshaller());
		
		if(response.getRta() == null){
			getLogger().info("Hubo un error al extraer la respuesta, se extrae a mano");
			response.setRta(extractResponse(respXML, VALIDAR_RESPONSE));
		} 
		
		getLogger().info("la repuesta del LDAP fue: " + response.getRta() + " , y la validacion es: " + VALID_USER.equalsIgnoreCase(response.getRta()));
		
		return VALID_USER.equalsIgnoreCase(response.getRta());
	}
	
	@Override
	public Usuario validMail(String email) {
		Usuario usuario = null;
		BuscarPorMailRequest validarMailRequest = new BuscarPorMailRequest();		
		validarMailRequest.setEmail(email);
		
		StreamSource source = new StreamSource(new StringReader(marshall(validarMailRequest, getMarshaller())));
		StringWriter result = new StringWriter();
		
		getLogger().info("Enviando mensaje al LDAP: " + endpointURL);
		WebServiceTemplate webServiceTemplate = new WebServiceTemplate();
		webServiceTemplate.sendSourceAndReceiveToResult(endpointURL, source, new StreamResult(result));
		getLogger().info("Se recibio la respuesta del LDAP: " + endpointURL);
		
		String respXML = result.getBuffer().toString();
		
		BuscarPorEmailResponse response = (BuscarPorEmailResponse) unmarshall(respXML, getUnMarshaller());
		
		if(response.getRta() != null && response.getRta().getApellido() != null) {
			usuario = new Usuario();
			usuario.setNombre(response.getRta().getNombre());
			usuario.setApellido(response.getRta().getApellido());
			usuario.setEmail(email);
		} else if(StringUtils.isNotBlank(extractResponse(respXML, BUSCAR_POR_EMAIL_NOMBRE))){
			getLogger().info("Hubo un error al extraer la respuesta, se extrae a mano");
			usuario = new Usuario();
			usuario.setNombre(extractResponse(respXML, BUSCAR_POR_EMAIL_NOMBRE));
			usuario.setApellido(extractResponse(respXML, BUSCAR_POR_EMAIL_APELLIDO));
			usuario.setEmail(email);
		}
		
		return usuario;
	}
	
	/**
	 * La respuesta del LDAP no deberia ser NULL, cuando usa la JDK de IBM falla al querer crear la clase de respuesta.
	 * @param parametro extraido.
	 * @return
	 */
	private String extractResponse(String respXML, String parametro) {
		String cutXml = "";
		String tagInicial = "<" + parametro + ">";
		String tagFinal = "</" + parametro + ">";
		
		if(respXML.contains(tagInicial)){
			cutXml = respXML.substring(respXML.indexOf(tagInicial) + tagInicial.length(), respXML.indexOf(tagFinal));
		}
			
		return cutXml;
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
		getLogger().info("Mensaje recibido del LDAP: " + string);
		return unmarshaller.unmarshal(new StreamSource(new StringReader(string)));
	}

	public static String marshall(final Object message, Jaxb2Marshaller marshaller) {		
		StringWriter out = new StringWriter();
		marshaller.marshal(message, new StreamResult(out));
		getLogger().info("Mensaje enviado al LDAP : " + sacarPassword(out.toString()));
		return out.toString();
	}

	/**
	 * <?xml version="1.0" encoding="UTF-8" standalone="yes"?><validar><email>cperret@buenosaires.gob.ar</email><clave>troquel</clave></validar>
	 * @param string
	 * @return
	 */
	private static String sacarPassword(String xml) {
		String cutXml = xml;
		
		if(xml.contains("<clave>")){
			cutXml = xml.substring(0, xml.indexOf("<clave>"));
			cutXml = cutXml.concat(xml.substring(xml.indexOf("</clave>") + 8));
		}
			
		return cutXml;
	}

	public static Logger getLogger() {
		return LOGGER;
	}

}
