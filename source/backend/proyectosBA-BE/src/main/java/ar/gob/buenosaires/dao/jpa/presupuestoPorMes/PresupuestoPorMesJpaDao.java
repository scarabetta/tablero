package ar.gob.buenosaires.dao.jpa.presupuestoPorMes;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.PresupuestoPorMes;

public interface PresupuestoPorMesJpaDao extends JpaRepository<PresupuestoPorMes, Serializable>{

}
