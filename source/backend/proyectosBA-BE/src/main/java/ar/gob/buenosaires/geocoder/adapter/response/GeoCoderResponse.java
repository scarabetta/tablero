package ar.gob.buenosaires.geocoder.adapter.response;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeoCoderResponse {

	List<DireccionNormalizada> direccionesNormalizadas = new ArrayList<DireccionNormalizada>();

	public GeoCoderResponse() {
	}

	public GeoCoderResponse(List<DireccionNormalizada> direccionesNormalizadas) {
		this.direccionesNormalizadas = direccionesNormalizadas;
	}

	public List<DireccionNormalizada> getDireccionesNormalizadas() {
		return direccionesNormalizadas;
	}

	public void setDireccionesNormalizadas(List<DireccionNormalizada> direccionesNormalizadas) {
		this.direccionesNormalizadas = direccionesNormalizadas;
	}

}
