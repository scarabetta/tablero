package ar.gob.buenosaires.esb.exception;

public class ESBException extends Exception {

	private static final long serialVersionUID = -5868325473613179531L;

	public ESBException() {
	}

	public ESBException(String msg) {
		super(msg);
	}

	/**
	 * @param cause
	 */
	public ESBException(Throwable cause) {
		super(cause);
	}
}
