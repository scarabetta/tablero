package ar.gob.buenosaires.service.impl;

import java.text.ParseException;
import java.util.List;

import javax.jms.JMSException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.domain.UsuarioResumen;
import ar.gob.buenosaires.esb.domain.ESBEvent;
import ar.gob.buenosaires.esb.domain.EsbBaseMsg;
import ar.gob.buenosaires.esb.domain.message.UsuarioReqMsg;
import ar.gob.buenosaires.esb.domain.message.UsuarioRespMsg;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.esb.service.EsbService;
import ar.gob.buenosaires.security.adapter.AuthenticationAdapter;
import ar.gob.buenosaires.security.jwt.JWToken;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	private final static Logger LOGGER = LoggerFactory.getLogger(JurisdiccionServiceImpl.class);

	@Autowired
	private EsbService esbService;
	
	@Autowired
	AuthenticationAdapter authAdapter;

	@Override
	public Usuario getUsuarioByEmail(final String email) throws ESBException, JMSException {
		final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
		reqMsg.setEmail(email);

		final List<Usuario> usuarios = getUsuarioFromReqMsg(reqMsg);
		return getFirstUsuarioFromTheList(usuarios);
	}

	@Override
	public List<Usuario> getUsuarios() throws ESBException, JMSException {
		final UsuarioReqMsg reqMsg = new UsuarioReqMsg();

		return getUsuarioFromReqMsg(reqMsg);
	}

	@Override
	public Usuario createUsuario(final Usuario usuario, String email) throws ESBException, JMSException {
		
		 if(authAdapter.validMail(usuario.getEmail()) != null){
			 final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
			 reqMsg.setUsuario(usuario);
			 reqMsg.setEmailUsuario(email);
			 
			 getLogger().info("Mensaje creado para crear un Usuario : {}", reqMsg.toString());
			 final EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_CREATE, UsuarioRespMsg.class);
			 final List<Usuario> usuarios = getUsuarioFromResponse(response);
			 return getFirstUsuarioFromTheList(usuarios);			 		
			 
		 } else {
			 throw new ESBException(CodigoError.USUARIO_INEXISTENTE.getCodigo(), "No se encontró el usuario con mail: " + usuario.getEmail());
		 }
	}

	@Override
	public Usuario updateUsuario(final Usuario usuario, String email) throws ESBException, JMSException {
		if(authAdapter.validMail(usuario.getEmail()) != null){
			final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
			reqMsg.setUsuario(usuario);
			reqMsg.setEmailUsuario(email);
	
			getLogger().info("Mensaje creado para actualizar un Usuario : {}", reqMsg.toString());
			final EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_UPDATE, UsuarioRespMsg.class);
			final List<Usuario> usuarios = getUsuarioFromResponse(response);
			return getFirstUsuarioFromTheList(usuarios);
		} else {
			 throw new ESBException(CodigoError.USUARIO_INEXISTENTE.getCodigo(), "No se encontró el usuario con mail: " + usuario.getEmail());
		 }
	}
	
	@Override
	public Usuario validarUsuario(String email) {
		return authAdapter.validMail(email);
	}

	@Override
	public void deleteUsuario(final String id, String email) throws ESBException, JMSException {
		final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
		reqMsg.setId(Long.parseLong(id));
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para borrar un Usuario : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_DELETE, UsuarioRespMsg.class);
	}

	@Override
	public Usuario getUsuarioPorId(final Long id) throws ESBException, JMSException {
		final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
		reqMsg.setId(id);

		final List<Usuario> usuarios = getUsuarioFromReqMsg(reqMsg);
		return getFirstUsuarioFromTheList(usuarios);
	}
	
	@Override
	public void inhabilitarUsuario(Long id, String email) throws ESBException, JMSException {
		final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
		reqMsg.setId(id);
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para inhablitar un Usuario : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_INHABILITAR_USUARIO, UsuarioRespMsg.class);
	}

	@Override
	public void habilitarUsuario(Long id, String email) throws ESBException, JMSException {
		final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
		reqMsg.setId(id);
		reqMsg.setEmailUsuario(email);

		getLogger().info("Mensaje creado para habilitar un Usuario : {}", reqMsg.toString());
		esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_HABILITAR_USUARIO, UsuarioRespMsg.class);
	}

	private List<Usuario> getUsuarioFromReqMsg(final UsuarioReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener un Usuario : {}", reqMsg.toString());
		final EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE, UsuarioRespMsg.class);

		final List<Usuario> ususarios = getUsuarioFromResponse(response);
		return ususarios;
	}

	private List<Usuario> getUsuarioFromResponse(final EsbBaseMsg response) {
		List<Usuario> ususarios = null;
		if (response.getEventType().equalsIgnoreCase(UsuarioRespMsg.USUARIO_TYPE)) {
			ususarios = ((UsuarioRespMsg) response).getUsuarios();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo los Usuarios de la respues del BUS de servicios: {}", ususarios.toString());
			} else {
				getLogger().info("Obteninendo los Usuarios de la respues del BUS de servicios");
			}
		}
		return ususarios;
	}

	private Usuario getFirstUsuarioFromTheList(final List<Usuario> usuarios) {
		if (!usuarios.isEmpty()) {
			return usuarios.get(0);
		} else {
			return null;
		}
	}

	public static Logger getLogger() {
		return LOGGER;
	}

	@Override
	public Usuario getUsuarioPorToken(final String stringToken)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		Usuario user = null;
		final JWToken token = new JWToken(stringToken);
		user = getUsuarioByEmail(token.getSubject());

		return user;
	}

	@Override
	public List<UsuarioResumen> getUsuariosResumen() throws ESBException, JMSException {
		final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
		
		return getUsuarioResumenFromReqMsg(reqMsg);
	}
	
	@Override
	public UsuarioResumen getUsuarioResumenByEmail(final String email) throws ESBException, JMSException {
		final UsuarioReqMsg reqMsg = new UsuarioReqMsg();
		reqMsg.setEmail(email);
		
		final List<UsuarioResumen> usuarios = getUsuarioResumenFromReqMsg(reqMsg);
		return getFirstUsuarioResumenFromTheList(usuarios);
	}
	
	private List<UsuarioResumen> getUsuarioResumenFromReqMsg(final UsuarioReqMsg reqMsg) throws ESBException, JMSException {
		getLogger().info("Mensaje creado para obtener un UsuarioResumen : {}", reqMsg.toString());
		final EsbBaseMsg response = esbService.sendToBus(reqMsg, "ProyectosDA-DS", ESBEvent.ACTION_RETRIEVE_RESUMEN, UsuarioRespMsg.class);

		final List<UsuarioResumen> ususarios = getUsuarioResumenFromResponse(response);
		return ususarios;
	}
	
	private List<UsuarioResumen> getUsuarioResumenFromResponse(final EsbBaseMsg response) {
		List<UsuarioResumen> ususarios = null;
		if (response.getEventType().equalsIgnoreCase(UsuarioRespMsg.USUARIO_TYPE)) {
			ususarios = ((UsuarioRespMsg) response).getUsuariosResumen();
			if(getLogger().isDebugEnabled()){
				getLogger().debug("Obteninendo los UsuariosResumen de la respues del BUS de servicios: {}", ususarios.toString());
			} else {
				getLogger().info("Obteninendo los UsuariosResumen de la respues del BUS de servicios");
			}
		}
		return ususarios;
	}
	
	private UsuarioResumen getFirstUsuarioResumenFromTheList(final List<UsuarioResumen> usuarios) {
		if (!usuarios.isEmpty()) {
			return usuarios.get(0);
		} else {
			return null;
		}
	}

}
