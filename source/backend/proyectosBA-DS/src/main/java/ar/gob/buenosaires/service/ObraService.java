package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.Obra;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface ObraService {

	List<Obra> getObras() throws ESBException, JMSException;

	Obra getObraPorId(Long id) throws ESBException, JMSException;

	Obra createObra(Obra obra, String mailDelUsuarioDelToken) throws ESBException, JMSException;

	void deleteObra(String id, String mailDelUsuarioDelToken) throws ESBException, JMSException;

	Obra updateObra(Obra obra, String mailDelUsuarioDelToken) throws ESBException, JMSException;

}
