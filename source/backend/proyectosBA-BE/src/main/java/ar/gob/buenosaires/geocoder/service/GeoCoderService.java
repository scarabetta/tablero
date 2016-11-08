package ar.gob.buenosaires.geocoder.service;

import ar.gob.buenosaires.geocoder.adapter.response.DatosUtilesResponse;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.adapter.response.SeccionManzanaParcelaResponse;

public interface GeoCoderService {

	public GeoCoderResponse getGeoCoding(String direccion);

	public DatosUtilesResponse getDatoUtil(String nombreCalle, int altura);

	public SeccionManzanaParcelaResponse getSeccionManzanaParcela(int codCalle, int altura);
}
