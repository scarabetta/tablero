package ar.gob.buenosaires.geocoder.service;

import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;

public interface GeoCoderService {

	public GeoCoderResponse getGeoCoding(String direccion);
}
