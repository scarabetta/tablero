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

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.OtrasEtiquetasService;

@RestController
@RequestMapping("/api/otrasEtiquetas")
public class OtrasEtiquetasController {

	@Autowired
	private OtrasEtiquetasService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<OtraEtiqueta> getOtrasEtiquetas() throws ESBException, JMSException {
		return this.service.getOtrasEtiquetas();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody OtraEtiqueta getOtradEtiquetadPorId(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getOtraEtiquetaPorId(id);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public OtraEtiqueta createOtraEtiqueta(@RequestBody OtraEtiqueta OtraEtiqueta) throws ESBException, JMSException {	
		return this.service.createOtraEtiqueta(OtraEtiqueta);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	public OtraEtiqueta updateOtraEtiqueta(@RequestBody OtraEtiqueta OtraEtiqueta) throws ESBException, JMSException {
		return this.service.updateOtraEtiqueta(OtraEtiqueta);
	}
	
	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteOtraEtiqueta(@PathVariable String id) throws ESBException, JMSException {
		this.service.deleteOtraEtiqueta(id);
	}
}
