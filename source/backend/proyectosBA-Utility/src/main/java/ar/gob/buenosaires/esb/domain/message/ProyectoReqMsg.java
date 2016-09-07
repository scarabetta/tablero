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
@XmlType(propOrder = { "id", "name", "proyecto", "codigo", "estados" })
@XmlRootElement(name = "ProyectoReqMsg")
public class ProyectoReqMsg extends EsbBaseMsg {
	
    public static final String PROYECTO_TYPE = "ProyectoReqMsg";
    
    private Long id;
    private String name;
    private Proyecto proyecto;
    private String codigo;
    
    @XmlElement(name = "estados")
    private List<String> estados;

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

	public Proyecto getProyecto() {
		return proyecto;
	}

	public void setProyecto(Proyecto proyecto) {
		this.proyecto = proyecto;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public List<String> getEstados() {
		if(estados == null){
			estados = new ArrayList<String>();
		}
		return estados;
	}

	public void setEstados(List<String> estados) {
		this.estados = estados;
	}
}
