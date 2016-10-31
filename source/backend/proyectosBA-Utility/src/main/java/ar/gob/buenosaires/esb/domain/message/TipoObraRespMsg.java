package ar.gob.buenosaires.esb.domain.message;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.TipoObra;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "tiposObras" })
@XmlRootElement(name = "TipoObraRespMsg")
public class TipoObraRespMsg extends EsbBaseMsg {

	public static final String TIPO_OBRA_TYPE = "TipoObraRespMsg";
	
	private List<TipoObra> tiposObras;
	
	@Override
	public String getEventType() {
		return TIPO_OBRA_TYPE;
	}

	public List<TipoObra> getTiposObras() {
		return tiposObras;
	}

	public void setTiposObras(List<TipoObra> tiposObras) {
		this.tiposObras = tiposObras;
	}
}
