package co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CategoriaJpaRepository extends JpaRepository<CategoriaEntity, Long> {
    List<CategoriaEntity> findAllByOrderByNombreAsc();
}
