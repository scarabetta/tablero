package ar.gob.buenosaires.esb.domain.message;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.ReporteProyectosView;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "id", "name", "reporteProyectosView", "codigo" })
@XmlRootElement(name = "ReporteProyectosViewReqMsg")
public class ReporteProyectosViewReqMsg extends EsbBaseMsg {

	public static final String REPORTE_PROYECTOS_VIEW_TYPE = "ReporteProyectosViewReqMsg";

	private Long id;
	private String name;
	private ReporteProyectosView reporteProyectosView;
	private String codigo;
	private String mailUsuario;

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

	public ReporteProyectosView getArea() {
		return reporteProyectosView;
	}

	public void setArea(ReporteProyectosView reporteProyectosView) {
		this.reporteProyectosView = reporteProyectosView;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getMailUsuario() {
		return mailUsuario;
	}

	public void setMailUsuario(String mailUsuario) {
		this.mailUsuario = mailUsuario;
	}
}
