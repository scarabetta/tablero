package ar.gob.buenosaires.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.EtiquetasMsg;
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
	
	private ObjectMapper mapper;

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
	public Proyecto getProyectoPorNombreIdJurisdiccionYCiertosEstados(String nombre, Long IdJurisdiccion,
			List<String> estados) throws ESBException, JMSException {
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

		getLogger().info("Mensaje creado para crear un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE,
				ProyectoRespMsg.class);
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

		getLogger().info("Mensaje creado para presentar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_PRESENTAR,
				ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}

	@Override
	public Proyecto cambiarEstadoProyecto(Proyecto proyecto, String action, String email)
			throws ESBException, JMSException {		
		
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		List<Proyecto> responseProyectos = new ArrayList<>();
		reqMsg.setProyecto(proyecto);
		reqMsg.setAccion(action);		
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para cancelar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE,
				ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			responseProyectos = ((ProyectoRespMsg) response).getProyectos();
		}
		return getProyectoFromResponse(responseProyectos);
	}

	@Override
	public List<String> getAccionesPermitidas(String idProyecto, String userMail) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setId(Long.parseLong(idProyecto));
		reqMsg.setUsuario(createUsuarioConMail(userMail));

		getLogger().info("Mensaje creado para obtener las acciones para un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE_ACTIONS,
				ProyectoRespMsg.class);

		List<String> accionesPermitidas = new ArrayList<>();
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			accionesPermitidas = ((ProyectoRespMsg) response).getAccionesPermitidas();
			if(getLogger().isDebugEnabled()){
				getLogger().debug(
					"Obteninendo las acciones permitidas para los  Proyectos de la respues del BUS de servicios: {}",
					accionesPermitidas.toString());
			} else {
				getLogger().info(
						"Obteninendo las acciones permitidas para los  Proyectos de la respues del BUS de servicios");
			}
		}
		return accionesPermitidas;
	}

	@Override
	public Proyecto etiquetarProyecto(EtiquetasMsg etiquetas, String id, String email)
			throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEtiquetas(etiquetas);
		reqMsg.setEmailUsuario(email);
		List<Proyecto> responseProyectos = new ArrayList<>();

		getLogger().info("Mensaje creado para etiquetar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_ETIQUETAR,
				ProyectoRespMsg.class);
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

		getLogger().info("Mensaje creado para actualizar un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE,
				ProyectoRespMsg.class);
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

		getLogger().info("Mensaje creado para borrar un Proyecto : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, ProyectoRespMsg.class);
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	private List<Proyecto> getProyectosFromReqMsg(ProyectoReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener un Proyecto : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE,
				ProyectoRespMsg.class);

		List<Proyecto> proyectos = null;
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			proyectos = ((ProyectoRespMsg) response).getProyectos();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo las Proyectos de la respues del BUS de servicios: {}", proyectos.toString());
			} else {
				getLogger().info("Obteninendo las Proyectos de la respues del BUS de servicios");
			}
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
		String responseProyectos = "";
		JsonNode result = null;

		getLogger().info("Mensaje creado para obtener el resumen de los proyectos a priorizar");
		EsbBaseMsg response = esbService.sendToBus(new ProyectoReqMsg(), "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE_RESUMEN_PRIORIZACION,
				ProyectoRespMsg.class);
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			getLogger().info("Obteninendo del resumen de los proyectos a priorizar de la respuesta del BUS de servicios");
			responseProyectos = ((ProyectoRespMsg) response).getResumenProyectosPriorizacion();
			
			try {
				result = getObjectMapper().readTree(responseProyectos);
			} catch (IOException e) {
				getLogger().error("Se produjo un error al querer obtener el resumen de los proyectos a priorizar.", e);
				throw new RuntimeException(e.getMessage());
			}
		}

		return result;
	}

	private ObjectMapper getObjectMapper() {
		if(mapper == null){
			mapper = new ObjectMapper();
		}
		return mapper;
	}

	@Override
	public void cancelarPriorizacionDeProyectos(String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para cancelar la Priorizacion de proyectos");
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CANCELAR_PRIORIZACION, ProyectoRespMsg.class);
	}

	@Override
	public void updatePriorizarProyectos(String email) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para inicar la Priorizacion de proyectos");
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_INICIAR_PRIORIZACION, ProyectoRespMsg.class);
	}

	private Usuario createUsuarioConMail(String mailDelUsuarioDelToken) {
		Usuario usuario = new Usuario();
		usuario.setEmail(mailDelUsuarioDelToken);
		return usuario;
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Integer> getTodosLosIdsProyectosEnPriorizacion(String userMail) throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();
		reqMsg.setUsuario(createUsuarioConMail(userMail));
		reqMsg.setEmailUsuario(userMail);
		reqMsg.setCustomStatement(
				"select idProyecto from proyecto where estado = \"" + EstadoProyecto.ENPRIORIZACION.getName() + "\"");
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CUSTOM_STATEMENT,
				ProyectoRespMsg.class);
		return (List<Integer>) response.getCustomStatementResult().stream().map(s -> Integer.parseInt((String) s))
				.collect(Collectors.toList());
	}

	@Override
	public List<String> getPrioridadesJefatura() throws ESBException, JMSException {
		ProyectoReqMsg reqMsg = new ProyectoReqMsg();

		getLogger().info("Mensaje creado para obtener las prioridades Jefatura: {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE_PRIORIDADES_JEFATURA, ProyectoRespMsg.class);

		List<String> prioridadesJefatura = new ArrayList<>();
		if (response.getEventType().equalsIgnoreCase(ProyectoRespMsg.PROYECTO_TYPE)) {
			prioridadesJefatura = ((ProyectoRespMsg) response).getPrioridadesJefatura();
			if (getLogger().isDebugEnabled()) {
				getLogger().debug("Obteninendo las prioridades de Jefatura del BUS de servicios: {}",
						prioridadesJefatura.toString());
			} else {
				getLogger().info("Obteninendo las prioridades de Jefatura del BUS de servicios");
			}
		}
		return prioridadesJefatura;
	}

}
