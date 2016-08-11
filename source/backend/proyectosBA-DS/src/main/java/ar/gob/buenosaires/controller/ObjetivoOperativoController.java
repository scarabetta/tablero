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

import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ObjetivoOperativoService;

@RestController
@RequestMapping("/api/objetivoOperativo")
public class ObjetivoOperativoController {
	
	@Autowired
	private ObjetivoOperativoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<ObjetivoOperativo> getObjetivosOperativos() throws ESBException, JMSException {
		return this.service.getObjetivosOperativos();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoOperativo getObjetivoOperativoPorId(@PathVariable String id) throws ESBException, JMSException {
		return this.service.getObjetivoOperativoPorId(id);
	}
	
	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoOperativo getObjetivoOperativoPorNombre(@PathVariable String nombre) throws ESBException, JMSException {
		return this.service.getObjetivoOperativoPorNombre(nombre);
	}
	
	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoOperativo getObjetivoOperativoPorCodigo(@PathVariable String codigo) throws ESBException, JMSException {
		return this.service.getObjetivoOperativoPorCodigo(codigo);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ObjetivoOperativo createObjetivoOperativo(@RequestBody ObjetivoOperativo objetivoOperativo) throws ESBException, JMSException {	
		return this.service.createObjetivoOperativo(objetivoOperativo);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public ObjetivoOperativo updateObjetivoOperativo(@RequestBody ObjetivoOperativo objetivoOperativo) throws ESBException, JMSException {
		return this.service.updateObjetivoOperativo(objetivoOperativo);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteObjetivoOperativo(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteObjetivoOperativo(id);
	}
	
}
