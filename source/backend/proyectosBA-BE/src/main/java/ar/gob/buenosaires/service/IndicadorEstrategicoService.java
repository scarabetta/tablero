package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.IndicadorEstrategico;

public interface IndicadorEstrategicoService {

	List<IndicadorEstrategico> getIndicadoresEstrategicos();

	IndicadorEstrategico getIndicadorEstrategicoPorId(Long id);
	
	IndicadorEstrategico createIndicadorEstrategico(IndicadorEstrategico indicadorEstrategico);
	
	IndicadorEstrategico updateIndicadorEstrategico(IndicadorEstrategico indicadorEstrategico);
	
	void deleteIndicadorEstrategico (IndicadorEstrategico indicadorEstrategico);
}
