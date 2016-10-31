package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.TipoObra;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface TipoObraService {

	TipoObra createTipoObra(TipoObra tipoObra);

	TipoObra updateTipoObra(TipoObra tipoObra);

	void deleteTipoObra(Long id) throws ESBException;

	TipoObra getTipoObraPorId(Long id);

	List<TipoObra> getTipoObras();

}
