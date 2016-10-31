package ar.gob.buenosaires.controller;

import java.text.ParseException;
import java.util.List;

import javax.jms.JMSException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.domain.PresupuestoPorMes;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.PresupuestoPorMesService;
import ar.gob.buenosaires.util.DSUtils;

@RestController
@RequestMapping("/api/presupuestoPorMes")
public class PresupuestoPorMesProyectoController {
	
	@Autowired
	private PresupuestoPorMesService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<PresupuestoPorMes> getPresupuestosPorMes() throws ESBException, JMSException {
		return this.service.getPresupuestosPorMes();
	}
	
	@RequestMapping(path = "/proyecto/{id}",method = RequestMethod.GET)
	public @ResponseBody List<PresupuestoPorMes> getPresupuestosPorMesPorProyecto(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getPresupuestosPorMesPorProyecto(id);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody PresupuestoPorMes getPresupuestoPorMesPorId(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getPresupuestoPorMesPorId(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public PresupuestoPorMes createPresupuestoPorMes(@RequestBody PresupuestoPorMes ppm, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.createPresupuestoPorMes(ppm, DSUtils.getMailDelUsuarioDelToken(token));
	}
	
	@RequestMapping(path = "/list",method = RequestMethod.POST)
	public List<PresupuestoPorMes> createPresupuestosPorMes(@RequestBody List<PresupuestoPorMes> ppms, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.createPresupuestosPorMes(ppms, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public PresupuestoPorMes updatePresupuestoPorMes(@RequestBody PresupuestoPorMes ppm, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.updatePresupuestoPorMes(ppm, DSUtils.getMailDelUsuarioDelToken(token));
	}
	
	@RequestMapping(path = "/list", method = RequestMethod.PUT)
	public List<PresupuestoPorMes> updatePresupuestosPorMes(@RequestBody List<PresupuestoPorMes> ppms, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.updatePresupuestosPorMes(ppms, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deletePresupuestoPorMes(@PathVariable String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		this.service.deletePresupuestoPorMes(id, DSUtils.getMailDelUsuarioDelToken(token));
	}

}
