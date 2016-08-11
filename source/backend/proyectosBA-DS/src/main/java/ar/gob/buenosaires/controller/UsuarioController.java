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

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.UsuarioService;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Usuario> getUsuarios() throws ESBException, JMSException {
		return this.service.getUsuarios();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Usuario getUsuarioPorId(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getUsuarioPorId(id);
	}
	
	@RequestMapping(path = "/email/{email}", method = RequestMethod.GET)
	public @ResponseBody Usuario getCategoriaPorNombre(@PathVariable String email) throws ESBException, JMSException {
		return this.service.getUsuarioByEmail(email);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public Usuario createUsuario(@RequestBody Usuario usuario) throws ESBException, JMSException {	
		return this.service.createUsuario(usuario);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public Usuario updateUsuario(@RequestBody Usuario usuario) throws ESBException, JMSException {
		return this.service.updateUsuario(usuario);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteUsuario(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteUsuario(id);
	}
}
