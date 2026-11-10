package cl.duoc.semana5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import cl.duoc.semana5.model.Producto;

@Repository
public interface ProductoRepo extends JpaRepository<Producto, Integer> {

}
