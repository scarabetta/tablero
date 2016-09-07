package ar.gob.buenosaires.domain;

public enum EstadoTemaTransversal {
	
	ACTIVO("Activo"), INACTIVO("Inactivo"), FINALIZADO("Finalizado");

    private String name;

    EstadoTemaTransversal(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

}
