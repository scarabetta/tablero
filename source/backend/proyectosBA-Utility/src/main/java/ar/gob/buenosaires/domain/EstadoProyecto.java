package ar.gob.buenosaires.domain;

public enum EstadoProyecto {
	
	COMPLETO("Completo"), INCOMPLETO("Incompleto"), PRESENTADO("Presentado"), BORRADOR("Borrador");

    private String name;

    EstadoProyecto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
