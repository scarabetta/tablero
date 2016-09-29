package ar.gob.buenosaires.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JurisdiccionResumen {

	private long idJurisdiccion;
	private String nombre;
	private String abreviatura;		
	private String mision;		
	private String codigo;		
	private List<Area> areas;		

	public JurisdiccionResumen() {
		super();
	}

	public JurisdiccionResumen(long idJurisdiccion, String nombre, String abreviatura, String mision, String codigo) {
		super();
		this.idJurisdiccion = idJurisdiccion;
		this.nombre = nombre;
		this.abreviatura = abreviatura;
		this.mision = mision;
		this.codigo = codigo;
	}

	public long getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public void setIdJurisdiccion(long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getAbreviatura() {
		return abreviatura;
	}

	public void setAbreviatura(String abreviatura) {
		this.abreviatura = abreviatura;
	}

	public String getMision() {
		return mision;
	}

	public void setMision(String mision) {
		this.mision = mision;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<Area> getAreas() {
		return areas;
	}

	public void setAreas(List<Area> areas) {
		this.areas = areas;
	}	
}
