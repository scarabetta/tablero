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

import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ProyectoService;

@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {

	@Autowired
	private ProyectoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Proyecto> getProyectos() throws ESBException, JMSException {
		return this.service.getProyectos();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Proyecto getProyectoPorId(@PathVariable String id) throws ESBException, JMSException {
		return this.service.getProyectoPorId(id);
	}
	
	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
	public @ResponseBody Proyecto getProyectoPorNombre(@PathVariable String nombre) throws ESBException, JMSException {
		return this.service.getProyectoByName(nombre);
	}
	
	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
	public @ResponseBody Proyecto getProyectoPorCodigo(@PathVariable String codigo) throws ESBException, JMSException {
		return this.service.getProyectoPorCodigo(codigo);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Proyecto createProyecto(@RequestBody Proyecto proyecto) throws ESBException, JMSException {	
		return this.service.createProyecto(proyecto);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Proyecto updateProyecto(@RequestBody Proyecto proyecto) throws ESBException, JMSException {
		return this.service.updateProyecto(proyecto);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteProyecto(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteProyecto(id);
	}
}
