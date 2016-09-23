package ar.gob.buenosaires.controller;

import java.util.List;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import ar.gob.buenosaires.domain.ErrorDescripcion;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ConfigurationService;
import ar.gob.buenosaires.service.ErrorDescripcionService;

@RestController
@RequestMapping("/config")
public class ConfigurationController {
	
	@Autowired
	ConfigurationService configService;
	
	
	@Autowired
	private ErrorDescripcionService erroresService;
	
	@RequestMapping(path = "/properties", method = RequestMethod.GET)
	public @ResponseBody JsonNode properties() throws Exception {
		return configService.getProperties();
	}
	
	@RequestMapping(path = "/errores", method = RequestMethod.GET)
	public @ResponseBody List<ErrorDescripcion> getErrores() throws ESBException, JMSException {
		return this.erroresService.getErrores();
	}
}
