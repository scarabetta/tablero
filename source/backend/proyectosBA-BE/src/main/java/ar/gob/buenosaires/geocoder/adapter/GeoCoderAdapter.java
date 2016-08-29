package ar.gob.buenosaires.geocoder.adapter;

import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;


public interface GeoCoderAdapter {
	
	public GeoCoderResponse normalizarYGeoCodificar(String direccion);
    
}
