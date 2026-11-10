package cl.duoc.semana5.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.semana5.model.Usuario;

@Repository
public interface UsuarioRepo extends JpaRepository<Usuario, Long> {
  Optional<Usuario> findByUsername(String username);
}
