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

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.OtrasEtiquetasService;
import ar.gob.buenosaires.util.DSUtils;

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
	public OtraEtiqueta createOtraEtiqueta(@RequestBody OtraEtiqueta OtraEtiqueta,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.createOtraEtiqueta(OtraEtiqueta, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public OtraEtiqueta updateOtraEtiqueta(@RequestBody OtraEtiqueta OtraEtiqueta,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.updateOtraEtiqueta(OtraEtiqueta, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteOtraEtiqueta(@PathVariable String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		this.service.deleteOtraEtiqueta(id, DSUtils.getMailDelUsuarioDelToken(token));
	}
}
