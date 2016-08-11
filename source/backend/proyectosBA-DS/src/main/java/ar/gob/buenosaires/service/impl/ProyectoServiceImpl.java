package ar.gob.buenosaires.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.ProyectoReqMsg;
import ar.gob.buenosaires.esb.domain.message.ProyectoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.ProyectoService;

@Service
public class ProyectoServiceImpl implements ProyectoService {

	private final static Logger LOGGER = LoggerFactory.getLogger(ProyectoServiceImpl.class);

	@Autowired
	private EsbService esbService;

	@Override
	public List<Proyecto> getProyectos() throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();

		return getProyectosFromReqMsg(reqMsg);
	}

	@Override
	public Proyecto getProyectoPorId(String id) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setId(Long.parseLong(id));

		List<Proyecto> proyectos = getProyectosFromReqMsg(reqMsg);
		return getProyectoFromResponse(proyectos);
	}

	@Override
	public Proyecto getProyectoByName(String nombre) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setName(nombre);
		
		List<Proyecto> proyectos = getProyectosFromReqMsg(reqMsg);
		return getProyectoFromResponse(proyectos);
	}
	
	@Override
	public Proyecto getProyectoPorCodigo(String codigo) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setCodigo(codigo);
		
		List<Proyecto> proyecto = getProyectosFromReqMsg(reqMsg);
		return getProyectoFromResponse(proyecto);
	}

	@Override
	public Proyecto createProyecto(@RequestBody Proyecto proyecto) throws ESBException, JMSException {		
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<Proyecto>();
		reqMsg.setProyecto(proyecto);

		getLogger().debug("Mensaje creado para crear un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_CREATE);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);		 
	}

	@Override
	public Proyecto updateProyecto(@RequestBody Proyecto proyecto) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<Proyecto>();;
		reqMsg.setProyecto(proyecto);

		getLogger().debug("Mensaje creado para actualizar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_UPDATE);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}
	
	@Override
	public void deleteProyecto(String id) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setId(Long.parseLong(id));

		getLogger().debug("Mensaje creado para borrar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE);		
	}

	public static Logger getLogger() {
		return LOGGER;
	}
	
	private List<Proyecto> getProyectosFromReqMsg(ProyectoReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener una Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE);

		List<Proyecto> proyectos = null;
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			proyectos = ((ProyectoRespMsg) response).getProyectos();
			LOGGER.debug("Obteninendo las Proyectos de la respues del BUS de servicios: {}",
					proyectos.toString());
		}
		return proyectos;
	}
	
	private Proyecto getProyectoFromResponse(List<Proyecto> proyectos) {
		if (!proyectos.isEmpty()) {
			return proyectos.get(0);
		} else {
			return null;
		}
	}
}
