package ar.gob.buenosaires.geocoder.adapter.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DireccionNormalizada {
	
	private int altura;
	private int codCalle;
	private int codCalleCruce;
	private int codPartido;
	private GeoCoderCoordenadas coordenadas;
	private String direccion;
	private String nombreCalle;
	private String nombreCalleCruce;
	private String nombreLocalidad;
	private String nombrePartido;
	private String tipo;
	
	
	public DireccionNormalizada() {}


	public DireccionNormalizada(int altura, int codCalle, int codCalleCruce,
			int codPartido, GeoCoderCoordenadas coordenadas, String direccion,
			String nombreCalle, String nombreCalleCruce,
			String nombreLocalidad, String nombrePartido, String tipo) {
		
		this.altura = altura;
		this.codCalle = codCalle;
		this.codCalleCruce = codCalleCruce;
		this.codPartido = codPartido;
		this.coordenadas = coordenadas;
		this.direccion = direccion;
		this.nombreCalle = nombreCalle;
		this.nombreCalleCruce = nombreCalleCruce;
		this.nombreLocalidad = nombreLocalidad;
		this.nombrePartido = nombrePartido;
		this.tipo = tipo;
	}


	public int getAltura() {
		return altura;
	}


	public void setAltura(int altura) {
		this.altura = altura;
	}


	public int getCodCalle() {
		return codCalle;
	}


	public void setCodCalle(int codCalle) {
		this.codCalle = codCalle;
	}


	public int getCodCalleCruce() {
		return codCalleCruce;
	}


	public void setCodCalleCruce(int codCalleCruce) {
		this.codCalleCruce = codCalleCruce;
	}


	public int getCodPartido() {
		return codPartido;
	}


	public void setCodPartido(int codPartido) {
		this.codPartido = codPartido;
	}


	public GeoCoderCoordenadas getCoordenadas() {
		return coordenadas;
	}


	public void setCoordenadas(GeoCoderCoordenadas coordenadas) {
		this.coordenadas = coordenadas;
	}


	public String getDireccion() {
		return direccion;
	}


	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}


	public String getNombreCalle() {
		return nombreCalle;
	}


	public void setNombreCalle(String nombreCalle) {
		this.nombreCalle = nombreCalle;
	}


	public String getNombreCalleCruce() {
		return nombreCalleCruce;
	}


	public void setNombreCalleCruce(String nombreCalleCruce) {
		this.nombreCalleCruce = nombreCalleCruce;
	}


	public String getNombreLocalidad() {
		return nombreLocalidad;
	}


	public void setNombreLocalidad(String nombreLocalidad) {
		this.nombreLocalidad = nombreLocalidad;
	}


	public String getNombrePartido() {
		return nombrePartido;
	}


	public void setNombrePartido(String nombrePartido) {
		this.nombrePartido = nombrePartido;
	}


	public String getTipo() {
		return tipo;
	}


	public void setTipo(String tipo) {
		this.tipo = tipo;
	}		
}
