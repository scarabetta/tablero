package ar.gob.buenosaires.service;

import java.util.List;

import javax.jms.JMSException;

import ar.gob.buenosaires.domain.PresupuestoPorMes;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface PresupuestoPorMesService {

	List<PresupuestoPorMes> getPresupuestosPorMes() throws ESBException, JMSException;

	List<PresupuestoPorMes> getPresupuestosPorMesPorProyecto(Long id) throws ESBException, JMSException;

	PresupuestoPorMes getPresupuestoPorMesPorId(Long id) throws ESBException, JMSException;

	PresupuestoPorMes createPresupuestoPorMes(PresupuestoPorMes ppm, String mailDelUsuarioDelToken) throws ESBException, JMSException;

	List<PresupuestoPorMes> createPresupuestosPorMes(List<PresupuestoPorMes> ppms, String mailDelUsuarioDelToken) throws ESBException, JMSException;

	PresupuestoPorMes updatePresupuestoPorMes(PresupuestoPorMes ppm, String mailDelUsuarioDelToken) throws ESBException, JMSException;

	List<PresupuestoPorMes> updatePresupuestosPorMes(List<PresupuestoPorMes> ppms, String mailDelUsuarioDelToken) throws ESBException, JMSException;

	void deletePresupuestoPorMes(String id, String mailDelUsuarioDelToken) throws ESBException, JMSException;

}
