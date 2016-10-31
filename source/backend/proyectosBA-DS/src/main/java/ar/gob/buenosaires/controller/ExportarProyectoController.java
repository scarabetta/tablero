package ar.gob.buenosaires.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;

import javax.jms.JMSException;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.JsonNode;
import com.nimbusds.jose.JOSEException;

import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.security.jwt.exception.SignatureVerificationException;
import ar.gob.buenosaires.service.ExportarProyectoService;
import ar.gob.buenosaires.service.ProyectoService;
import ar.gob.buenosaires.service.UsuarioService;
import ar.gob.buenosaires.util.DSUtils;

@RestController
@RequestMapping("/api/exportar")
public class ExportarProyectoController {

	private static final String PROPERTY_REPORTE_PROYECTO_PATH = "exportacion.archivos.reporte.proyecto.path";

	private static final String PROPERTY_EXPORTACION_PROYECTO_PATH = "exportacion.archivos.proyecto.path";

	private static final String PROYECTOS_EN_PRIORIZACION_XLSX = "proyectos_en_priorizacion.xlsx";

	private static final String REPORTE_PROYECTOS_XLSX = "reporte_proyectos.xlsx";

	private final static Logger LOGGER = LoggerFactory.getLogger(ExportarProyectoController.class);

	@Autowired
	private ProyectoService proyectoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private ExportarProyectoService service;

	@Autowired
	Environment env;

	@RequestMapping(path = "/proyectos", method = RequestMethod.GET)
	public void exportarProyecto(HttpServletResponse response,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) throws IOException, ESBException,
			JMSException, ParseException, JOSEException, SignatureVerificationException {
		FileInputStream fis = new FileInputStream(env.getProperty(PROPERTY_EXPORTACION_PROYECTO_PATH));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheetAt0 = workbook.getSheetAt(0);
		service.generarExcel(sheetAt0);

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment; filename = " + PROYECTOS_EN_PRIORIZACION_XLSX);

		IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
		os.close();
		is.close();
		fis.close();
		workbook.close();

		proyectoService.updatePriorizarProyectos(DSUtils.getMailDelUsuarioDelToken(token));
	}

	@RequestMapping(path = "/proyectos/reporte", method = RequestMethod.GET)
	public void reporteProyectos(HttpServletResponse response,
			@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token) throws IOException, ESBException,
			JMSException, ParseException, JOSEException, SignatureVerificationException {

		FileInputStream fis = new FileInputStream(env.getProperty(PROPERTY_REPORTE_PROYECTO_PATH));
		XSSFWorkbook workbook = new XSSFWorkbook(fis);
		XSSFSheet sheetAt0 = workbook.getSheetAt(0);
		service.generarExcelReporte(sheetAt0, DSUtils.getMailDelUsuarioDelToken(token));

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		workbook.write(os);
		ByteArrayInputStream is = new ByteArrayInputStream(os.toByteArray());
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("content-disposition", "attachment; filename = " + REPORTE_PROYECTOS_XLSX);

		IOUtils.copy(is, response.getOutputStream());
		response.flushBuffer();
		os.close();
		is.close();
		fis.close();
		workbook.close();
	}

	@RequestMapping(path = "resumenProyectos", method = RequestMethod.GET)
	public @ResponseBody JsonNode resumenProyectos() throws ESBException, JMSException {
		return proyectoService.getResumenProyectosPriorizacion();
	}

	@RequestMapping(path = "cancelarPriorizacion", method = RequestMethod.GET)
	public void cancelarPriorizacionDeProyectos(@RequestHeader(value = HttpHeaders.AUTHORIZATION) String token)
			throws ESBException, JMSException, ParseException, JOSEException, SignatureVerificationException {
		proyectoService.cancelarPriorizacionDeProyectos(DSUtils.getMailDelUsuarioDelToken(token));
	}
}
