package ar.gob.buenosaires.dao.jpa.indicadorEstrategico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.IndicadorEstrategico;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;

public interface IndicadorEstrategicoJpaDao extends JpaRepository<IndicadorEstrategico, Long> {
	
	List<IndicadorEstrategico> findByObjetivoJurisdiccional(ObjetivoJurisdiccional objetivoJurisdiccional);

}
