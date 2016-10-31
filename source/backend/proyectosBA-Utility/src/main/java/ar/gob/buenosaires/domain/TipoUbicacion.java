package ar.gob.buenosaires.domain;

public enum TipoUbicacion {

	DIRECCION("Direccion"), TRAMO("Tramo"), OTRO("Otro");

    private String name;

    TipoUbicacion(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
}
