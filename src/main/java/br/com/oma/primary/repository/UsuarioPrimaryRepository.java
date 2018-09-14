package br.com.oma.primary.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.oma.primary.domain.UsuarioPrimary;

@Repository
public interface UsuarioPrimaryRepository extends JpaRepository<UsuarioPrimary, Integer>{

	@Procedure("sp_usuario")
	Optional<UsuarioPrimary> findById(@Param("id") Integer param);
	
}
