package cl.duoc.semana5.dto;

import cl.duoc.semana5.model.Producto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {
  private int id;
  private String nombre;
  private int precio;

  public static ProductoResponseDTO from(Producto producto) {
    return new ProductoResponseDTO(producto.getId(), producto.getNombre(), producto.getPrecio());
  }

}
