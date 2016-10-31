package ar.gob.buenosaires.service;

import java.util.List;

import ar.gob.buenosaires.domain.ReporteProyectosView;
import ar.gob.buenosaires.domain.Usuario;

public interface ReporteProyectosViewService {

	List<ReporteProyectosView> getAllReporteProyectosView(Usuario user);

}
