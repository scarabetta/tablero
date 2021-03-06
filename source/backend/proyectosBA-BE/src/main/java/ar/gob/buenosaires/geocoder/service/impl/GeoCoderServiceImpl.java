package ar.gob.buenosaires.geocoder.service.impl;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.geocoder.adapter.GeoCoderAdapter;
import ar.gob.buenosaires.geocoder.adapter.impl.GeoCoderAdapterImpl;
import ar.gob.buenosaires.geocoder.adapter.response.DatosUtilesResponse;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.adapter.response.SeccionManzanaParcelaResponse;
import ar.gob.buenosaires.geocoder.service.GeoCoderService;

@Service
@Profile({ "dev", "prod" })
public class GeoCoderServiceImpl implements GeoCoderService {

	@Autowired
	GeoCoderAdapter geoCoderAdapter;

	@Override
	public GeoCoderResponse getGeoCoding(String direccion) {
		return geoCoderAdapter.normalizarYGeoCodificar(direccion);
	}

	@Override
	public DatosUtilesResponse getDatoUtil(String nombreCalle, int altura) {
		return geoCoderAdapter.obtenerDatosUtiles(nombreCalle, altura);
	}

	@Override
	public SeccionManzanaParcelaResponse getSeccionManzanaParcela(int codCalle, int altura) {
		return geoCoderAdapter.obtenerSeccionManzanaParcela(codCalle, altura);
	};

	@VisibleForTesting
	public void setGeoCoderAdapter(GeoCoderAdapterImpl adapter) {
		this.geoCoderAdapter = adapter;
	}

}
