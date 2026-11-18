package co.edu.pascualbravo.ockly.application.service;

import co.edu.pascualbravo.ockly.domain.model.Categoria;
import co.edu.pascualbravo.ockly.domain.model.ReglasCatalogo;
import co.edu.pascualbravo.ockly.domain.exception.*;
import co.edu.pascualbravo.ockly.domain.port.in.GestionarCategoriasUseCase;
import co.edu.pascualbravo.ockly.domain.port.out.CategoriaRepositoryPort;
import co.edu.pascualbravo.ockly.domain.port.out.ProductoRepositoryPort;
import java.util.List;

public class GestionarCategoriasService implements GestionarCategoriasUseCase {
    private final CategoriaRepositoryPort categorias;
    private final ProductoRepositoryPort productos;
    public GestionarCategoriasService(CategoriaRepositoryPort categorias, ProductoRepositoryPort productos) {
        this.categorias = categorias; this.productos = productos;
    }
    public List<Categoria> listar() { return categorias.listar(); }
    public Categoria buscar(Long id) {
        return categorias.buscar(id).orElseThrow(() -> new RegistroNoEncontradoException("Categoría no encontrada"));
    }
    public Categoria guardar(Categoria entrada) {
        ReglasCatalogo.categoria(entrada);
        if (entrada.getId() != null) buscar(entrada.getId());
        Categoria categoria = new Categoria();
        categoria.setId(entrada.getId());
        categoria.setNombre(entrada.getNombre().trim());
        categoria.setDescripcion(entrada.getDescripcion());
        return categorias.guardar(categoria);
    }
    public void eliminar(Long id) {
        buscar(id);
        if (productos.contarPorCategoria(id) > 0)
            throw new CategoriaConProductosException("No puedes eliminar una categoría con productos asociados.");
        categorias.eliminar(id);
    }
    public long contar() { return categorias.contar(); }
}
