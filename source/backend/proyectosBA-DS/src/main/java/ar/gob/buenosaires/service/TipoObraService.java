package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.TipoObra;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface TipoObraService {

	List<TipoObra> getTiposObras() throws ESBException, JMSException;

	TipoObra getTipoObraPorId(Long id) throws ESBException, JMSException;

	TipoObra createTipoObra(TipoObra tipoObra, String mailDelUsuarioDelToken) throws ESBException, JMSException;

	TipoObra updateTipoObra(TipoObra tipoObra, String mailDelUsuarioDelToken) throws ESBException, JMSException;

	void deleteTipoObra(String id, String mailDelUsuarioDelToken) throws ESBException, JMSException;


}
