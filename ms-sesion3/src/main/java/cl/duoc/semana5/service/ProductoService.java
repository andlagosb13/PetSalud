package cl.duoc.semana5.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cl.duoc.semana5.exception.ResourceNotFoundException;
import cl.duoc.semana5.model.Producto;
import cl.duoc.semana5.repository.ProductoRepo;

import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

@Service
public class ProductoService {

  @Autowired
  private ProductoRepo productoRepo;

  public List<Producto> getAllProductos() {
    return productoRepo.findAll();
  }

  public Optional<Producto> getProductoById(int id) {
    return productoRepo.findById(id);
  }

  public Producto getProductoByIdOrThrow(int id) {
    return productoRepo.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
  }

  public Producto addProducto(Producto producto) {
    producto.setNombre(sanitizeInput(producto.getNombre()));
    producto.setDescripcion(sanitizeInput(producto.getDescripcion()));
    return productoRepo.save(producto);
  }

  public Optional<Producto> updateProducto(int id, Producto producto) {
    if (productoRepo.existsById(id)) {
      producto.setId(id); // Aseguramos que el ID del producto a actualizar sea el mismo que el ID proporcionado
      producto.setNombre(sanitizeInput(producto.getNombre()));
      producto.setDescripcion(sanitizeInput(producto.getDescripcion()));
      return Optional.of(productoRepo.save(producto));
    }
    return Optional.empty();
  }

  public boolean deleteProducto(int id) {
    if (productoRepo.existsById(id)) {
      productoRepo.deleteById(id);
      return true;
    }
    return false;
  }

  private String sanitizeInput(String input) {
    if (input == null) return null;
    return Jsoup.clean(input, Safelist.basic());
  }


}
