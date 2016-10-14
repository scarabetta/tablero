package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.EtiquetasMsg;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ProyectoService extends IGenericService{

	List<Proyecto> getProyectos();

	Proyecto getProyectoPorNombre(String nombre);

	Proyecto getProyectoPorNombreYIdJurisdiccionYCiertosEstados(String nombre, Long IdJurisdiccion, List<String> estados);

	Proyecto getProyectoPorNombreYIdJurisdiccion(String nombre, Long IdJurisdiccion);

	Proyecto getProyectoPorId(Long id);

	Proyecto getProyectoPorCodigo(String codigo);

	Proyecto createProyecto(Proyecto proyecto) throws ESBException;

	Proyecto updateProyecto(Proyecto proyecto, Usuario usuario) throws ESBException;

	void deleteProyecto(Long id) throws ESBException;

	List<Proyecto> getProyectosPorEstado(String string);

	void cancelarPriorizacionDeProyectosVerificados();

	void cancelarPriorizacionDeProyectosNoVerificados();

	void iniciarPriorizacionDeProyectos();

	Proyecto etiquetarProyecto(Long id, EtiquetasMsg etiquetas) throws ESBException;

	List<Proyecto> buscarResumenProyectosPriorizacion();
}