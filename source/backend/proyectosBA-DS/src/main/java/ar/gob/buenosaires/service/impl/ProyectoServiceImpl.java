package ar.gob.buenosaires.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.gob.buenosaires.domain.AccionesProyecto;
import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
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
	public Proyecto getProyectoPorNombreIdJurisdiccionYCiertosEstados(String nombre, Long IdJurisdiccion, List<String> estados)
			throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setName(nombre);
		reqMsg.setEstados(estados);
		reqMsg.setIdJurisdiccion(IdJurisdiccion);

		List<Proyecto> proyectos = getProyectosFromReqMsg(reqMsg);
		return getProyectoFromResponse(proyectos);
	}

	@Override
	public Proyecto getProyectoPorNombreIdJurisdiccion(String nombre, Long IdJurisdiccion)
			throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setName(nombre);
		reqMsg.setIdJurisdiccion(IdJurisdiccion);

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
	public Proyecto createProyecto(Proyecto proyecto, String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<>();
		reqMsg.setProyecto(proyecto);
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para crear un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_CREATE, ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}

	@Override
	public Proyecto presentarProyecto(Proyecto proyecto, String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<>();
		reqMsg.setProyecto(proyecto);
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para presentar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_PRESENTAR, ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}

	@Override
	public Proyecto cambiarEstadoProyecto(Proyecto proyecto, String action, String email) throws ESBException, JMSException {

		if(AccionesProyecto.CANCELAR.getName().equalsIgnoreCase(action)){
			return cancelarProyecto(proyecto, email);
		} else if(AccionesProyecto.VERIFICAR.getName().equalsIgnoreCase(action)){
			return verificarProyecto(proyecto, email);
		} else if(AccionesProyecto.DESHACER_CANCELACION.getName().equalsIgnoreCase(action)){
			return deshacerCancelacion(proyecto, email);
		}
		return null;
	}

	@Override
	public List<String> getAccionesPermitidas(String idProyecto, String userMail) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setId(Long.parseLong(idProyecto));
		reqMsg.setUsuario(createUsuarioConMail(userMail));

		getLogger().debug("Mensaje creado para obtener las acciones para un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE_ACTIONS, ProyectoRespMsg.class);

		List<String> accionesPermitidas = new ArrayList<>();
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			accionesPermitidas = ((ProyectoRespMsg) response).getAccionesPermitidas();
			LOGGER.debug("Obteninendo las acciones permitidas para los  Proyectos de la respues del BUS de servicios: {}",
					accionesPermitidas.toString());
		}
		return accionesPermitidas;
	}

	@Override
	public Proyecto cancelarProyecto(Proyecto proyecto, String email) throws ESBException, JMSException {

		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<>();
		reqMsg.setProyecto(proyecto);
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para cancelar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CANCEL, ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}

	@Override
	public Proyecto deshacerCancelacion(Proyecto proyecto, String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<>();
		reqMsg.setProyecto(proyecto);
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para deshacer una cancelacion de un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DESHACER_CANCELACION, ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}

	@Override
	public Proyecto verificarProyecto(Proyecto proyecto, String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<>();
		reqMsg.setProyecto(proyecto);
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para cancelar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_VERIFICAR, ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}

	@Override
	public Proyecto updateProyecto(Proyecto proyecto, String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<>();
		reqMsg.setProyecto(proyecto);
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para actualizar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_UPDATE, ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}

	@Override
	public void deleteProyecto(String id, String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(email);

		getLogger().debug("Mensaje creado para borrar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_DELETE, ProyectoRespMsg.class);
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private List<Proyecto> getProyectosFromReqMsg(ProyectoReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().debug("Mensaje creado para obtener un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, ProyectoRespMsg.class);

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
		List<String> estados = new ArrayList<>();
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
			} else if(proyecto.getEstado().equalsIgnoreCase(EstadoProyecto.ENPRIORIZACION.getName())) {
				enpriorizacion++;
			}
		}

		ObjectMapper mapper = new ObjectMapper();
		JsonNode actualObj = null;
		try {
			actualObj = mapper.readTree("{ \"" + EstadoProyecto.PRESENTADO.getName() + "\" : \"" + presentado +
					"\", \"" + EstadoProyecto.VERIFICADO.getName() + "\" : \"" + verificado +
					"\", \"EnPriorizacion\" : \"" + enpriorizacion +"\" }");
		} catch (IOException e) {
			getLogger().error("Se produjo un error al querer obtener el resumen de los proyectos a priorizar.", e);
			throw new RuntimeException(e.getMessage());
		}

		return actualObj;
	}

	@Override
	public void cancelarPriorizacionDeProyectos(String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setEmailUsuario(email);
		
		getLogger().debug("Mensaje creado para cancelar la Priorizacion de proyectos");
		esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_CANCELAR_PRIORIZACION, ProyectoRespMsg.class);
	}

	@Override
	public void updatePriorizarProyectos(String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setEmailUsuario(email);
		
		getLogger().debug("Mensaje creado para inicar la Priorizacion de proyectos");
		esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_INICIAR_PRIORIZACION, ProyectoRespMsg.class);
	}
	
	private Usuario createUsuarioConMail(String mailDelUsuarioDelToken) {
		Usuario usuario = new Usuario();
		usuario.setEmail(mailDelUsuarioDelToken);
		return usuario;
	}
}
