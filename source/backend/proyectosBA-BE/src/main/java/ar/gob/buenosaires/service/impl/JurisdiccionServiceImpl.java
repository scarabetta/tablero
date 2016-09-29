package ar.gob.buenosaires.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.fest.util.VisibleForTesting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.gob.buenosaires.dao.jpa.area.AreaRepository;
import ar.gob.buenosaires.dao.jpa.area.AreaRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionJpaDao;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionRepository;
import ar.gob.buenosaires.dao.jpa.jurisdiccion.JurisdiccionRepositoryImpl;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepository;
import ar.gob.buenosaires.dao.jpa.proyecto.ProyectoRepositoryImpl;
import ar.gob.buenosaires.domain.EstadoProyecto;
import ar.gob.buenosaires.domain.Jurisdiccion;
import ar.gob.buenosaires.domain.JurisdiccionResumen;
import ar.gob.buenosaires.domain.ObjetivoJurisdiccional;
import ar.gob.buenosaires.domain.ObjetivoOperativo;
import ar.gob.buenosaires.domain.Proyecto;
import ar.gob.buenosaires.domain.Usuario;
import ar.gob.buenosaires.esb.exception.CodigoError;
import ar.gob.buenosaires.esb.exception.ESBException;
import ar.gob.buenosaires.service.JurisdiccionService;

@Service
public class JurisdiccionServiceImpl implements JurisdiccionService {

	private static final int POSICION_CODIGO = 4;

	private static final int POSICION_MISION = 3;

	private static final int POSICION_ABREVIATURA = 2;

	private static final int POSICION_NOMBRE = 1;

	private static final int POSICION_ID = 0;

	@Autowired
	private JurisdiccionRepository repositorio;
	
	@Autowired
	private ProyectoRepository repositorioProyecto;
	
	@Autowired
	private AreaRepository repositorioArea;


	@Override
	public List<Jurisdiccion> getJurisdicciones() {
		return getJurisdiccionDAO().findAll();
	}
	
	@Override
	public List<JurisdiccionResumen> getJurisdiccionesResumen(Usuario user) {
		List<JurisdiccionResumen> resumenJurisdicciones = new ArrayList<>();
		
		if (user != null && user.tienePerfilSecretaria()) {
			resumenJurisdicciones = getJurisdiccionDAO().findAllResumen();
		} else {
			List<Object[]> findResumenParaUsuario = getJurisdiccionDAO().findResumenParaUsuario(user.getIdUsuario());
			for (Object object[] : findResumenParaUsuario) {				
				agregarJurisdiccionAResumen(resumenJurisdicciones, object); 
			}
		}
		
		agregarAreas(resumenJurisdicciones);
		return resumenJurisdicciones;
	}

	private void agregarAreas(List<JurisdiccionResumen> resumenJurisdicciones) {
		for (JurisdiccionResumen jurisdiccionResumen : resumenJurisdicciones) {
			jurisdiccionResumen.setAreas(repositorioArea.getAreaJpaDao().findByidJurisdiccion(jurisdiccionResumen.getIdJurisdiccion()));
		}
	}

	private void agregarJurisdiccionAResumen(List<JurisdiccionResumen> resumenJurisdicciones, Object[] object) {
		int id = (int)object[POSICION_ID];
		String nombre = (String)object[POSICION_NOMBRE];
		String abreviatura = (String)object[POSICION_ABREVIATURA];
		String mision = (String)object[POSICION_MISION];
		String codigo = (String)object[POSICION_CODIGO];
		resumenJurisdicciones.add(new JurisdiccionResumen(id, nombre, abreviatura, mision, codigo));
	}
	
	@Override
	public List<Jurisdiccion> getJurisdiccionesParaSecretaria() {
		return getJurisdiccionDAO().findAllParaSecretaria();
	}

	@Override
	public Jurisdiccion getJurisdiccionPorNombre(String nombre) {
		return getJurisdiccionDAO().findByNombre(nombre);
	}

	@Override
	public Jurisdiccion getJurisdiccionPorId(Long id) {
		return getJurisdiccionDAO().findOne(id);
	}
	
	@Override
	public Jurisdiccion getJurisdiccionPorIdParaSecretaria(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public Jurisdiccion getJurisdiccionPorCodigo(String codigo) {
		return getJurisdiccionDAO().findByCodigo(codigo);
	}
	
	@Override
	public Jurisdiccion createJurisdiccion(Jurisdiccion jurisdiccion) {
		return getJurisdiccionDAO().save(jurisdiccion);
	}
	
	@Override
	public Jurisdiccion updateJurisdiccion(Jurisdiccion jurisdiccion) {
		return getJurisdiccionDAO().save(jurisdiccion);
	}
	
	@Override
	public void deleteJurisdiccion(Long id) throws ESBException {		
		Jurisdiccion jurisdiccion = getJurisdiccionDAO().findOne(id);
		if (jurisdiccion != null) {
			getJurisdiccionDAO().delete(jurisdiccion);
		} else {
			throw new ESBException(CodigoError.JURISDICCION_INEXISTENTE.getCodigo(), "No se encontro Jurisdiccion con id: " + id);
		}
	}
	
	@Override
	public void presentarProyectosCompletos(Long id) throws ESBException {
		Jurisdiccion jurisdiccion = getJurisdiccionDAO().findOne(id);
		List<Long> proyectosCompletos = new ArrayList<Long>();
		if (jurisdiccion != null) {
			getProyectosCompletos(jurisdiccion, proyectosCompletos);
			if (!proyectosCompletos.isEmpty()) {
				repositorioProyecto.getProyectoJpaDao().updateProyectosCompletos(proyectosCompletos);
			}
		} else {
			throw new ESBException(CodigoError.JURISDICCION_INEXISTENTE.getCodigo(), "No se encontro Jurisdiccion con id: " + id);
		}
	}
	
	private void getProyectosCompletos(Jurisdiccion jurisdiccion, List<Long> proyectosCompletos) {
		for(ObjetivoJurisdiccional oj: jurisdiccion.getObjetivosJurisdiccionales()) {
			for(ObjetivoOperativo op : oj.getObjetivosOperativos()) {
				for(Proyecto proy: op.getProyectos()) {
					if(EstadoProyecto.COMPLETO.getName().equals(proy.getEstado())) {
						proyectosCompletos.add(proy.getIdProyecto());
					}
				}
			}
		}
	}

	public JurisdiccionJpaDao getJurisdiccionDAO() {
		return repositorio.getJurisdiccionJpaDao();
	}

	@VisibleForTesting
	public void setJurisdiccionRepository(JurisdiccionRepositoryImpl repo) {
		this.repositorio = repo;
	}
	
	@VisibleForTesting
	public void setProyectoRepository(ProyectoRepositoryImpl repo) {
		this.repositorioProyecto = repo;
	}
	
	@VisibleForTesting
	public void setAreaRepository(AreaRepositoryImpl repo) {
		this.repositorioArea = repo;
	}
}
