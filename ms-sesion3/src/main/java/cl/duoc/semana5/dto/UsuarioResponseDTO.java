package cl.duoc.semana5.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioResponseDTO {

  private Long id;
  private String username;

  public static UsuarioResponseDTO from(cl.duoc.semana5.model.Usuario usuario) {
    return new UsuarioResponseDTO(usuario.getId(), usuario.getUsername());
  }

}
