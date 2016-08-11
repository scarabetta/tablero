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

import ar.gob.buenosaires.domain.PoblacionMeta;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.PoblacionMetaService;

@RestController
@RequestMapping("/api/poblacionMeta")
public class PoblacionMetaController {

	@Autowired
	private PoblacionMetaService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<PoblacionMeta> getPoblacionesMetas() throws ESBException, JMSException {
		return this.service.getPoblacionesMeta();
	}
//
//	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
//	public @ResponseBody PoblacionMeta getPoblacionMetaPorId(@PathVariable String id) throws ESBException, JMSException {
//		return this.service.getPoblacionMetaPorId(id);
//	}
//	
//	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
//	public @ResponseBody PoblacionMeta getCategoriaPorNombre(@PathVariable String nombre) throws ESBException, JMSException {
//		return this.service.getPoblacionMetaesByName(nombre);
//	}
//	
//	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
//	public @ResponseBody PoblacionMeta getCategoriaPorCodigo(@PathVariable String codigo) throws ESBException, JMSException {
//		return this.service.getPoblacionMetaesPorCodigo(codigo);
//	}
//	
//	@RequestMapping(method = RequestMethod.POST)
//	public void createPoblacionMeta(@RequestBody PoblacionMeta PoblacionMeta) throws ESBException, JMSException {	
//		this.service.createPoblacionMetaes(PoblacionMeta);
//	}
//	
//	@RequestMapping(method = RequestMethod.PUT)
//	public void updatePoblacionMeta(@RequestBody PoblacionMeta PoblacionMeta) throws ESBException, JMSException {
//		this.service.updatePoblacionMetaes(PoblacionMeta);
//	}
//	
//	@RequestMapping(method = RequestMethod.DELETE)
//	public void deletePoblacionMeta(@RequestBody PoblacionMeta PoblacionMeta) throws ESBException, JMSException {
//		this.service.deletePoblacionMeta(PoblacionMeta);
//	}
}
