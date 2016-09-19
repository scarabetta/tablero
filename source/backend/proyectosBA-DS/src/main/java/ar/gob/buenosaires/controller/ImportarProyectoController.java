package ar.gob.buenosaires.controller;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.gob.buenosaires.importador.ResultadoProcesamiento;
import ar.gob.buenosaires.service.ImportarProyectoService;

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
			@PathVariable final int idJurisdiccion) {
		ResultadoProcesamiento resultadoPreview = null;
		ByteArrayInputStream bis;
		try {
			bis = new ByteArrayInputStream(archivoAImportar.getBytes());
			XSSFWorkbook workbook = new XSSFWorkbook(bis);
			resultadoPreview = service.previewProyectos(workbook);

			Date now = new Date();
			String pathParaGuardar = env.getProperty("save.archivos.proyecto.error.path").replace("idJurisdiccion",
					String.valueOf(idJurisdiccion));
			String nombreArchivo = new SimpleDateFormat("yyyyMMddHHmmss", Locale.getDefault()).format(now) + ".xlsx";

			if (Files.notExists(Paths.get(pathParaGuardar))) {
				Files.createDirectories(Paths.get(pathParaGuardar));
			}
			archivoAImportar.transferTo(new File(pathParaGuardar + nombreArchivo));
			resultadoPreview.setNombreArchivoError(nombreArchivo);

		} catch (IOException e) {
			e.printStackTrace();
			LOGGER.debug(e.getMessage());
			resultadoPreview = new ResultadoProcesamiento();
			resultadoPreview.getErroresDeSolapa().add("Hubo un problema general en la lectura del Excel.");

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
		FileInputStream fis = new FileInputStream(env.getProperty("save.archivos.proyecto.error.path")
				.concat(nombreArchivo).replace("idJurisdiccion", String.valueOf(idJurisdiccion)));

		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment; filename = " + nombreArchivo);

		IOUtils.copy(fis, response.getOutputStream());
		response.flushBuffer();
		fis.close();

	}

	@RequestMapping(path = "/proyecto/{nombreArchivo:.+}/{idJurisdiccion}/{pisarProyectos}", method = RequestMethod.GET)
	public ResultadoProcesamiento importarProyecto(@PathVariable final String nombreArchivo,
			@PathVariable final Integer idJurisdiccion, @PathVariable final boolean pisarProyectos,
			HttpServletResponse response) {
		ResultadoProcesamiento resultadoProcesamiento = null;
		try {
			FileInputStream fis = new FileInputStream(env.getProperty("save.archivos.proyecto.error.path")
					.concat(nombreArchivo).replace("idJurisdiccion", String.valueOf(idJurisdiccion)));

			XSSFWorkbook workbook = new XSSFWorkbook(fis);

			resultadoProcesamiento = service.importarSolapaProyectos(workbook, pisarProyectos);

			fis.close();
			workbook.close();

		} catch (NullPointerException | IOException | InvalidFormatException e) {
			resultadoProcesamiento = new ResultadoProcesamiento();
			resultadoProcesamiento.setErrorGenerico(
					"Hubo un problema en el servicio de importación. Intente nuevamente. \n" + e.getMessage());
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
		return resultadoProcesamiento;
	}
}
