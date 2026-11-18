package co.edu.pascualbravo.ockly.application.service;

import co.edu.pascualbravo.ockly.domain.model.*;
import co.edu.pascualbravo.ockly.domain.exception.*;
import co.edu.pascualbravo.ockly.domain.port.in.GestionarProductosUseCase;
import co.edu.pascualbravo.ockly.domain.port.out.CategoriaRepositoryPort;
import co.edu.pascualbravo.ockly.domain.port.out.ProductoRepositoryPort;
import java.util.List;

public class GestionarProductosService implements GestionarProductosUseCase {
    private final ProductoRepositoryPort productos;
    private final CategoriaRepositoryPort categorias;
    public GestionarProductosService(ProductoRepositoryPort productos, CategoriaRepositoryPort categorias) {
        this.productos = productos; this.categorias = categorias;
    }
    public List<Producto> listar() { return productos.listar(); }
    public List<Producto> porCategoria(Long id) { return productos.porCategoria(id); }
    public Producto buscar(Long id) {
        return productos.buscar(id).orElseThrow(() -> new RegistroNoEncontradoException("Producto no encontrado"));
    }
    public Producto guardar(Producto entrada) {
        ReglasCatalogo.producto(entrada);
        Categoria categoria = categorias.buscar(entrada.getCategoria().getId())
            .orElseThrow(() -> new DatosInvalidosException("Categoría inválida"));
        if (entrada.getId() != null) buscar(entrada.getId());
        Producto producto = new Producto();
        producto.setId(entrada.getId());
        producto.setNombre(entrada.getNombre().trim());
        producto.setDescripcion(entrada.getDescripcion());
        producto.setPrecio(entrada.getPrecio());
        producto.setStock(entrada.getStock());
        producto.setCategoria(categoria);
        return productos.guardar(producto);
    }
    public void eliminar(Long id) { buscar(id); productos.eliminar(id); }
    public long contar() { return productos.contar(); }
}
