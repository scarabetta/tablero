package ar.gob.buenosaires.controller;

import java.util.List;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ObjetivoJurisdiccionalService;

@RestController
@RequestMapping("/api/objetivoJurisdiccional")
public class ObjetivoJurisdiccionalController {

	@Autowired
	private ObjetivoJurisdiccionalService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<ObjetivoJurisdiccional> getObjetivosJurisdiccionales() throws ESBException, JMSException {
		return this.service.getObjetivosJurisdiccionales();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoJurisdiccional getObjetivoJurisdiccionalPorId(@PathVariable String id) throws ESBException, JMSException {
		return this.service.getObjetivoJurisdiccionalPorId(id);
	}
	
	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoJurisdiccional getObjetivoJurisdiccionalPorNombre(@PathVariable String nombre) throws ESBException, JMSException {
		return this.service.getObjetivoJurisdiccionalPorNombre(nombre);
	}
	
	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoJurisdiccional getObjetivoJurisdiccionalPorCodigo(@PathVariable String codigo) throws ESBException, JMSException {
		return this.service.getObjetivoJurisdiccionalPorCodigo(codigo);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ObjetivoJurisdiccional createObjetivoJurisdiccional(@RequestBody ObjetivoJurisdiccional objetivoJurisdiccional) throws ESBException, JMSException {	
		return this.service.createObjetivoJurisdiccional(objetivoJurisdiccional);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ObjetivoJurisdiccional updateObjetivoJurisdiccional(@RequestBody ObjetivoJurisdiccional objetivoJurisdiccional) throws ESBException, JMSException {
		return this.service.updateObjetivoJurisdiccional(objetivoJurisdiccional);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteObjetivoJurisdiccional(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteObjetivoJurisdiccional(id);
	}
	
}
