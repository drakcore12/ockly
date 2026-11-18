package co.edu.pascualbravo.ockly.domain.port.out;

import co.edu.pascualbravo.ockly.domain.model.Producto;
import java.util.List;
import java.util.Optional;

public interface ProductoRepositoryPort {
    List<Producto> listar();
    Optional<Producto> buscar(Long id);
    Producto guardar(Producto entrada);
    void eliminar(Long id);
    long contar();
    List<Producto> porCategoria(Long categoriaId);
    long contarPorCategoria(Long categoriaId);
}
