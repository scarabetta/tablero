package ar.gob.buenosaires.domain;

public enum AccionesProyecto {
	
	PRESENTAR("Presentar"), CANCELAR("Cancelar"), VERIFICAR("Verificar"), DESHACER_CANCELACION("Deshacer Cancelacion"),
	PREAPROBAR("Pre-aprobar"), DEMORAR("Demorar"), RECHAZAR("Rechazar"), DESHACER_PREAPROBACION("Deshacer Pre-Aprobacion"),
	REANUDAR("Reanudar"), DESHACER_RECHAZO("Deshacer Rechazo");

    private String name;

    AccionesProyecto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    public String getNameSinEspacios() {
        return name.replaceAll("\\s","");
    }

}
