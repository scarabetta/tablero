package ar.gob.buenosaires.controller;

import java.util.List;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.gob.buenosaires.domain.EjeDeGobierno;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.EjeDeGobiernoService;

@RestController
@RequestMapping("/api/ejeDeGobierno")
public class EjeDeGobiernoController {

	@Autowired
	private EjeDeGobiernoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<EjeDeGobierno> getEjesDeGobierno() throws ESBException, JMSException {
		return this.service.getEjesDeGobierno();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody EjeDeGobierno getEjeDeGobiernoPorId(@PathVariable String id) throws ESBException, JMSException {
		return this.service.getEjeDeGobiernoPorId(id);
	}
	
	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
	public @ResponseBody EjeDeGobierno getCategoriaPorNombre(@PathVariable String nombre) throws ESBException, JMSException {
		return this.service.getEjeDeGobiernoByName(nombre);
	}
	
//	@RequestMapping(method = RequestMethod.POST)
//	public void createEjeDeGobierno(@RequestBody EjeDeGobierno EjeDeGobierno) throws ESBException, JMSException {	
//		this.service.createEjeDeGobiernoes(EjeDeGobierno);
//	}
//	
//	@RequestMapping(method = RequestMethod.PUT)
//	public void updateEjeDeGobierno(@RequestBody EjeDeGobierno EjeDeGobierno) throws ESBException, JMSException {
//		this.service.updateEjeDeGobiernoes(EjeDeGobierno);
//	}
//	
//	@RequestMapping(method = RequestMethod.DELETE)
//	public void deleteEjeDeGobierno(@RequestBody EjeDeGobierno EjeDeGobierno) throws ESBException, JMSException {
//		this.service.deleteEjeDeGobierno(EjeDeGobierno);
//	}
}
