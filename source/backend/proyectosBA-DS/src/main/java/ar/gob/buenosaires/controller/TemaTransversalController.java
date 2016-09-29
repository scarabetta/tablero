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

import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.TemaTransversalService;
import ar.gob.buenosaires.util.DSUtils;

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
	public @ResponseBody TemaTransversal getTemaTransversalPorId(@PathVariable Long id)
			throws ESBException, JMSException {
		return this.service.getTemaTransversalPorId(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public TemaTransversal createTemaTransversal(@RequestBody TemaTransversal temaTransversal,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.createTemaTransversal(temaTransversal, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public TemaTransversal updateTemaTransversal(@RequestBody TemaTransversal temaTransversal,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.updateTemaTransversal(temaTransversal, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteTemaTransversal(@PathVariable String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		this.service.deleteTemaTransversal(id, DSUtils.getMailDelUsuarioDelToken(token));
	}
}
