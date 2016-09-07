package ar.gob.buenosaires.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import ar.gob.buenosaires.service.AreaService;
import ar.gob.buenosaires.service.ComunaService;
import ar.gob.buenosaires.service.EjeDeGobiernoService;
import ar.gob.buenosaires.service.IServiceFactory;
import ar.gob.buenosaires.service.ImportarProyectoService;
import ar.gob.buenosaires.service.JurisdiccionService;
import ar.gob.buenosaires.service.ObjetivoJurisdiccionalService;
import ar.gob.buenosaires.service.ObjetivoOperativoService;
import ar.gob.buenosaires.service.PoblacionMetaService;
import ar.gob.buenosaires.service.ProyectoService;
import ar.gob.buenosaires.service.UsuarioService;

@Component
public class ServiceFactoryImpl implements IServiceFactory {

	@Autowired
	private PoblacionMetaService poblacionMetaService;

	@Autowired
	private ProyectoServiceImpl proyectoService;

	@Autowired
	private ObjetivoJurisdiccionalService ojService;

	@Autowired
	private ObjetivoOperativoService ooService;

	@Autowired
	private EjeDeGobiernoService edgService;

	@Autowired
	private ImportarProyectoService importadorService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private JurisdiccionService jurisdiccionService;

	@Autowired
	private ComunaService comunaService;

	@Autowired
	private AreaService areaService;

	@Override
	public ProyectoService getProyectoService() {
		return this.proyectoService;
	}

	@Override
	public JurisdiccionService getjurisdiccionService() {
		return this.jurisdiccionService;
	}

	@Override
	public ObjetivoOperativoService getObjetivoOperativoService() {
		return this.ooService;
	}

	@Override
	public ObjetivoJurisdiccionalService getObjetivoJurisdiccionalService() {
		return this.ojService;
	}

	@Override
	public ImportarProyectoService getImportarProyectoService() {
		return this.importadorService;
	}

	@Override
	public EjeDeGobiernoService getEjeDeGobiernoService() {
		return this.edgService;
	}

	@Override
	public UsuarioService getUsuarioService() {
		return this.usuarioService;
	}

	@Override
	public ComunaService getComunaService() {
		return this.comunaService;
	}

	@Override
	public PoblacionMetaService getPoblacionMetaService() {
		return poblacionMetaService;
	}

	@Override
	public AreaService getAreaService() {
		return areaService;
	}

}
