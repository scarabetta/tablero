package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.EjeDeGobierno;

public interface EjeDeGobiernoService {

	List<EjeDeGobierno> getEjesDeGobierno();

	EjeDeGobierno getEjeDeGobiernoPorNombre(String nombre);

	EjeDeGobierno getEjeDeGobiernoPorId(Long id);	
	
	EjeDeGobierno createEjeDeGobierno(EjeDeGobierno ejeDeGobierno);
	
	EjeDeGobierno updateEjeDeGobierno (EjeDeGobierno ejeDeGobierno);
	
	void deleteEjeDeGobierno (EjeDeGobierno ejeDeGobierno);
}
