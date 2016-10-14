package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "temasTransversales" })
@XmlRootElement(name = "TemaTransversaRespMsg")
public class TemaTransversalRespMsg extends EsbBaseMsg {

	public static final String TEMA_TRANSVERSAL_TYPE = "TemaTransversalRespMsg";

	private List<TemaTransversal> temasTransversales;

	public List<TemaTransversal> getTemasTransversales() {
		if (temasTransversales == null) {
			temasTransversales = new ArrayList<>();
		}
		return temasTransversales;
	}

	public void setTemasTransversales(List<TemaTransversal> temasTransversales) {
		this.temasTransversales = temasTransversales;
	}

	@Override
	public String getEventType() {
		return TEMA_TRANSVERSAL_TYPE;
	}
}
