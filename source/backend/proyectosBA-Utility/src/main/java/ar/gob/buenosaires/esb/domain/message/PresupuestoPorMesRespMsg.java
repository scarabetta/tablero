package ar.gob.buenosaires.esb.domain.message;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import ar.gob.buenosaires.domain.PresupuestoPorMes;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(propOrder = { "ppms" })
@XmlRootElement(name = "PresupuestoPorMesRespMsg")
public class PresupuestoPorMesRespMsg extends EsbBaseMsg {
	
    public static final String PPM_TYPE =
            "PresupuestoPorMesRespMsg";

    private List<PresupuestoPorMes> ppms;

	@Override
	public String getEventType() {
		return PPM_TYPE;
	}

	public List<PresupuestoPorMes> getPpms() {
		if(ppms == null){
			ppms = new ArrayList<PresupuestoPorMes>();
		}
		return ppms;
	}

	public void setPpms(List<PresupuestoPorMes> ppms) {
		this.ppms = ppms;
	}
}
