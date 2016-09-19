package ar.gob.buenosaires.importador.proyecto;

import java.util.ArrayList;
import java.util.List;

public class ProyectoImportadoFallidoDTO extends ProyectoImportadoDTO {

	private int numeroFila;

	private List<String> mensajeErrores = new ArrayList<>();

	public int getNumeroFila() {
		return numeroFila;
	}

	public void setNumeroFila(int numeroFila) {
		this.numeroFila = numeroFila;
	}

	public List<String> getMensajeErrores() {
		return mensajeErrores;
	}

	public void setMensajeErrores(List<String> mensajeErrores) {
		this.mensajeErrores = mensajeErrores;
	}

	public void agregarMensajeError(String msgError) {
		getMensajeErrores().add(msgError);
	}

}
