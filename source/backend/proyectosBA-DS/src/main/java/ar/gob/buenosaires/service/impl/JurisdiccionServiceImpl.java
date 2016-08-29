package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.JurisdiccionReqMsg;
import ar.gob.buenosaires.esb.domain.message.JurisdiccionRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.JurisdiccionService;

@Service
public class JurisdiccionServiceImpl implements JurisdiccionService {

	private final static Logger LOGGER = LoggerFactory.getLogger(JurisdiccionServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<Jurisdiccion> getJurisdicciones() throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();

		return getJurisdiccionesFromReqMsg(reqMsg);
	}

	@Override
	public Jurisdiccion getJurisdiccionPorId(String id) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setId(Long.parseLong(id));

		List<Jurisdiccion> jurisdicciones = getJurisdiccionesFromReqMsg(reqMsg);
		return getJurisdiccionFromResponse(jurisdicciones);
	}

	@Override
	public Jurisdiccion getJurisdiccionesByName(String nombre) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setName(nombre);
		
		List<Jurisdiccion> jurisdicciones = getJurisdiccionesFromReqMsg(reqMsg);
		return getJurisdiccionFromResponse(jurisdicciones);
	}
	
	@Override
	public Jurisdiccion getJurisdiccionesPorCodigo(String codigo) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setCodigo(codigo);
		
		List<Jurisdiccion> jurisdicciones = getJurisdiccionesFromReqMsg(reqMsg);
		return getJurisdiccionFromResponse(jurisdicciones);
	}

	@Override
	public void createJurisdicciones(@RequestBody Jurisdiccion jurisdiccion) throws ESBException, JMSException {		
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setJurisdiccion(jurisdiccion);

		getLogger().debug("Mensaje creado para crear una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_CREATE);		
	}

	@Override
	public void updateJurisdicciones(@RequestBody Jurisdiccion jurisdiccion) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setJurisdiccion(jurisdiccion);

		getLogger().debug("Mensaje creado para actualizar una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_UPDATE);		
	}
	
	@Override
	public void deleteJurisdiccion(String id) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setId(Long.parseLong(id));

		getLogger().debug("Mensaje creado para borrar una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE);		
	}
	
	@Override
	public void presentarProyectosCompletos(String id) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setId(Long.parseLong(id));

		getLogger().debug("Mensaje creado para presentar todos los proyectos completos de una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_PRESENTAR_TODOS);		
	}

	public static Logger getLogger() {
		return LOGGER;
	}
	
	private List<Jurisdiccion> getJurisdiccionesFromReqMsg(JurisdiccionReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE);

		List<Jurisdiccion> jurisdicciones = null;
		if (response.getEventType().equalsIgnoreCase(JurisdiccionRespMsg.JURISDICCION_TYPE)) {
			jurisdicciones = ((JurisdiccionRespMsg) response).getJurisdicciones();
			LOGGER.debug("Obteninendo las jurisdicciones de la respues del BUS de servicios: {}",
					jurisdicciones.toString());
		}
		return jurisdicciones;
	}
	
	private Jurisdiccion getJurisdiccionFromResponse(
			List<Jurisdiccion> jurisdicciones) {
		if (!jurisdicciones.isEmpty()) {
			return jurisdicciones.get(0);
		} else {
			return null;
		}
	}
}
