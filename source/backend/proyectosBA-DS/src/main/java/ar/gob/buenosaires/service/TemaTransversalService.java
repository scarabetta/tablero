package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.TemaTransversal;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface TemaTransversalService {

	List<TemaTransversal> getTemasTransversales() throws ESBException, JMSException;

	TemaTransversal getTemaTransversalPorId(Long id) throws ESBException, JMSException;

	TemaTransversal createTemaTransversal(TemaTransversal TemaTransversal) throws ESBException, JMSException;

	TemaTransversal updateTemaTransversal(TemaTransversal TemaTransversal) throws ESBException, JMSException;

	void deleteTemaTransversal(String id) throws ESBException, JMSException;

}
