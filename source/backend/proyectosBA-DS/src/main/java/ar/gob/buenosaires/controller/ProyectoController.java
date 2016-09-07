package ar.gob.buenosaires.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.domain.ArchivoProyecto;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.ProyectoService;
import ar.gob.buenosaires.service.UsuarioService;

@RestController
@RequestMapping("/api/proyecto")
public class ProyectoController {

	@Autowired
	private ProyectoService service;
	
	@Autowired
	private UsuarioService usuarioService;

	@Value("${download.archivos.proyecto.path}")
	private String PATHARCHIVOS;

	@RequestMapping(path = "/bajar_archivo", method = RequestMethod.GET)
	public @ResponseBody void bajarArchivoDeProyecto(final String id, final String idJurisdiccion,
			final String nombreArchivo, final HttpServletResponse response) {
		final String path = PATHARCHIVOS.replace("idJurisdiccion", idJurisdiccion).replace("idProyecto", id);

		FileInputStream fis;
		try {
			fis = new FileInputStream(path + nombreArchivo);
			response.setContentType("application/octet-stream");
			response.setHeader("content-disposition", "attachment; filename = " + nombreArchivo);

			IOUtils.copy(fis, response.getOutputStream());
			response.flushBuffer();
			fis.close();
		} catch (final IOException e) {
			e.printStackTrace();
		}

	}

	@RequestMapping(path = "/subir_archivo", method = RequestMethod.POST)
	public @ResponseBody String addArchivoAProyecto(final String id, final String idJurisdiccion,
			final MultipartFile archivoASubir) throws ESBException, JMSException {
		final Proyecto elProyecto = service.getProyectoPorId(id);
		final ArchivoProyecto nuevoArchivo = new ArchivoProyecto();
		nuevoArchivo.setNombre(archivoASubir.getOriginalFilename());
		final String pathArchivos = PATHARCHIVOS.replace("idJurisdiccion", idJurisdiccion).replace("idProyecto", id);

		// Chequear si exite el path, sino crearlo
		if (Files.notExists(Paths.get(pathArchivos))) {
			try {
				Files.createDirectories(Paths.get(pathArchivos));
			} catch (final IOException e) {
				e.printStackTrace();
			}
		}
		// guardar archivo.
		try {
			archivoASubir.transferTo(new File(pathArchivos + nuevoArchivo.getNombre()));
			elProyecto.getArchivos().add(nuevoArchivo);
			nuevoArchivo.setProyecto(elProyecto);
			service.updateProyecto(elProyecto);
		} catch (IllegalStateException | IOException e) {
			e.printStackTrace();
		}

		return nuevoArchivo.getNombre();
	}

	@RequestMapping(method = RequestMethod.GET)
	public @ResponseBody List<Proyecto> getProyectos() throws ESBException, JMSException {
		return service.getProyectos();
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public @ResponseBody Proyecto getProyectoPorId(@PathVariable final String id) throws ESBException, JMSException {
		return service.getProyectoPorId(id);
	}

	@RequestMapping(path = "/nombre/{nombre}", method = RequestMethod.GET)
	public @ResponseBody Proyecto getProyectoPorNombre(@PathVariable final String nombre)
			throws ESBException, JMSException {
		return service.getProyectoByName(nombre);
	}

	@RequestMapping(path = "/codigo/{codigo}", method = RequestMethod.GET)
	public @ResponseBody Proyecto getProyectoPorCodigo(@PathVariable final String codigo)
			throws ESBException, JMSException {
		return service.getProyectoPorCodigo(codigo);
	}

	@RequestMapping(method = RequestMethod.POST)
	public Proyecto createProyecto(@RequestBody final Proyecto proyecto) throws ESBException, JMSException {
		return service.createProyecto(proyecto);
	}
	
	@RequestMapping(path = "/presentar", method = RequestMethod.POST)
	public Proyecto presentarProyecto(@RequestBody final Proyecto proyecto, final HttpServletRequest request) throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		Usuario user = usuarioService.getUsuarioPorToken(request.getHeader(HttpHeaders.AUTHORIZATION));
		if (user.tienePerfilSecretaria()) {
			return service.presentarProyecto(proyecto);
		} else {
			throw new ESBException("No tiene el perfil para realizar esta accion");
		}
		
	}
	
	@RequestMapping(path = "/cambiarEstado/{action}", method = RequestMethod.POST)
	public Proyecto cambiarEstadoProyecto(@RequestBody final Proyecto proyecto, @PathVariable final String action, final HttpServletRequest request) throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		Usuario user = usuarioService.getUsuarioPorToken(request.getHeader(HttpHeaders.AUTHORIZATION));
		if (user.tienePerfilSecretaria()) {
			return service.cambiarEstadoProyecto(proyecto, action);
		} else {
			throw new ESBException("No tiene el perfil para realizar esta accion");
		}
	}
	
	@RequestMapping(path = "/cambiarEstado/accionesPermitidas/{id}", method = RequestMethod.GET)
	public List<String> getAccionesPermitidas(@PathVariable final String id, final HttpServletRequest request) throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		Usuario user = usuarioService.getUsuarioPorToken(request.getHeader(HttpHeaders.AUTHORIZATION));
		if (user.tienePerfilSecretaria()) {
			return service.getAccionesPermitidas(id);			
		} else {
			return new ArrayList<String>();
		}
	}

	@RequestMapping(method = RequestMethod.PUT)
	public Proyecto updateProyecto(@RequestBody final Proyecto proyecto) throws ESBException, JMSException {
		return service.updateProyecto(proyecto);
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public @ResponseBody void deleteProyecto(@PathVariable final String id) throws ESBException, JMSException {
		service.deleteProyecto(id);
	}
}
