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

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.JurisdiccionService;

@RestController
@RequestMapping("/api/jurisdiccion")
public class JurisdiccionController {

	@Autowired
	private JurisdiccionService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Jurisdiccion> getJurisdicciones() throws ESBException, JMSException {
		return this.service.getJurisdicciones();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Jurisdiccion getJurisdiccionPorId(@PathVariable String id) throws ESBException, JMSException {
		return this.service.getJurisdiccionPorId(id);
	}
	
	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
	public @ResponseBody Jurisdiccion getCategoriaPorNombre(@PathVariable String nombre) throws ESBException, JMSException {
		return this.service.getJurisdiccionesByName(nombre);
	}
	
	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
	public @ResponseBody Jurisdiccion getCategoriaPorCodigo(@PathVariable String codigo) throws ESBException, JMSException {
		return this.service.getJurisdiccionesPorCodigo(codigo);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public void createJurisdiccion(@RequestBody Jurisdiccion jurisdiccion) throws ESBException, JMSException {	
		this.service.createJurisdicciones(jurisdiccion);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public void updateJurisdiccion(@RequestBody Jurisdiccion jurisdiccion) throws ESBException, JMSException {
		this.service.updateJurisdicciones(jurisdiccion);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteJurisdiccion(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteJurisdiccion(id);
	}
}
