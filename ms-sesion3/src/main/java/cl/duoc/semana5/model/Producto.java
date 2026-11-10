package cl.duoc.semana5.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Producto {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private @Getter @Setter int id;

  @NotBlank(message = "El nombre no puede estar vacío")
  @Size(max = 100, message = "El nombre no puede tener más de 100 caracteres")
  private @Getter @Setter String nombre;

  @Min(value = 0, message = "El precio debe ser un valor positivo")
  private @Getter @Setter int precio;

  @Size(max = 255, message = "La descripción no puede tener más de 255 caracteres")
  private @Getter @Setter String descripcion;
}
