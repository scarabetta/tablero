package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.HitoProyecto;
import ar.gob.buenosaires.esb.exception.ESBException;

public interface HitoProyectoService {
	List<HitoProyecto> getHitosDeProyecto();

	HitoProyecto getHitoProyectoPorId(Long id);

	HitoProyecto createHitoProyecto(HitoProyecto hitoProyecto) throws ESBException;

	HitoProyecto updateHitoProyecto(HitoProyecto hitoProyecto) throws ESBException;

	void deleteHitoDeProyecto(Long id) throws ESBException;
}
