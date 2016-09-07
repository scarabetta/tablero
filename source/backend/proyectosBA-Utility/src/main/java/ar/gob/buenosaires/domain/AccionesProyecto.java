package ar.gob.buenosaires.domain;

public enum AccionesProyecto {
	
	PRESENTAR("Presentar"), CANCELAR("Cancelar"), VERIFICAR("Verificar"), DESHACER_CANCELACION("DeshacerCancelacion");

    private String name;

    AccionesProyecto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
