package co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence;

import co.edu.pascualbravo.ockly.domain.model.Categoria;
import co.edu.pascualbravo.ockly.domain.port.out.CategoriaRepositoryPort;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class CategoriaRepositoryJpaAdapter implements CategoriaRepositoryPort {
    private final CategoriaJpaRepository jpa;
    private final CatalogoPersistenceMapper mapper;
    public CategoriaRepositoryJpaAdapter(CategoriaJpaRepository jpa, CatalogoPersistenceMapper mapper) {
        this.jpa = jpa; this.mapper = mapper;
    }
    public List<Categoria> listar() { return jpa.findAllByOrderByNombreAsc().stream().map(mapper::toDomain).toList(); }
    public Optional<Categoria> buscar(Long id) { return jpa.findById(id).map(mapper::toDomain); }
    public Categoria guardar(Categoria entrada) { return mapper.toDomain(jpa.save(mapper.toEntity(entrada))); }
    public void eliminar(Long id) { jpa.deleteById(id); }
    public long contar() { return jpa.count(); }
}
