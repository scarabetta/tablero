package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Area;

public interface AreaService {

	List<Area> getAreas();

	Area getAreaPorId(Long id);
	
	List<Area> getAreasPorNombre(String nombre);
	
	Area getAreaPorNombreYIdJurisdiccion(String nombre, Long idIdJurisdiccion);
}
