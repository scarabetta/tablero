package ar.gob.buenosaires.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.importador.ResultadoProcesamiento;
import ar.gob.buenosaires.importador.proyecto.priorizado.ProyectosPriorizadosResultadoProcesamiento;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.ImportarProyectoService;
import ar.gob.buenosaires.util.DSUtils;

/**
 * @author Mauro Gonzalez
 *
 */
@RestController
@RequestMapping("/api/importar")
public class ImportarProyectoController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ImportarProyectoController.class);

	@Autowired
	private ImportarProyectoService service;

	@Autowired
	Environment env;

	@Value("${save.archivos.proyecto.error.path}")
	private String PATH_ARCHIVOS_ERRORES;

	@RequestMapping(path = "/preview/{idJurisdiccion}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResultadoProcesamiento previewImportarProyecto(final MultipartFile archivoAImportar,
			@PathVariable final int idJurisdiccion) throws IOException {
		ResultadoProcesamiento resultadoPreview = null;

		if (!validarExtensionArchivoExcel(archivoAImportar)) {
			ByteArrayInputStream bis = null;
			XSSFWorkbook workbook = null;
			try {
				bis = new ByteArrayInputStream(archivoAImportar.getBytes());
				workbook = new XSSFWorkbook(bis);
				resultadoPreview = service.previewProyectos(workbook);

				saveFile(archivoAImportar, idJurisdiccion, resultadoPreview);

				bis.close();
				workbook.close();
			} catch (IOException e) {
				if (bis != null) {
					bis.close();
				}
				if (workbook != null) {
					workbook.close();
				}
				e.printStackTrace();
				LOGGER.debug(e.getMessage());
				resultadoPreview = new ResultadoProcesamiento();
				resultadoPreview.getErroresDeSolapa().add("Hubo un problema general en la lectura del Excel.");

			}
		} else {
			resultadoPreview = new ResultadoProcesamiento();
			resultadoPreview.setErrorGenerico("El archivo seleccionado es inválido");
		}
		return resultadoPreview;

	}

	@RequestMapping(path = "/download/template/proyecto", method = RequestMethod.GET)
	public void downloadTemplateProyecto(HttpServletResponse response) throws IOException {
		FileInputStream fis = new FileInputStream(env.getProperty("download.template.proyecto.path"));

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition",
				"attachment; filename = " + env.getProperty("download.template.proyecto.file"));

		IOUtils.copy(fis, response.getOutputStream());
		response.flushBuffer();
		fis.close();

	}

	@RequestMapping(path = "/download/error/{nombreArchivo:.+}/{idJurisdiccion}", method = RequestMethod.GET)
	public void downloadErrorImportacion(@PathVariable final String nombreArchivo,
			@PathVariable final int idJurisdiccion, HttpServletResponse response) throws IOException {
		FileInputStream fis = getArchivoError(nombreArchivo, idJurisdiccion);

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment; filename = " + nombreArchivo);

		IOUtils.copy(fis, response.getOutputStream());
		response.flushBuffer();
		fis.close();

	}

	@RequestMapping(path = "/proyecto/{nombreArchivo:.+}/{idJurisdiccion}/{pisarProyectos}", method = RequestMethod.GET)
	public ResultadoProcesamiento importarProyecto(@PathVariable final String nombreArchivo,
			@PathVariable final Integer idJurisdiccion, @PathVariable final boolean pisarProyectos,
			HttpServletResponse response, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
					throws ParseException, JOSEException, SignatureVerificationException, IOException {
		ResultadoProcesamiento resultadoProcesamiento = null;
		FileInputStream fis = null;
		XSSFWorkbook workbook = null;
		try {
			fis = getArchivoError(nombreArchivo, idJurisdiccion);
			workbook = new XSSFWorkbook(fis);

			resultadoProcesamiento = service.importarSolapaProyectos(workbook, pisarProyectos,
					DSUtils.getMailDelUsuarioDelToken(token));

			fis.close();
			workbook.close();

		} catch (NullPointerException | IOException | InvalidFormatException e) {
			if (fis != null) {
				fis.close();
			}
			if (workbook != null) {
				workbook.close();
			}
			resultadoProcesamiento = agregarResultadoInesperado(e);
		}
		return resultadoProcesamiento;
	}

	@RequestMapping(path = "/proyecto/priorizado/preview/{idJurisdiccion}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ProyectosPriorizadosResultadoProcesamiento previewProyectoPriorizado(final MultipartFile archivoAImportar,
			@PathVariable final int idJurisdiccion, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
					throws IOException, ParseException, JOSEException, SignatureVerificationException {
		ProyectosPriorizadosResultadoProcesamiento resultadoPreview = null;

		if (!validarExtensionArchivoExcel(archivoAImportar)) {

			ByteArrayInputStream bis = new ByteArrayInputStream(archivoAImportar.getBytes());
			XSSFWorkbook workbook = new XSSFWorkbook(bis);
			resultadoPreview = service.validarSolapaProyectosPriorizados(workbook, DSUtils.getMailDelUsuarioDelToken(token));
			saveFile(archivoAImportar, idJurisdiccion, resultadoPreview);

			bis.close();
			workbook.close();
		} else {
			resultadoPreview = new ProyectosPriorizadosResultadoProcesamiento();
			resultadoPreview.setErrorGenerico("El archivo seleccionado es inválido");
		}

		return resultadoPreview;

	}

	@RequestMapping(path = "/proyecto/priorizado/{nombreArchivo:.+}/{idJurisdiccion}", method = RequestMethod.GET)
	public ResultadoProcesamiento importarProyectoPriorizado(@PathVariable final String nombreArchivo,
			@PathVariable final int idJurisdiccion, @RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
					throws IOException, ParseException, JOSEException, SignatureVerificationException {
		FileInputStream fis = getArchivoError(nombreArchivo, idJurisdiccion);
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		ResultadoProcesamiento resultadoImportacion;

		try {
			resultadoImportacion = service.importarSolapaProyectosPriorizados(workbook,
					DSUtils.getMailDelUsuarioDelToken(token));
			fis.close();
			workbook.close();
		} catch (InvalidFormatException | ESBException | JMSException e) {
			if (fis != null) {
				fis.close();
			}
			if (workbook != null) {
				workbook.close();
			}
			resultadoImportacion = agregarResultadoInesperado(e);
		}

		return resultadoImportacion;

	}

	private void saveFile(final MultipartFile archivoAImportar, final int idJurisdiccion,
			ResultadoProcesamiento resultadoPreview) throws IOException {
		Date now = new Date();
		String pathParaGuardar = env.getProperty("save.archivos.proyecto.error.path").replace("idJurisdiccion",
				String.valueOf(idJurisdiccion));
		String nombreArchivo = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(now) + ".xlsx";

		if (Files.notExists(Paths.get(pathParaGuardar))) {
			Files.createDirectories(Paths.get(pathParaGuardar));
		}
		archivoAImportar.transferTo(new File(pathParaGuardar + nombreArchivo));
		resultadoPreview.setNombreArchivoError(nombreArchivo);
	}

	private FileInputStream getArchivoError(final String nombreArchivo, final int idJurisdiccion)
			throws FileNotFoundException {
		return new FileInputStream(env.getProperty("save.archivos.proyecto.error.path").concat(nombreArchivo)
				.replace("idJurisdiccion", String.valueOf(idJurisdiccion)));
	}

	private ResultadoProcesamiento agregarResultadoInesperado(Exception e) {
		ResultadoProcesamiento resultadoProcesamiento = new ResultadoProcesamiento();
		resultadoProcesamiento.setErrorGenerico(
				"Hubo un problema en el servicio de importación. Intente nuevamente. \n" + e.getMessage());
		LOGGER.debug(e.getMessage());
		e.printStackTrace();
		return resultadoProcesamiento;
	}

	private boolean validarExtensionArchivoExcel(final MultipartFile archivoAImportar) {
		int splitedFileNameLength = archivoAImportar.getName().split("\\.").length - 1;
		return "xlsx".equalsIgnoreCase(archivoAImportar.getName().split("\\.")[splitedFileNameLength]);
	}
}
