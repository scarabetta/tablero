package ar.gob.buenosaires.geocoder.adapter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import ar.gob.buenosaires.geocoder.adapter.GeoCoderAdapter;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
@Service
@Profile({"dev", "prod"})
public class GeoCoderAdapterImpl implements GeoCoderAdapter {
	
	private final static Logger LOGGER = LoggerFactory.getLogger(GeoCoderAdapterImpl.class);
	private final static String URL_USIG = "http://servicios.usig.buenosaires.gob.ar/normalizar/?direccion=";
	private final static String AREA_CABA = ",caba";
	private final static String CODIFICAR = "&geocodificar=TRUE";
	
	public GeoCoderResponse normalizarYGeoCodificar(String direccion) {
		RestTemplate restTemplate = new RestTemplate();
		GeoCoderResponse response = null;
		try {
			response = restTemplate.getForObject(URL_USIG + direccion + AREA_CABA + CODIFICAR,
							GeoCoderResponse.class);
		}catch(Exception e){
			LOGGER.error("Se produjo un error al obtener las coordenadas para la direccion");
			e.printStackTrace();			
		}
		
		return response;		
	}

}