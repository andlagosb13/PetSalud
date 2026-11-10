package cl.duoc.semana5.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Usuario {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter @Setter Long id;

  @NotBlank(message = "Username no puede estar vacío")
  @Size(max = 50, message = "Username no puede tener más de 50 caracteres")
  private @Getter @Setter String username;

  @NotBlank(message = "Password no puede estar vacío")
  private @Getter @Setter String password;
}
