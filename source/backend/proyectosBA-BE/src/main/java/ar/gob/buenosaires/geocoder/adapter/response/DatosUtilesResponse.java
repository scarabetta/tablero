package ar.gob.buenosaires.geocoder.adapter.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DatosUtilesResponse {

	DatoUtil datoUtil;

	public DatosUtilesResponse() {
	}

	public DatosUtilesResponse(DatoUtil datoUtil) {
		super();
		this.datoUtil = datoUtil;
	}

	public DatoUtil getDatoUtil() {
		return datoUtil;
	}

	public void setDatoUtil(DatoUtil datoUtil) {
		this.datoUtil = datoUtil;
	}

}
