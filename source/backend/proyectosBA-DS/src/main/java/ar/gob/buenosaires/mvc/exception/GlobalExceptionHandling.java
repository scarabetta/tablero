package ar.gob.buenosaires.mvc.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;

import ar.gob.buenosaires.esb.exception.ESBException;

@ControllerAdvice
public class GlobalExceptionHandling {

	private final Log logger = LogFactory.getLog(getClass());
	
	@ExceptionHandler(ESBException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	public ModelAndView erroresDelBus(HttpServletRequest req, ESBException exception) throws Exception {
		ModelAndView model = new ModelAndView(new MappingJackson2JsonView());
		model.addObject("status", exception.getStatus());
		model.addObject("codigoError", exception.getCodigoError());
		model.addObject("mensajeError", exception.getMessage());
		return model;
	}
	
	@ExceptionHandler(Exception.class)
	public void general(HttpServletRequest req, Exception exception) throws Exception {
		logger.error(exception.getMessage(), exception);
		throw exception;
	}
}
