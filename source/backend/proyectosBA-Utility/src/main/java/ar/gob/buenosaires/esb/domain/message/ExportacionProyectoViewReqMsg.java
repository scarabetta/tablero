package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ExportacionProyectoView;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "exportacionProyectoView", "codigo" })
@XmlRootElement(name = "ExportacionProyectoViewReqMsg")
public class ExportacionProyectoViewReqMsg extends EsbBaseMsg {

	public static final String EXPORTACION_PROYECTO_VIEW_TYPE = "ExportacionProyectoViewReqMsg";

	private Long id;
	private String name;
	private ExportacionProyectoView exportacionProyectoView;
	private String codigo;

	@Override
	public String getEventType() {
		return EXPORTACION_PROYECTO_VIEW_TYPE;
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

	public ExportacionProyectoView getArea() {
		return exportacionProyectoView;
	}

	public void setArea(ExportacionProyectoView exportacionProyectoView) {
		this.exportacionProyectoView = exportacionProyectoView;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}
}
