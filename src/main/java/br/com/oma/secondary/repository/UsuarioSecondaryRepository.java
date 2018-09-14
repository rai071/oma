package br.com.oma.secondary.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.oma.secondary.domain.UsuarioSecondary;

@Repository
public interface UsuarioSecondaryRepository extends JpaRepository<UsuarioSecondary, Integer>{
	UsuarioSecondary findByNome(String valor);
	
	@Query(value="exec sp_usuario @id = :secondaryId", nativeQuery = true)
	List<UsuarioSecondary> busca(@Param("secondaryId") Integer secondaryId);
}
