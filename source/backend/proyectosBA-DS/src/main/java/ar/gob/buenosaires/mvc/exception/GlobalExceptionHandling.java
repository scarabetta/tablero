package ar.gob.buenosaires.mvc.exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandling {

	private final Log logger = LogFactory.getLog(getClass());
	
	@ExceptionHandler(Exception.class)
	public void general(HttpServletRequest req, Exception exception) throws Exception {
		logger.error(exception.getMessage(), exception);
		throw exception;
	}
}
