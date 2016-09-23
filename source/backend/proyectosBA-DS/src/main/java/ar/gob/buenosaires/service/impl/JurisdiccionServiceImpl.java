package ar.gob.buenosaires.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.Usuario;
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
	public List<Jurisdiccion> getJurisdicciones(Usuario usuario) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setUsuario(usuario);

		return getJurisdiccionesFromReqMsg(reqMsg);
	}

	@Override
	public Jurisdiccion getJurisdiccionPorId(String id, Usuario usuario) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setUsuario(usuario);

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
	public Jurisdiccion createJurisdicciones(@RequestBody Jurisdiccion jurisdiccion) throws ESBException, JMSException {		
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setJurisdiccion(jurisdiccion);

		getLogger().debug("Mensaje creado para crear una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_CREATE, JurisdiccionRespMsg.class);
		final List<Jurisdiccion> usuarios = getJurisdiccionFromResponse(response);
		return getFirstJurisdiccionFromTheList(usuarios);
	}

	@Override
	public Jurisdiccion updateJurisdicciones(@RequestBody Jurisdiccion jurisdiccion) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setJurisdiccion(jurisdiccion);

		getLogger().debug("Mensaje creado para actualizar una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_UPDATE, JurisdiccionRespMsg.class);
		final List<Jurisdiccion> usuarios = getJurisdiccionFromResponse(response);
		return getFirstJurisdiccionFromTheList(usuarios);
	}
	
	@Override
	public void deleteJurisdiccion(String id) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setId(Long.parseLong(id));

		getLogger().debug("Mensaje creado para borrar una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE, JurisdiccionRespMsg.class);		
	}
	
	@Override
	public void presentarProyectosCompletos(String id) throws ESBException, JMSException {
		JurisdiccionReqMsg reqMsg = new JurisdiccionReqMsg();
		reqMsg.setId(Long.parseLong(id));

		getLogger().debug("Mensaje creado para presentar todos los proyectos completos de una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_PRESENTAR_TODOS, JurisdiccionRespMsg.class); 		
	}

	public static Logger getLogger() {
		return LOGGER;
	}
	
	private List<Jurisdiccion> getJurisdiccionesFromReqMsg(JurisdiccionReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener una Jurisdiccion : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, JurisdiccionRespMsg.class);

		List<Jurisdiccion> jurisdicciones = null;
		if (response.getEventType().equalsIgnoreCase(JurisdiccionRespMsg.JURISDICCION_TYPE)) {
			jurisdicciones = ((JurisdiccionRespMsg) response).getJurisdicciones();
			LOGGER.debug("Obteninendo las jurisdicciones de la respuesta del BUS de servicios: {}", jurisdiccionToString(jurisdicciones));
		}
		return jurisdicciones;
	}

	private String jurisdiccionToString(List<Jurisdiccion> jurisdicciones) {
		return jurisdicciones == null ? "El user no tiene jurisdicciones" : jurisdicciones.toString();
	}
	
	private Jurisdiccion getJurisdiccionFromResponse(
			List<Jurisdiccion> jurisdicciones) {
		if (!jurisdicciones.isEmpty()) {
			return jurisdicciones.get(0);
		} else {
			return null;
		}
	}
	
	private List<Jurisdiccion> getJurisdiccionFromResponse(final EsbBaseMsg response) {
		List<Jurisdiccion> jurisdicciones = new ArrayList<Jurisdiccion>();
		if (response.getEventType().equalsIgnoreCase(JurisdiccionRespMsg.JURISDICCION_TYPE)) {
			jurisdicciones = ((JurisdiccionRespMsg) response).getJurisdicciones();
			LOGGER.debug("Obteninendo las jutisdicciones de la respuesta del BUS de servicios: {}", jurisdiccionToString(jurisdicciones));
		}
		return jurisdicciones;
	}
	
	private Jurisdiccion getFirstJurisdiccionFromTheList(final List<Jurisdiccion> jurisdicciones) {
		if (!jurisdicciones.isEmpty()) {
			return jurisdicciones.get(0);
		} else {
			return null;
		}
	}
}
