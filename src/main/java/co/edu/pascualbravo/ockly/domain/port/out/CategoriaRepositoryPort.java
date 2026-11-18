package co.edu.pascualbravo.ockly.domain.port.out;

import co.edu.pascualbravo.ockly.domain.model.Categoria;
import java.util.List;
import java.util.Optional;

public interface CategoriaRepositoryPort {
    List<Categoria> listar();
    Optional<Categoria> buscar(Long id);
    Categoria guardar(Categoria entrada);
    void eliminar(Long id);
    long contar();
}
