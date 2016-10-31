package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "obras" })
@XmlRootElement(name = "ObraRespMsg")
public class ObraRespMsg extends EsbBaseMsg {
	
	public static final String OBRA_TYPE = "ObraRespMsg";
	
	private List<Obra> obras;

	@Override
	public String getEventType() {
		return OBRA_TYPE;
	}

	public List<Obra> getObras() {
		if(obras == null){
			obras = new ArrayList<Obra>();
		}
		return obras;
	}

	public void setObras(List<Obra> obras) {
		this.obras = obras;
	}
}
