package co.edu.pascualbravo.ockly.domain.port.in;

import co.edu.pascualbravo.ockly.domain.model.Producto;
import java.util.List;

public interface GestionarProductosUseCase {
    List<Producto> listar();
    Producto buscar(Long id);
    Producto guardar(Producto entrada);
    void eliminar(Long id);
    long contar();
    List<Producto> porCategoria(Long categoriaId);
}
