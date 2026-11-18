package co.edu.pascualbravo.ockly.domain.port.in;

import co.edu.pascualbravo.ockly.domain.model.Categoria;
import java.util.List;

public interface GestionarCategoriasUseCase {
    List<Categoria> listar();
    Categoria buscar(Long id);
    Categoria guardar(Categoria entrada);
    void eliminar(Long id);
    long contar();
}
