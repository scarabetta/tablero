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

import ar.gob.buenosaires.domain.Rol;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.RolService;

@RestController
@RequestMapping("/api/rol")
public class RolController {

	@Autowired
	private RolService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Rol> getRoles() throws ESBException, JMSException {
		return this.service.getRoles();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Rol getRolPorId(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getRolPorId(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Rol createRol(@RequestBody Rol Rol) throws ESBException, JMSException {	
		return this.service.createRol(Rol);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Rol updateJurisdiccion(@RequestBody Rol rol) throws ESBException, JMSException {
		return this.service.updateRol(rol);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteRol(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteRol(id);
	}
}
