package ar.gob.buenosaires.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.domain.Payload;
import ar.gob.buenosaires.security.jwt.jsonview.configuration.JWTokenAuthViewConfiguration;
import ar.gob.buenosaires.security.service.AuthenticationService;
import ar.gob.buenosaires.security.service.exception.LoginException;

@RestController
@RequestMapping("/authentication")
public class AuthenticationController {

	@Autowired
	AuthenticationService authenticationService;
	
	@RequestMapping(path = "/login", method = RequestMethod.POST)
	@JsonView(JWTokenAuthViewConfiguration.class)
	public @ResponseBody JWToken authentication(@RequestBody Payload payload) throws JOSEException, LoginException {
		return authenticationService.userLogin(payload);
	}
	
}
