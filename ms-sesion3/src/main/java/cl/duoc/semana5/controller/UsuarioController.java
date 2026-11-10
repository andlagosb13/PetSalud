package cl.duoc.semana5.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cl.duoc.semana5.dto.UsuarioResponseDTO;
import cl.duoc.semana5.model.Usuario;
import cl.duoc.semana5.service.UsuarioService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;


@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

  @Autowired
  private UsuarioService usuarioService;

  @GetMapping
  public ResponseEntity<List<UsuarioResponseDTO>> listarUsuarios() {
    List<UsuarioResponseDTO> usuarios = usuarioService.getAllUsuarios()
        .stream()
        .map(UsuarioResponseDTO::from)
        .toList(); 
    return ResponseEntity.ok(usuarios);
  }

  @GetMapping("/{id}")
  public ResponseEntity<UsuarioResponseDTO> obtenerUsuarioPorId(@PathVariable @Positive Long id) {
    UsuarioResponseDTO usuario = UsuarioResponseDTO.from(usuarioService.getUsuarioById(id));
    return ResponseEntity.ok(usuario);
  }

  @PostMapping
  public ResponseEntity<UsuarioResponseDTO> agregarUsuario(@Valid @RequestBody Usuario usuario) {
    UsuarioResponseDTO createdUsuario = UsuarioResponseDTO.from(usuarioService.addUsuario(usuario));
    return ResponseEntity.ok(createdUsuario);
  }

  @PutMapping("/{id}")
  public ResponseEntity<UsuarioResponseDTO> actualizarUsuario(@PathVariable @Positive Long id, @Valid @RequestBody Usuario usuario) {
    return usuarioService.updateUsuario(id, usuario)
        .map(UsuarioResponseDTO::from)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> eliminarUsuario(@PathVariable @Positive Long id) {
    return usuarioService.deleteUsuario(id)
        ? ResponseEntity.noContent().build()
        : ResponseEntity.notFound().build();
  }

}
