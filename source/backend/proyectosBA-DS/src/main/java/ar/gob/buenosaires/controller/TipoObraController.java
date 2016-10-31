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

import ar.gob.buenosaires.domain.TipoObra;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.TipoObraService;
import ar.gob.buenosaires.util.DSUtils;

@RestController
@RequestMapping("/api/tipoObra")
public class TipoObraController {

	@Autowired
	private TipoObraService service;
	
	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<TipoObra> getTiposObras() throws ESBException, JMSException {
		return this.service.getTiposObras();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody TipoObra getTipoObraPorId(@PathVariable Long id) throws ESBException, JMSException {
		return this.service.getTipoObraPorId(id);
	}

	@RequestMapping(method = RequestMethod.POST)
	public TipoObra createTipoObra(@RequestBody TipoObra tipoObra, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.createTipoObra(tipoObra, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(method = RequestMethod.PUT)
	public TipoObra updateTipoObra(@RequestBody TipoObra tipoObra, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		return this.service.updateTipoObra(tipoObra, DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void TipodeleteObra(@PathVariable String id,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		this.service.deleteTipoObra(id, DSUtils.getMailDelUsuarioDelToken(token));
	}
}
