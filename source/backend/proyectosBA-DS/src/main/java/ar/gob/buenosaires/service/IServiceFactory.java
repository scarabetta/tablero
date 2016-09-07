package ar.gob.buenosaires.service;

public interface IServiceFactory {

	ProyectoService getProyectoService();

	JurisdiccionService getjurisdiccionService();

	ObjetivoOperativoService getObjetivoOperativoService();

	ObjetivoJurisdiccionalService getObjetivoJurisdiccionalService();

	ImportarProyectoService getImportarProyectoService();

	EjeDeGobiernoService getEjeDeGobiernoService();

	UsuarioService getUsuarioService();

	ComunaService getComunaService();
	
	AreaService getAreaService();
	
	PoblacionMetaService getPoblacionMetaService();
}
