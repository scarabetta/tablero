package ar.gob.buenosaires.geocoder.service.mock;

import java.util.ArrayList;

import ar.gob.buenosaires.geocoder.adapter.response.DatosUtilesResponse;
import ar.gob.buenosaires.geocoder.adapter.response.DireccionNormalizada;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderCoordenadas;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.adapter.response.SeccionManzanaParcelaResponse;
import ar.gob.buenosaires.geocoder.service.GeoCoderService;

public class GeoCoderserviceMock implements GeoCoderService{

	@Override
	public GeoCoderResponse getGeoCoding(String direccion) {
		DireccionNormalizada dn = new DireccionNormalizada();
		dn.setDireccion("Test 123, CABA");
		GeoCoderCoordenadas coordenadas = new GeoCoderCoordenadas();
		coordenadas.setX("1.0001");
		coordenadas.setY("2.002");
		dn.setCoordenadas(coordenadas);
		GeoCoderResponse geoCoderResponse = new GeoCoderResponse();
		geoCoderResponse.setDireccionesNormalizadas(new ArrayList<DireccionNormalizada>());
		return geoCoderResponse;
	}

	@Override
	public DatosUtilesResponse getDatoUtil(String nombreCalle, int altura) {
		return new DatosUtilesResponse();
	}

	@Override
	public SeccionManzanaParcelaResponse getSeccionManzanaParcela(int codCalle, int altura) {
		return new SeccionManzanaParcelaResponse();
	}

}
