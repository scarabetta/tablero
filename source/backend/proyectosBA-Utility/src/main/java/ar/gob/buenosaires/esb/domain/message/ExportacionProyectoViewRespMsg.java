package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ExportacionProyectoView;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "exportacionProyectoView" })
@XmlRootElement(name = "ExportacionProyectoViewRespMsg")
public class ExportacionProyectoViewRespMsg extends EsbBaseMsg {

	public static final String EXPORTACION_PROYECTO_VIEW_TYPE = "ExportacionProyectoViewRespMsg";

	private Long id;
	private String name;
	@XmlElement(name = "exportacionProyectoView")
	private List<ExportacionProyectoView> exportacionProyectoView = new ArrayList<>();

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

	public List<ExportacionProyectoView> getExportacionProyectoViews() {
		return exportacionProyectoView;
	}

	public void setExportacionProyectoViews(List<ExportacionProyectoView> exportacionProyectoView) {
		this.exportacionProyectoView = exportacionProyectoView;
	}
}
