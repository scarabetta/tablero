package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ObraService {

	Obra createObra(Obra obra);

	Obra updateObra(Obra obra);

	void deleteObra(Long id) throws ESBException;

	Obra getObraPorId(Long id);

	List<Obra> getObras();

}
