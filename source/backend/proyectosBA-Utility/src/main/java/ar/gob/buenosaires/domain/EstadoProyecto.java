package ar.gob.buenosaires.domain;

public enum EstadoProyecto {

	COMPLETO("Completo"), INCOMPLETO("Incompleto"), PRESENTADO("Presentado"), BORRADOR("Borrador"), PREAPROBADO("Pre Aprobado"), PREAPROBADO_COMPLETO(
			"Pre Aprobado Completo"), VERIFICADO("Verificado"), DEMORADO("Demorado"), ENPRIORIZACION("En Priorización"), CANCELADO(
			"Cancelado"), RECHAZADO("Rechazado"), APROBADO("Aprobado"), D_COMPLETO("D. Completo"), D_INCOMPLETO("D. Incompleto"), D_PRESENTADO("D. Presentado"),
			D_RECHAZADO("D. Rechazado"), D_MODIFICABLE("D. Modificable");

	private String name;

	EstadoProyecto(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

}
