package ar.gob.buenosaires.dao.jpa.temaTransversal;

import org.springframework.data.jpa.repository.JpaRepository;

import ar.gob.buenosaires.domain.TemaTransversal;

public interface TemaTransversalJpaDao extends JpaRepository<TemaTransversal, Long> {
	
	TemaTransversal findByTemaTransversal(String temaTransversal);
	
}
