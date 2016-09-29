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

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.CompromisoPublicoService;
import ar.gob.buenosaires.util.DSUtils;

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
	public @ResponseBody CompromisoPublico getCompromisoPublicoPorId(@PathVariable Long id)
			throws ESBException, JMSException {
		return this.service.getCompromisoPublicoPorId(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public CompromisoPublico createCompromisoPublico(@RequestBody CompromisoPublico compromisoPublico,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.createCompromisoPublico(compromisoPublico, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public CompromisoPublico updateCompromisoPublico(@RequestBody CompromisoPublico compromisoPublico,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.updateCompromisoPublico(compromisoPublico, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteCompromisoPublico(@PathVariable String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		this.service.deleteCompromisoPublico(id, DSUtils.getMailDelUsuarioDelToken(token));
	}
}
