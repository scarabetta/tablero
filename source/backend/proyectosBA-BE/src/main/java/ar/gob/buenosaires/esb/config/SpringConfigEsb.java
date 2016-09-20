package ar.gob.buenosaires.esb.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import ar.gob.buenosaires.esb.consumer.EsbConsumer;
import ar.gob.buenosaires.esb.consumer.EsbServer;
import ar.gob.buenosaires.esb.handler.AreaHandler;
import ar.gob.buenosaires.esb.handler.CompromisoPublicoHandler;
import ar.gob.buenosaires.esb.handler.ComunaHandler;
import ar.gob.buenosaires.esb.handler.EjeDeGobiernoHandler;
import ar.gob.buenosaires.esb.handler.EtiquetasHandler;
import ar.gob.buenosaires.esb.handler.ExportacionProyectoViewHandler;
import ar.gob.buenosaires.esb.handler.JurisdiccionHandler;
import ar.gob.buenosaires.esb.handler.ObjetivoJurisdiccionalHandler;
import ar.gob.buenosaires.esb.handler.ObjetivoOperativoHandler;
import ar.gob.buenosaires.esb.handler.OtrasEtiquetasHandler;
import ar.gob.buenosaires.esb.handler.PoblacionMetaHandler;
import ar.gob.buenosaires.esb.handler.ProyectoHandler;
import ar.gob.buenosaires.esb.handler.RolHandler;
import ar.gob.buenosaires.esb.handler.TemaTransversalHandler;
import ar.gob.buenosaires.esb.handler.UsuarioHandler;

@Configuration
@Profile({"dev", "prod", "mock" })
public class SpringConfigEsb {

	@Bean(name = "EsbServerPBA")
	public EsbServer getEsbServer(){
		return new EsbServer();
	}

	@Bean
	public JurisdiccionHandler getJurisdiccionHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		JurisdiccionHandler jurisdiccionHandler = new JurisdiccionHandler();
		((EsbServer)consumer).addHandler(jurisdiccionHandler);
		return jurisdiccionHandler;
	}

	@Bean
	public ObjetivoJurisdiccionalHandler getObjetivoJurisdiccionalHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		ObjetivoJurisdiccionalHandler objetivoJurisdiccionalHandler = new ObjetivoJurisdiccionalHandler();
		((EsbServer)consumer).addHandler(objetivoJurisdiccionalHandler);
		return objetivoJurisdiccionalHandler;
	}

	@Bean
	public ObjetivoOperativoHandler getObjetivoOperativoHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		ObjetivoOperativoHandler objetivoOperativoHandler = new ObjetivoOperativoHandler();
		((EsbServer)consumer).addHandler(objetivoOperativoHandler);
		return objetivoOperativoHandler;
	}


	@Bean
	public ProyectoHandler getProyectoHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		ProyectoHandler proyectoHandler = new ProyectoHandler();
		((EsbServer)consumer).addHandler(proyectoHandler);
		return proyectoHandler;
	}

	@Bean
	public EjeDeGobiernoHandler getEjeDeGobiernoHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		EjeDeGobiernoHandler ejeDeGobiernoHandler = new EjeDeGobiernoHandler();
		((EsbServer)consumer).addHandler(ejeDeGobiernoHandler);
		return ejeDeGobiernoHandler;
	}

	@Bean
	public PoblacionMetaHandler getPoblacionMetaHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		PoblacionMetaHandler poblacionMetaHandler = new PoblacionMetaHandler();
		((EsbServer)consumer).addHandler(poblacionMetaHandler);
		return poblacionMetaHandler;
	}

	@Bean
	public ComunaHandler getComunaHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		ComunaHandler comunaHandler = new ComunaHandler();
		((EsbServer)consumer).addHandler(comunaHandler);
		return comunaHandler;
	}

	@Bean
	public AreaHandler getAreaHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		AreaHandler areaHandler = new AreaHandler();
		((EsbServer)consumer).addHandler(areaHandler);
		return areaHandler;
	}

	@Bean
	public UsuarioHandler getUsuarioHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		UsuarioHandler usuarioHandler = new UsuarioHandler();
		((EsbServer)consumer).addHandler(usuarioHandler);
		return usuarioHandler;
	}

	@Bean
	public RolHandler getRolHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		RolHandler rolHandler = new RolHandler();
		((EsbServer)consumer).addHandler(rolHandler);
		return rolHandler;
	}

	@Bean
	public TemaTransversalHandler getTemaTransversalHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		TemaTransversalHandler temaTransversalHandler = new TemaTransversalHandler();
		((EsbServer)consumer).addHandler(temaTransversalHandler);
		return temaTransversalHandler;
	}
	
	@Bean
	public CompromisoPublicoHandler getCompromisoPublicoHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		CompromisoPublicoHandler compromisoPublicoHandler = new CompromisoPublicoHandler();
		((EsbServer)consumer).addHandler(compromisoPublicoHandler);
		return compromisoPublicoHandler;
	}
	
	@Bean
	public OtrasEtiquetasHandler getOtrasEtiquetasHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		OtrasEtiquetasHandler otrasEtiquetasHandler = new OtrasEtiquetasHandler();
		((EsbServer)consumer).addHandler(otrasEtiquetasHandler);
		return otrasEtiquetasHandler;
	}
	
	@Bean
	public EtiquetasHandler getEtiquetasHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		EtiquetasHandler etiquetasHandler = new EtiquetasHandler();
		((EsbServer)consumer).addHandler(etiquetasHandler);
		return etiquetasHandler;
	}

	@Bean
	public ExportacionProyectoViewHandler getExportacionProyectosViewHandler(@Qualifier("EsbServerPBA") EsbConsumer consumer){
		ExportacionProyectoViewHandler exportacionProyectoViewHandlerHandler = new ExportacionProyectoViewHandler();
		((EsbServer)consumer).addHandler(exportacionProyectoViewHandlerHandler);
		return exportacionProyectoViewHandlerHandler;
	}
}
