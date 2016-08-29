package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ProyectoService {

	List<Proyecto> getProyectos();

	Proyecto getProyectoPorNombre(String nombre);

	Proyecto getProyectoPorId(Long id);

	Proyecto getProyectoPorCodigo(String codigo);

	Proyecto createProyecto(Proyecto proyecto) throws ESBException;

	Proyecto updateProyecto(Proyecto proyecto) throws ESBException;

	void deleteProyecto(Long id) throws ESBException;
}