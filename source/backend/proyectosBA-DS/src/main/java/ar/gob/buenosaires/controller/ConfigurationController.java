package ar.gob.buenosaires.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import ar.gob.buenosaires.service.ConfigurationService;

@RestController
@RequestMapping("/config")
public class ConfigurationController {
	
	@Autowired
	ConfigurationService configService;
	
	@RequestMapping(path = "/properties", method = RequestMethod.GET)
	public @ResponseBody JsonNode properties() throws Exception {
		return configService.getProperties();
	}
}
