package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "proyectos", "accionesPermitidas", "prioridadesJefatura", "resumenProyectosPriorizacion" })
@XmlRootElement(name = "ProyectoRespMsg")

public class ProyectoRespMsg extends EsbBaseMsg {

	public static final String PROYECTO_TYPE = "ProyectoRespMsg";

	private Long id;
	private String name;
	@XmlElement(name = "proyectos")
	private List<Proyecto> proyectos;
	@XmlElement(name = "accionesPermitidas")
	private List<String> accionesPermitidas;
	@XmlElement(name = "prioridadesJefatura")
	private List<String> prioridadesJefatura;
	private String resumenProyectosPriorizacion;

	@Override
	public String getEventType() {
		return PROYECTO_TYPE;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Proyecto> getProyectos() {
		return proyectos;
	}

	public void setProyectos(List<Proyecto> proyectos) {
		if (proyectos == null) {
			proyectos = new ArrayList<>();
		}
		this.proyectos = proyectos;
	}

	public List<String> getAccionesPermitidas() {
		if (accionesPermitidas == null) {
			accionesPermitidas = new ArrayList<>();
		}
		return accionesPermitidas;
	}

	public void setAccionesPermitidas(List<String> accionesPermitidas) {
		this.accionesPermitidas = accionesPermitidas;
	}

	public List<String> getPrioridadesJefatura() {
		if(prioridadesJefatura == null){
			prioridadesJefatura = new ArrayList<String>();
		}
		return prioridadesJefatura;
	}

	public void setPrioridadesJefatura(List<String> prioridadesJefatura) {
		this.prioridadesJefatura = prioridadesJefatura;
	}

	public String getResumenProyectosPriorizacion() {
		return resumenProyectosPriorizacion;
	}

	public void setResumenProyectosPriorizacion(String resumenProyectosPriorizacion) {
		this.resumenProyectosPriorizacion = resumenProyectosPriorizacion;
	}
}
