package ar.gob.buenosaires.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
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
	public Proyecto presentarProyecto(Proyecto proyecto) throws ESBException, JMSException {
		/*TODO temporal hasta que arreglemos el bus. Sacarlo una vez que el Bus no nos rompa las relaciones.
		* Sin esto falla el check que se fija si el proyecto esta completo
		*/  
		proyecto.setObjetivoOperativo(new ObjetivoOperativo());
		
		if (EstadoProyecto.COMPLETO.getName().equals(proyecto.getEstadoActualizado())) {
			proyecto.setEstado(EstadoProyecto.PRESENTADO.getName());
			proyecto.setVerificado(false);
			if (proyecto.getIdProyecto() == null) {				
				return createProyecto(proyecto);
			} else {
				return updateProyecto(proyecto);
			}
			
		} else {
			throw new ESBException("El proyecto debe estar completo para poder ser presentado");
		}

	}
	
	@Override
	public Proyecto cambiarEstadoProyecto(Proyecto proyecto, String action) throws ESBException, JMSException {
		
		//TODO pasar a ENUM
		switch (action) {
		case "Cancelar":
			return cancelarProyecto(proyecto);
		case "Verificar":
			return verificarProyecto(proyecto);
		case "DeshacerCancelacion":
			return deshacerCancelacion(proyecto);
		case "Presentar":
			return presentarProyecto(proyecto);
		default:
			return null;
		}
	}	

	@Override
	public List<String> getAccionesPermitidas(String idProyecto) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setId(Long.parseLong(idProyecto));

		getLogger().debug("Mensaje creado para obtener las acciones para un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE_ACTIONS);

		List<String> accionesPermitidas = new ArrayList<String>();
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			accionesPermitidas = ((ProyectoRespMsg) response).getAccionesPermitidas();
			LOGGER.debug("Obteninendo las acciones permitidas para los  Proyectos de la respues del BUS de servicios: {}",
					accionesPermitidas.toString());
		}
		return accionesPermitidas;
	}
	
	@Override
	public Proyecto cancelarProyecto(Proyecto proyecto) throws ESBException,JMSException {

		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<Proyecto>();
		reqMsg.setProyecto(proyecto);

		getLogger().debug("Mensaje creado para cancelar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CANCEL);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}
	
	@Override
	public Proyecto deshacerCancelacion(Proyecto proyecto) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<Proyecto>();
		reqMsg.setProyecto(proyecto);

		getLogger().debug("Mensaje creado para deshacer una cancelacion de un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DESHACER_CANCELACION);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}
	
	@Override
	public Proyecto verificarProyecto(Proyecto proyecto) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<Proyecto>();
		reqMsg.setProyecto(proyecto);

		getLogger().debug("Mensaje creado para cancelar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_VERIFICAR);
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
		getLogger().debug("Mensaje creado para obtener un Proyecto : {}", reqMsg.toString());
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

	@Override
	public JsonNode getResumenProyectosPriorizacion() throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<String> estados = new ArrayList<String>();
		estados.add(EstadoProyecto.PRESENTADO.getName());
		estados.add(EstadoProyecto.VERIFICADO.getName());
		estados.add(EstadoProyecto.ENPRIORIZACION.getName());
		reqMsg.setEstados(estados);
		
		List<Proyecto> proyectos = getProyectosFromReqMsg(reqMsg);
		int presentado = 0, verificado = 0, enpriorizacion = 0;
		for (Proyecto proyecto : proyectos) {
			if(proyecto.getEstado().equalsIgnoreCase(EstadoProyecto.PRESENTADO.getName())){
				presentado++;
			} else if(proyecto.getEstado().equalsIgnoreCase(EstadoProyecto.VERIFICADO.getName())){
				verificado++;
			} else {
				enpriorizacion++;
			}
		}
		
		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
	    try {
			actualObj = mapper.readTree("{ \"" + EstadoProyecto.PRESENTADO.getName() + "\" : \"" + presentado + 
								"\", \"" + EstadoProyecto.VERIFICADO.getName() + "\" : \"" + verificado + 
								"\", \"En-Priorizacion\" : \"" + enpriorizacion +"\" }");
		} catch (IOException e) {
			getLogger().error("Se produjo un error al querer obtener el resumen de los proyectos a priorizar.",e);
			throw new RuntimeException(e.getMessage());
		}
		
		return actualObj;
	}

	@Override
	public void cancelarPriorizacionDeProyectos() throws ESBException, JMSException {

		getLogger().debug("Mensaje creado para cancelar la Priorizacion de proyectos");
		esbService.sendToBus(new ProyectoReqMsg(), "ProyectosDA-DS",ESBEvent.ACTION_CANCELAR_PRIORIZACION);
	}

	@Override
	public void updatePriorizarProyectos() throws ESBException, JMSException {

		getLogger().debug("Mensaje creado para inicar la Priorizacion de proyectos");
		esbService.sendToBus(new ProyectoReqMsg(), "ProyectosDA-DS",ESBEvent.ACTION_INICIAR_PRIORIZACION);
	}
}
