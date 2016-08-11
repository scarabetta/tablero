package ar.gob.buenosaires.dao.jpa.jurisdiccion;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.Jurisdiccion;

public interface JurisdiccionJpaDao extends JpaRepository<Jurisdiccion, Long> {
	
	Jurisdiccion findByNombre(String nombre);
	
	Jurisdiccion findByCodigo(String codigo);

}
