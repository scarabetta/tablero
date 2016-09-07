package ar.gob.buenosaires.service.impl;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.gob.buenosaires.service.ConfigurationService;

@Service
public class ConfigurationServiceImpl implements ConfigurationService {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(ConfigurationServiceImpl.class);
	
	@Value("#{'${configs.key.value:}'.split(';')}")
	private String[] properties;

	@Override
	public JsonNode getProperties() throws Exception{
		
		StringBuffer jsonString = new StringBuffer("{");
		boolean key = Boolean.TRUE;
		if(properties != null && properties.length > 1) {
			for (int i = 0; i < properties.length; i++) {
				jsonString.append("\"" + properties[i] + "\"");
				if(key){
					jsonString.append(":");
					key = Boolean.FALSE;
				} else if( i < properties.length - 1){
					jsonString.append(",");
					key = Boolean.TRUE;
				}
			}
		}
		jsonString.append("}");
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
	    try {
			actualObj = mapper.readTree(jsonString.toString());
		} catch (IOException e) {
			getLogger().error("Verifique los parametros de la propiedad configs.key.value en el archivo application.properties");
			throw new Exception(e.getMessage());
		}
		
		return actualObj;
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
