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

import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.ObraService;
import ar.gob.buenosaires.util.DSUtils;

@RestController
@RequestMapping("/api/obra")
public class ObraController {

	@Autowired
	private ObraService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Obra> getObras() throws ESBException, JMSException {
		return this.service.getObras();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Obra getObraPorId(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getObraPorId(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Obra createObra(@RequestBody Obra obra, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.createObra(obra, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Obra updateObra(@RequestBody Obra obra, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.updateObra(obra, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteObra(@PathVariable String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		this.service.deleteObra(id, DSUtils.getMailDelUsuarioDelToken(token));
	}
}
