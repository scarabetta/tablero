package ar.gob.buenosaires.domain;

public enum EstadoProyecto {

	COMPLETO("Completo"), INCOMPLETO("Incompleto"), PRESENTADO("Presentado"), BORRADOR("Borrador"), 
	APLAZADO("Aplazado"), PREAPROBADO("Pre Aprobado"), VERIFICADO("Verificado"), 
	ENPRIORIZACION("En Priorización"), CANCELADO("Cancelado"), RECHAZADO("Rechazado"),
	APROBADO("Aprobado");

	private String name;

	EstadoProyecto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
