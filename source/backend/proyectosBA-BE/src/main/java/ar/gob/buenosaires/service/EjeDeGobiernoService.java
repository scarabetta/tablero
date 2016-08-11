package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.EjeDeGobierno;

public interface EjeDeGobiernoService {

	List<EjeDeGobierno> getEjesDeGobierno();

	EjeDeGobierno getEjeDeGobiernoPorNombre(String nombre);

	EjeDeGobierno getEjeDeGobiernoPorId(Long id);	
	
//	void createEjeDeGobierno(EjeDeGobierno ejeDeGobierno);
//	
//	void updateEjeDeGobierno (EjeDeGobierno ejeDeGobierno);
//	
//	void deleteEjeDeGobierno (EjeDeGobierno ejeDeGobierno);
}
