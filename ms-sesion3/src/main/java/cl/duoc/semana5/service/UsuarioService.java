package cl.duoc.semana5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import cl.duoc.semana5.model.Usuario;
import cl.duoc.semana5.repository.UsuarioRepo;

@Service
public class UsuarioService {

  @Autowired
  private UsuarioRepo usuarioRepo;

  @Autowired
  private PasswordEncoder passwordEncoder;

  public List<Usuario> getAllUsuarios() {
    return usuarioRepo.findAll();
  }

  public Usuario getUsuarioById(Long id) {
    return usuarioRepo.findById(id)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
  }

  public Usuario getUsuarioByUsername(String username) {
    return usuarioRepo.findByUsername(username)
        .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + username));
  }

  public Usuario addUsuario(Usuario usuario) {
    // Encode password before saving
    if (usuario.getPassword() != null) {
      usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
    }
    return usuarioRepo.save(usuario);
  }

  public Optional<Usuario> updateUsuario(Long id, Usuario usuario) {
    if (usuarioRepo.existsById(id)) {
      usuario.setId(id); // Aseguramos que el ID del usuario a actualizar sea el mismo que el ID proporcionado
      // If password is provided, encode it. If not provided or blank, keep existing password.
      if (usuario.getPassword() == null || usuario.getPassword().isBlank()) {
        Usuario existing = usuarioRepo.findById(id).orElse(null);
        if (existing != null) {
          usuario.setPassword(existing.getPassword());
        }
      } else {
        usuario.setPassword(passwordEncoder.encode(usuario.getPassword()));
      }
      return Optional.of(usuarioRepo.save(usuario));
    }
    return Optional.empty();
  }

  public boolean deleteUsuario(Long id) {
    if (usuarioRepo.existsById(id)) {
      usuarioRepo.deleteById(id);
      return true;
    }
    return false;
  }


}
