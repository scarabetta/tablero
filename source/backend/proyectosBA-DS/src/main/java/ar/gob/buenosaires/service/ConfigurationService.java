package ar.gob.buenosaires.service;

import com.fasterxml.jackson.databind.JsonNode;

public interface ConfigurationService {

	JsonNode getProperties() throws Exception;

}
