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

import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.ObjetivoJurisdiccionalService;
import ar.gob.buenosaires.util.DSUtils;

@RestController
@RequestMapping("/api/objetivoJurisdiccional")
public class ObjetivoJurisdiccionalController {

	@Autowired
	private ObjetivoJurisdiccionalService service;

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<ObjetivoJurisdiccional> getObjetivosJurisdiccionales() throws ESBException, JMSException {
		return this.service.getObjetivosJurisdiccionales();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoJurisdiccional getObjetivoJurisdiccionalPorId(@PathVariable String id)
			throws ESBException, JMSException {
		return this.service.getObjetivoJurisdiccionalPorId(id);
	}

	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoJurisdiccional getObjetivoJurisdiccionalPorNombre(@PathVariable String nombre)
			throws ESBException, JMSException {
		return this.service.getObjetivoJurisdiccionalPorNombre(nombre);
	}

	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
	public @ResponseBody ObjetivoJurisdiccional getObjetivoJurisdiccionalPorCodigo(@PathVariable String codigo)
			throws ESBException, JMSException {
		return this.service.getObjetivoJurisdiccionalPorCodigo(codigo);
	}

	@RequestMapping(method = RequestMethod.POST)
	public ObjetivoJurisdiccional createObjetivoJurisdiccional(
			@RequestBody ObjetivoJurisdiccional objetivoJurisdiccional,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.createObjetivoJurisdiccional(objetivoJurisdiccional,
				DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public ObjetivoJurisdiccional updateObjetivoJurisdiccional(
			@RequestBody ObjetivoJurisdiccional objetivoJurisdiccional,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.updateObjetivoJurisdiccional(objetivoJurisdiccional,
				DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteObjetivoJurisdiccional(@PathVariable String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		this.service.deleteObjetivoJurisdiccional(id, DSUtils.getMailDelUsuarioDelToken(token));
	}

}
