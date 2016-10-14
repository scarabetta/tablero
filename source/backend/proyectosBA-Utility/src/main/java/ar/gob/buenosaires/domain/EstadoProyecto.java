package ar.gob.buenosaires.domain;

public enum EstadoProyecto {

	COMPLETO("Completo"), INCOMPLETO("Incompleto"), PRESENTADO("Presentado"), BORRADOR("Borrador"), PREAPROBADO("Pre Aprobado"), PREAPROBADO_COMPLETO(
			"Pre Aprobado Completo"), VERIFICADO("Verificado"), DEMORADO("Demorado"), ENPRIORIZACION("En Priorización"), CANCELADO(
			"Cancelado"), RECHAZADO("Rechazado"), APROBADO("Aprobado");

	private String name;

	EstadoProyecto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
