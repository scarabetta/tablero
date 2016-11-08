package ar.gob.buenosaires.geocoder.adapter;

import ar.gob.buenosaires.geocoder.adapter.response.DatosUtilesResponse;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.adapter.response.SeccionManzanaParcelaResponse;

public interface GeoCoderAdapter {

	public GeoCoderResponse normalizarYGeoCodificar(String direccion);

	public DatosUtilesResponse obtenerDatosUtiles(String nombreCalle, int altura);

	public SeccionManzanaParcelaResponse obtenerSeccionManzanaParcela(int codCalle, int altura);

}
