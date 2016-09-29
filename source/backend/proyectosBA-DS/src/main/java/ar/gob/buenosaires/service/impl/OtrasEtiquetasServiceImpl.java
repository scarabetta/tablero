package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.OtraEtiqueta;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.OtrasEtiquetasReqMsg;
import ar.gob.buenosaires.esb.domain.message.OtrasEtiquetasRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.OtrasEtiquetasService;

@Service
public class OtrasEtiquetasServiceImpl implements OtrasEtiquetasService {

	private final static Logger LOGGER = LoggerFactory.getLogger(OtrasEtiquetasServiceImpl.class);

	@Autowired
	private EsbService esbService;
	
	@Override
	public List<OtraEtiqueta> getOtrasEtiquetas() throws ESBException, JMSException {
		OtrasEtiquetasReqMsg reqMsg = new OtrasEtiquetasReqMsg();

		return getOtrasEtiquetasFromReqMsg(reqMsg);
	}

	@Override
	public OtraEtiqueta createOtraEtiqueta(OtraEtiqueta otraEtiqueta, String email) throws ESBException, JMSException {
		OtrasEtiquetasReqMsg reqMsg = new OtrasEtiquetasReqMsg();
		reqMsg.setOtraEtiqueta(otraEtiqueta);
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para crear una etiqueta : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, OtrasEtiquetasRespMsg.class);
		List<OtraEtiqueta> otrasEtiquetas = getOtraEtiquetaFromResponse(response);
		return getFirstOtraEtiquetaFromTheList(otrasEtiquetas);
	}

	@Override
	public OtraEtiqueta updateOtraEtiqueta(OtraEtiqueta otraEtiqueta, String email) throws ESBException, JMSException {
		OtrasEtiquetasReqMsg reqMsg = new OtrasEtiquetasReqMsg();
		reqMsg.setOtraEtiqueta(otraEtiqueta);
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para actualizar una etiqueta : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, OtrasEtiquetasRespMsg.class);
		List<OtraEtiqueta> otrasEtiquetas = getOtraEtiquetaFromResponse(response);
		return getFirstOtraEtiquetaFromTheList(otrasEtiquetas);
	}

	@Override
	public void deleteOtraEtiqueta(String id, String email) throws ESBException, JMSException {
		OtrasEtiquetasReqMsg reqMsg = new OtrasEtiquetasReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para borrar una etiqueta: {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, OtrasEtiquetasRespMsg.class);
	}

	@Override
	public OtraEtiqueta getOtraEtiquetaPorId(Long id) throws ESBException, JMSException {
		OtrasEtiquetasReqMsg reqMsg = new OtrasEtiquetasReqMsg();
		reqMsg.setId(id);

		List<OtraEtiqueta> otrasEtiquetas = getOtrasEtiquetasFromReqMsg(reqMsg);
		return getFirstOtraEtiquetaFromTheList(otrasEtiquetas);
	}

	private List<OtraEtiqueta> getOtrasEtiquetasFromReqMsg(OtrasEtiquetasReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener una etiqueta : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, OtrasEtiquetasRespMsg.class);

		List<OtraEtiqueta> otrasEtiquetas = null;
		if (response.getEventType().equalsIgnoreCase(OtrasEtiquetasRespMsg.OTRAS_ETIQUETAS_TYPE)) {
			otrasEtiquetas = ((OtrasEtiquetasRespMsg) response).getOtrasEtiquetas();
			LOGGER.debug("Obteninendo etiquetas  de la respues del BUS de servicios: {}",
					otrasEtiquetas .toString());
		}
		return otrasEtiquetas;
	}

	private List<OtraEtiqueta> getOtraEtiquetaFromResponse(EsbBaseMsg response) {
		List<OtraEtiqueta> otrasEtiquetas = null;
		if (response.getEventType().equalsIgnoreCase(OtrasEtiquetasRespMsg.OTRAS_ETIQUETAS_TYPE)) {
			otrasEtiquetas = ((OtrasEtiquetasRespMsg) response).getOtrasEtiquetas();
			LOGGER.debug("Obteninendo los etiquetas de la respues del BUS de servicios: {}", otrasEtiquetas.toString());
		}
		return otrasEtiquetas;
	}

	private OtraEtiqueta getFirstOtraEtiquetaFromTheList(List<OtraEtiqueta> OtraEtiqueta) {
		if (!OtraEtiqueta.isEmpty()) {
			return OtraEtiqueta.get(0);
		} else {
			return null;
		}
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
