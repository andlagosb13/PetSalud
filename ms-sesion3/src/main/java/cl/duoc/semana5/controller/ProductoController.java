package cl.duoc.semana5.controller;

import java.net.URI;
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
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import cl.duoc.semana5.dto.ProductoResponseDTO;
import cl.duoc.semana5.model.Producto;
import cl.duoc.semana5.service.ProductoService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;

@RestController
@RequestMapping("/api/productos")
public class ProductoController {

  @Autowired
  private ProductoService productoService;

  @GetMapping
  public ResponseEntity<List<ProductoResponseDTO>> getAllProductos() {
    List<ProductoResponseDTO> productos = productoService.getAllProductos()
        .stream()
        .map(ProductoResponseDTO::from)
        .toList();
    return ResponseEntity.ok(productos);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Producto> getProductoById(@PathVariable @Positive int id) {
    // return productoService.getProductoById(id)
    //     .map(ResponseEntity::ok)
    //     .orElse(ResponseEntity.notFound().build());
    Producto producto = productoService.getProductoByIdOrThrow(id);
    return ResponseEntity.ok(producto);
  }

  @PostMapping
  public ResponseEntity<Producto> addProducto(@Valid @RequestBody Producto producto) {
    Producto createdProducto = productoService.addProducto(producto);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(createdProducto.getId()).toUri();
    return ResponseEntity.created(location).body(createdProducto);
    // return ResponseEntity.ok(createdProducto);
  }

  @PutMapping("/{id}")
  public ResponseEntity<Producto> updateProducto(@PathVariable @Positive int id, @Valid @RequestBody Producto producto) {
    return productoService.updateProducto(id, producto)
        .map(ResponseEntity::ok)
        .orElse(ResponseEntity.notFound().build());
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteProducto(@PathVariable @Positive int id) {
    return productoService.deleteProducto(id)
      ? ResponseEntity.noContent().build()
      : ResponseEntity.notFound().build();
  }
}
