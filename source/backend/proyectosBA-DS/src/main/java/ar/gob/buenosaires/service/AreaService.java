package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.Area;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface AreaService {

	List<Area> getAreas() throws ESBException, JMSException;

	Area getAreaPorId(String id) throws ESBException, JMSException;

	List<Area> getAreasByName(String nombre) throws ESBException, JMSException;

	Area getAreasByNameAndIdJurisdiccion(String nombre, Long idJurisdiccion) throws ESBException, JMSException;

	List<Area> getAreasByIdJurisdiccion(Long idJurisdiccion) throws ESBException, JMSException;

}

