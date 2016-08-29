package ar.gob.buenosaires.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import ar.gob.buenosaires.importador.MensajeError;
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

	@RequestMapping(path = "/preview", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<String> previewImportarProyecto(final MultipartFile archivoAImportar) {

		ByteArrayInputStream bis;
		List<String> listaDeErrore = new ArrayList<>();
		try {
			bis = new ByteArrayInputStream(archivoAImportar.getBytes());
			XSSFWorkbook workbook = new XSSFWorkbook(bis);
			List<MensajeError> problemasSolpa = service.validarSolapaProyectos(workbook);

			if (!problemasSolpa.isEmpty()) {
				problemasSolpa.stream().forEach(new Consumer<MensajeError>() {

					@Override
					public void accept(MensajeError t) {
						listaDeErrore.add(t.getMensaje());
					}
				});

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return listaDeErrore;

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

	@RequestMapping(path = "/proyecto", method = RequestMethod.POST)
	public void importarProyecto(final MultipartFile archivoAImportar, final boolean pisarProyectos,
			HttpServletResponse response) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(archivoAImportar.getBytes());
			XSSFWorkbook workbook = new XSSFWorkbook(bis);
			Workbook workbookConErrores = service.importarSolapaProyectos(workbook, pisarProyectos);
			if (workbookConErrores != null) {

				ByteArrayOutputStream os = new ByteArrayOutputStream();
				workbookConErrores.write(os);
				ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("content-disposition", "attachment; filename=errores_de_importacion.xlsx");
				response.setStatus(HttpServletResponse.SC_ACCEPTED);

				IOUtils.copy(is, response.getOutputStream());
				response.flushBuffer();
				is.close();
				os.close();
				workbookConErrores.close();
			} else {
				response.getWriter().print("Ok");

			}
			bis.close();
			workbook.close();

		} catch (NullPointerException | IOException | InvalidFormatException e) {
			try {
				response.getWriter().println("Hubo un problema en el servicio de importación. Intente nuevamente.");
			} catch (IOException e1) {
				LOGGER.debug(e1.getMessage());
				e.printStackTrace();
			}
			LOGGER.debug(e.getMessage());
			e.printStackTrace();
		}
	}
}
