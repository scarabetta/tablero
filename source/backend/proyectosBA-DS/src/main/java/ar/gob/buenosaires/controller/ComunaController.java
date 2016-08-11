package ar.gob.buenosaires.controller;

import java.util.List;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.gob.buenosaires.domain.Comuna;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ComunaService;

@RestController
@RequestMapping("/api/comuna")
public class ComunaController {

	@Autowired
	private ComunaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Comuna> getComunas() throws ESBException, JMSException {
		return this.service.getComunas();
	}

//	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
//	public @ResponseBody Comuna getComunaPorId(@PathVariable String id) throws ESBException, JMSException {
//		return this.service.getComunaPorId(id);
//	}
//	
//	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
//	public @ResponseBody Comuna getCategoriaPorNombre(@PathVariable String nombre) throws ESBException, JMSException {
//		return this.service.getComunaesByName(nombre);
//	}
//	
//	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
//	public @ResponseBody Comuna getCategoriaPorCodigo(@PathVariable String codigo) throws ESBException, JMSException {
//		return this.service.getComunaesPorCodigo(codigo);
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public void createComuna(@RequestBody Comuna Comuna) throws ESBException, JMSException {	
//		this.service.createComunaes(Comuna);
//	}
//	
//	@RequestMapping(method = RequestMethod.PUT)
//	public void updateComuna(@RequestBody Comuna Comuna) throws ESBException, JMSException {
//		this.service.updateComunaes(Comuna);
//	}
//	
//	@RequestMapping(method = RequestMethod.DELETE)
//	public void deleteComuna(@RequestBody Comuna Comuna) throws ESBException, JMSException {
//		this.service.deleteComuna(Comuna);
//	}
}
