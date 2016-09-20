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

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.CompromisoPublicoService;

@RestController
@RequestMapping("/api/compromisoPublico")
public class CompromisoPublicoController {

	@Autowired
	private CompromisoPublicoService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<CompromisoPublico> getCompromisosPublicos() throws ESBException, JMSException {
		return this.service.getCompromisosPublicos();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody CompromisoPublico getCompromisoPublicoPorId(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getCompromisoPublicoPorId(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public CompromisoPublico createCompromisoPublico(@RequestBody CompromisoPublico compromisoPublico) throws ESBException, JMSException {	
		return this.service.createCompromisoPublico(compromisoPublico);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public CompromisoPublico updateCompromisoPublico(@RequestBody CompromisoPublico compromisoPublico) throws ESBException, JMSException {
		return this.service.updateCompromisoPublico(compromisoPublico);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteCompromisoPublico(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteCompromisoPublico(id);
	}
}
