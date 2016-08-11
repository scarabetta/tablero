package ar.gob.buenosaires.importador;

public class MensajeError {

	public static final String TIPO_ERROR = "error";
	public static final String TIPO_WARNING = "alerta";

	private String tipoError;
	private String mensaje;

	public MensajeError(String tipoError, String mensaje) {
		this.tipoError = tipoError;
		this.mensaje = mensaje;

	}

	public String getTipoError() {
		return tipoError;
	}

	public void setTipoError(String tipoError) {
		this.tipoError = tipoError;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	@Override
	public String toString() {
		return this.tipoError + " - " + this.mensaje;
	}

}
