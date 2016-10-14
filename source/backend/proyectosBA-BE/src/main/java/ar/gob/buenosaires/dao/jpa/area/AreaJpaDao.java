package ar.gob.buenosaires.dao.jpa.area;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.Area;

public interface AreaJpaDao extends JpaRepository<Area, Long> {

	List<Area> findByNombre(String nombre);
	
	List<Area> findByidJurisdiccion(long idJurisdiccion);

	Area findByNombreAndIdJurisdiccion(String nombre, Long idIdJurisdiccion);

	List<Area> findByIdJurisdiccion(Long idIdJurisdiccion);

}