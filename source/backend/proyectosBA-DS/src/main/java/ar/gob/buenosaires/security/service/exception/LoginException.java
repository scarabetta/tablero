package ar.gob.buenosaires.security.service.exception;

public class LoginException extends Exception {

	private static final long serialVersionUID = -5868325473613179531L;

	public LoginException() {
	}

	public LoginException(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 */
	public LoginException(Throwable cause) {
		super(cause);
	}
}