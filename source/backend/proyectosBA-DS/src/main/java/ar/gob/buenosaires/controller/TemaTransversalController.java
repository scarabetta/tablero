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

import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.TemaTransversalService;

@RestController
@RequestMapping("/api/temaTransversal")
public class TemaTransversalController {

	@Autowired
	private TemaTransversalService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<TemaTransversal> getTemasTransversales() throws ESBException, JMSException {
		return this.service.getTemasTransversales();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody TemaTransversal getTemaTransversalPorId(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getTemaTransversalPorId(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public TemaTransversal createTemaTransversal(@RequestBody TemaTransversal temaTransversal) throws ESBException, JMSException {	
		return this.service.createTemaTransversal(temaTransversal);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public TemaTransversal updateTemaTransversal(@RequestBody TemaTransversal temaTransversal) throws ESBException, JMSException {
		return this.service.updateTemaTransversal(temaTransversal);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteTemaTransversal(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteTemaTransversal(id);
	}
}
