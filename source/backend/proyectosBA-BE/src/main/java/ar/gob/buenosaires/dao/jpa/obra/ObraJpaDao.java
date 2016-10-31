package ar.gob.buenosaires.dao.jpa.obra;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.Obra;

public interface ObraJpaDao extends JpaRepository<Obra, Long> {

}
