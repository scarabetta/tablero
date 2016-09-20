package ar.gob.buenosaires.otrasEtiquetas;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.OtraEtiqueta;

public interface OtrasEtiquetasJpaDao extends JpaRepository<OtraEtiqueta, Long> {
	
	OtraEtiqueta findByEtiqueta(String etiqueta);
	
}