package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ExportacionProyectoView;
import ar.gob.buenosaires.domain.ReporteProyectosView;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "reporteProyectosView" })
@XmlRootElement(name = "ReporteProyectosViewRespMsg")
public class ReporteProyectosViewRespMsg extends EsbBaseMsg {

	public static final String REPORTE_PROYECTOS_VIEW_TYPE = "ReporteProyectosViewRespMsg";

	private Long id;
	private String name;
	@XmlElement(name = "reporteProyectosView")
	private List<ReporteProyectosView> reporteProyectosView = new ArrayList<>();

	@Override
	public String getEventType() {
		return REPORTE_PROYECTOS_VIEW_TYPE;
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

	public List<ReporteProyectosView> getReporteProyectosViews() {
		if (reporteProyectosView == null) {
			reporteProyectosView = new ArrayList<>();
		}
		return reporteProyectosView;
	}

	public void setReporteProyectosViews(List<ReporteProyectosView> reporteProyectosView) {
		this.reporteProyectosView = reporteProyectosView;
	}
}
