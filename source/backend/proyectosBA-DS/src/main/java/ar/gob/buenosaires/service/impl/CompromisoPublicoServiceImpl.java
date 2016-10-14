package ar.gob.buenosaires.service.impl;

import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.domain.CompromisoPublico;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.CompromisoPublicoReqMsg;
import ar.gob.buenosaires.esb.domain.message.CompromisoPublicoRespMsg;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.service.CompromisoPublicoService;

@Service
public class CompromisoPublicoServiceImpl implements CompromisoPublicoService {

	private final static Logger LOGGER = LoggerFactory.getLogger(CompromisoPublicoServiceImpl.class);

	@Autowired
	private EsbService esbService;
	
	@Override
	public List<CompromisoPublico> getCompromisosPublicos() throws ESBException, JMSException {
		CompromisoPublicoReqMsg reqMsg = new CompromisoPublicoReqMsg();

		return getCompromisoPublicoFromReqMsg(reqMsg);
	}

	@Override
	public CompromisoPublico createCompromisoPublico(CompromisoPublico compromisoPublico, String email) throws ESBException, JMSException {
		CompromisoPublicoReqMsg reqMsg = new CompromisoPublicoReqMsg();
		reqMsg.setCompromisoPublico(compromisoPublico);
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para crear un Compromiso Publico : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, CompromisoPublicoRespMsg.class);
		List<CompromisoPublico> compromisosPublicos = getCompromisoPublicoFromResponse(response);
		return getFirstCompromisoPublicoFromTheList(compromisosPublicos);
	}

	@Override
	public CompromisoPublico updateCompromisoPublico(CompromisoPublico compromisoPublico, String email) throws ESBException, JMSException {
		CompromisoPublicoReqMsg reqMsg = new CompromisoPublicoReqMsg();
		reqMsg.setCompromisoPublico(compromisoPublico);
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para actualizar un Compromiso Publico : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, CompromisoPublicoRespMsg.class);
		List<CompromisoPublico> compromisosPublicos = getCompromisoPublicoFromResponse(response);
		return getFirstCompromisoPublicoFromTheList(compromisosPublicos);
	}

	@Override
	public void deleteCompromisoPublico(String id, String email) throws ESBException, JMSException {
		CompromisoPublicoReqMsg reqMsg = new CompromisoPublicoReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para borrar un Compromiso Publico : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, CompromisoPublicoRespMsg.class);
	}

	@Override
	public CompromisoPublico getCompromisoPublicoPorId(Long id) throws ESBException, JMSException {
		CompromisoPublicoReqMsg reqMsg = new CompromisoPublicoReqMsg();
		reqMsg.setId(id);

		List<CompromisoPublico> compromisosPublicos = getCompromisoPublicoFromReqMsg(reqMsg);
		return getFirstCompromisoPublicoFromTheList(compromisosPublicos);
	}

	private List<CompromisoPublico> getCompromisoPublicoFromReqMsg(CompromisoPublicoReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener un Compromiso Publico : {}", reqMsg.toString());
		EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS",ESBEvent.ACTION_RETRIEVE, CompromisoPublicoRespMsg.class);

		List<CompromisoPublico> compromisosPublicos = null;
		if (response.getEventType().equalsIgnoreCase(CompromisoPublicoRespMsg.COMPROMISO_PUBLICO_TYPE)) {
			compromisosPublicos = ((CompromisoPublicoRespMsg) response).getCompromisosPublicos();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo los Compromisos Publicos de la respues del BUS de servicios: {}",
						compromisosPublicos.toString());
			} else {
				getLogger().info("Obteninendo los Compromisos Publicos de la respues del BUS de servicios");
			}
		}
		return compromisosPublicos;
	}

	private List<CompromisoPublico> getCompromisoPublicoFromResponse(EsbBaseMsg response) {
		List<CompromisoPublico> compromisosPublicos = null;
		if (response.getEventType().equalsIgnoreCase(CompromisoPublicoRespMsg.COMPROMISO_PUBLICO_TYPE)) {
			compromisosPublicos = ((CompromisoPublicoRespMsg) response).getCompromisosPublicos();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo los Compromisos Publicos de la respues del BUS de servicios: {}",
						compromisosPublicos.toString());
			} else {
				getLogger().info("Obteninendo los Compromisos Publicos de la respues del BUS de servicios");
			}
		}
		return compromisosPublicos;
	}

	private CompromisoPublico getFirstCompromisoPublicoFromTheList(List<CompromisoPublico> compromisoPublico) {
		if (!compromisoPublico.isEmpty()) {
			return compromisoPublico.get(0);
		} else {
			return null;
		}
	}

	public static Logger getLogger() {
		return LOGGER;
	}
}
