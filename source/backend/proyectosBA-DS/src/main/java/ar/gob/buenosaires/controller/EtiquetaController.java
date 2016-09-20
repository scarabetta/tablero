package ar.gob.buenosaires.controller;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.gob.buenosaires.domain.EtiquetaResponse;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.EtiquetaService;

@RestController
@RequestMapping("/api/etiqueta")
public class EtiquetaController {

	@Autowired
	private EtiquetaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody EtiquetaResponse getTemasTransversales() throws ESBException, JMSException {
		return this.service.getEtiquetas();
	}
}
