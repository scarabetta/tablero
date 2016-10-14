package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.EtiquetasMsg;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "accion", "proyecto", "codigo", "estados", "idJurisdiccion", "usuario", "etiquetas" })
@XmlRootElement(name = "ProyectoReqMsg")
public class ProyectoReqMsg extends EsbBaseMsg {

	public static final String PROYECTO_TYPE = "ProyectoReqMsg";

	private Long id;
	private String name;
	private String accion;
	private Proyecto proyecto;
	private String codigo;
	private Usuario usuario;
	private EtiquetasMsg etiquetas;

	@XmlElement(name = "estados")
	private List<String> estados;

	private Long idJurisdiccion;

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

	public String getAccion() {
		return accion;
	}

	public void setAccion(String accion) {
		this.accion = accion;
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
		if (estados == null) {
			estados = new ArrayList<>();
		}
		return estados;
	}

	public void setEstados(List<String> estados) {
		this.estados = estados;
	}

	public void setIdJurisdiccion(Long idJurisdiccion) {
		this.idJurisdiccion = idJurisdiccion;
	}
	
	public Long getIdJurisdiccion() {
		return idJurisdiccion;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public EtiquetasMsg getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(EtiquetasMsg etiquetas) {
		this.etiquetas = etiquetas;
	}
	
}
