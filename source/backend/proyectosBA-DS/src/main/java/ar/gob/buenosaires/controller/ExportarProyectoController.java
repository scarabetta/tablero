package ar.gob.buenosaires.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;

import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.ExportarProyectoService;
import ar.gob.buenosaires.service.ProyectoService;

@RestController
@RequestMapping("/api/exportar")
public class ExportarProyectoController {

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportarProyectoController.class);

	@Autowired
	private ProyectoService proyectoService;

	@Autowired
	private ExportarProyectoService service;


	@Autowired
	Environment env;

	@RequestMapping(path = "/proyectos", method = RequestMethod.GET)
	public void exportarProyecto(HttpServletResponse response) throws IOException, ESBException, JMSException {
		FileInputStream fis = new FileInputStream(env.getProperty("exportacion.archivos.proyecto.path"));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheetAt0 = workbook.getSheetAt(0);
		service.generarExcel(sheetAt0);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition",
				"attachment; filename = " + "proyectos_en_priorizacion.xlsx");

		IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
		os.close();
		is.close();
		fis.close();
		workbook.close();

		proyectoService.updatePriorizarProyectos();
	}

	@RequestMapping(path = "resumenProyectos", method = RequestMethod.GET)
	public @ResponseBody JsonNode resumenProyectos() throws ESBException, JMSException{
		return proyectoService.getResumenProyectosPriorizacion();
	}

	@RequestMapping(path = "cancelarPriorizacion", method = RequestMethod.GET)
	public void cancelarPriorizacionDeProyectos() throws ESBException, JMSException{
		proyectoService.cancelarPriorizacionDeProyectos();
	}
}
