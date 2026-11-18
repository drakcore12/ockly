package co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface ProductoJpaRepository extends JpaRepository<ProductoEntity, Long> {
    // La vista MVC necesita el nombre de la categoría después de cerrar la transacción.
    @EntityGraph(attributePaths = "categoria")
    List<ProductoEntity> findAllByOrderByNombreAsc();

    @EntityGraph(attributePaths = "categoria")
    List<ProductoEntity> findByCategoriaIdOrderByNombreAsc(Long categoriaId);

    @Override
    @EntityGraph(attributePaths = "categoria")
    Optional<ProductoEntity> findById(Long id);

    long countByCategoriaId(Long categoriaId);
}
