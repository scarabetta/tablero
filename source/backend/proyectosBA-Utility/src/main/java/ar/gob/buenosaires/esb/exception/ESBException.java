package ar.gob.buenosaires.esb.exception;

public class ESBException extends Exception {

	private static final long serialVersionUID = -5868325473613179531L;
	private String codigoError = "555";
	private String status = "500";

	public ESBException() {
	}

	public ESBException(String msg) {
		super(msg);
	}		

	public ESBException(String codigoError, String msg) {
		super(msg);
		this.codigoError = codigoError;
	}
	
	public ESBException(Throwable cause) {
		super(cause);
	}

	public String getCodigoError() {
		return codigoError;
	}

	public void setCodigoError(String codigoError) {
		this.codigoError = codigoError;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}	
}
