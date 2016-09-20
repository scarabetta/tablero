package ar.gob.buenosaires.dao.jpa.compromisoPublico;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.CompromisoPublico;

public interface CompromisoPublicoJpaDao extends JpaRepository<CompromisoPublico, Long> {

	CompromisoPublico findByCompromisoPublico(String compromisoPublico);		
	
	List<CompromisoPublico> findByActivo(boolean activo);		
	
}
