package ar.gob.buenosaires.controller;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.gob.buenosaires.domain.EtiquetasMsg;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.EtiquetaService;

@RestController
@RequestMapping("/api/etiqueta")
public class EtiquetaController {

	@Autowired
	private EtiquetaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody EtiquetasMsg getEtiquetas() throws ESBException, JMSException {
		return this.service.getEtiquetas();
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody EtiquetasMsg getEtiquetasPorProyecto(@PathVariable final String id) throws ESBException, JMSException {
		return this.service.getEtiquetasPorProyecto(id);
	}
}
