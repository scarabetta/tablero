package ar.gob.buenosaires.geocoder.adapter.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

import ar.gob.buenosaires.esb.util.JSonMapperSingleton;
import ar.gob.buenosaires.geocoder.adapter.GeoCoderAdapter;
import ar.gob.buenosaires.geocoder.adapter.response.DatoUtil;
import ar.gob.buenosaires.geocoder.adapter.response.DatosUtilesResponse;
import ar.gob.buenosaires.geocoder.adapter.response.GeoCoderResponse;
import ar.gob.buenosaires.geocoder.adapter.response.SeccionManzanaParcela;
import ar.gob.buenosaires.geocoder.adapter.response.SeccionManzanaParcelaResponse;

@Service
@Profile({ "dev", "prod" })
public class GeoCoderAdapterImpl implements GeoCoderAdapter {

	private final static Logger LOGGER = LoggerFactory.getLogger(GeoCoderAdapterImpl.class);
	private final static String URL_USIG = "http://servicios.usig.buenosaires.gob.ar/normalizar/?direccion=";
	private final static String AREA_CABA = ",caba";
	private final static String CODIFICAR = "&geocodificar=TRUE";

	private final static String URL_USIG_DATOS_UTILES = "http://ws.usig.buenosaires.gob.ar/datos_utiles?calle=";
	private final static String ALTURA_USIG_DATOS_UTILES = "&altura=";

	private final static String URL_SMP_USIG = "http://ws.usig.buenosaires.gob.ar/geocoder/2.2/smp?cod_calle=";
	private final static String ALTURA_SMP = "&altura=";

	public GeoCoderResponse normalizarYGeoCodificar(String direccion) {
		RestTemplate restTemplate = new RestTemplate();
		GeoCoderResponse response = null;
		try {
			response = restTemplate.getForObject(URL_USIG + direccion + AREA_CABA + CODIFICAR, GeoCoderResponse.class);
		} catch (Exception e) {
			LOGGER.error("Se produjo un error al obtener las coordenadas para la direccion");
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public DatosUtilesResponse obtenerDatosUtiles(String nombreCalle, int altura) {
		RestTemplate restTemplate = new RestTemplate();
		DatosUtilesResponse response = new DatosUtilesResponse();
		DatoUtil datoUtil;
		try {
			String jsonText = restTemplate.getForObject(URL_USIG_DATOS_UTILES + nombreCalle + ALTURA_USIG_DATOS_UTILES + altura + CODIFICAR,
					String.class);
			ObjectMapper mapper = JSonMapperSingleton.getInstance().getMapper();
			datoUtil = mapper.readValue(jsonText, DatoUtil.class);
			response.setDatoUtil(datoUtil);
		} catch (Exception e) {
			LOGGER.error("Se produjo un error al obtener los datos para la calle y altura");
			e.printStackTrace();
		}

		return response;
	}

	@Override
	public SeccionManzanaParcelaResponse obtenerSeccionManzanaParcela(int codCalle, int altura) {
		RestTemplate restTemplate = new RestTemplate();
		SeccionManzanaParcelaResponse response = new SeccionManzanaParcelaResponse();
		try {
			String jsonText = restTemplate.getForObject(URL_SMP_USIG + codCalle + ALTURA_SMP + altura,
					String.class);
			ObjectMapper mapper = JSonMapperSingleton.getInstance().getMapper();
			SeccionManzanaParcela smp;
			try{
				smp = mapper.readValue(jsonText.replace("(", "").replace(")", ""), SeccionManzanaParcela.class);
			} catch (Exception ep) {
				smp = null;
			}
			response.setSmp(smp); 
		} catch (Exception e) {
			LOGGER.error(
					"Se produjo un error al obtener los datos de seccion, manzana y parcela para el codigo de calle y altura");
			e.printStackTrace();
		}

		return response;
	}

}