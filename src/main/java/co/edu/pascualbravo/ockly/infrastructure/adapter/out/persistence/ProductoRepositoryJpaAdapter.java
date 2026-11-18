package co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence;

import co.edu.pascualbravo.ockly.domain.model.Producto;
import co.edu.pascualbravo.ockly.domain.port.out.ProductoRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class ProductoRepositoryJpaAdapter implements ProductoRepositoryPort {
    private final ProductoJpaRepository jpa;
    private final CatalogoPersistenceMapper mapper;
    public ProductoRepositoryJpaAdapter(ProductoJpaRepository jpa, CatalogoPersistenceMapper mapper) {
        this.jpa = jpa; this.mapper = mapper;
    }
    public List<Producto> listar() { return jpa.findAllByOrderByNombreAsc().stream().map(mapper::toDomain).toList(); }
    public Optional<Producto> buscar(Long id) { return jpa.findById(id).map(mapper::toDomain); }
    public Producto guardar(Producto entrada) { return mapper.toDomain(jpa.save(mapper.toEntity(entrada))); }
    public void eliminar(Long id) { jpa.deleteById(id); }
    public long contar() { return jpa.count(); }

    public List<Producto> porCategoria(Long id) {
        return jpa.findByCategoriaIdOrderByNombreAsc(id).stream().map(mapper::toDomain).toList();
    }
    public long contarPorCategoria(Long id) { return jpa.countByCategoriaId(id); }
}
