package ar.gob.buenosaires.geocoder.adapter.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class SeccionManzanaParcelaResponse {

	SeccionManzanaParcela smp;

	public SeccionManzanaParcelaResponse() {
	}

	public SeccionManzanaParcelaResponse(SeccionManzanaParcela smp) {
		super();
		this.smp = smp;
	}

	public SeccionManzanaParcela getSmp() {
		return smp;
	}

	public void setSmp(SeccionManzanaParcela smp) {
		this.smp = smp;
	}



}
