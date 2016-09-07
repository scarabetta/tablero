package ar.gob.buenosaires.dao.jpa.area;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.Area;

public interface AreaJpaDao extends JpaRepository<Area, Long> {

	List<Area> findByNombre(String nombre);

	Area findByNombreAndIdJurisdiccion(String nombre, Long idIdJurisdiccion);

}