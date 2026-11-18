package co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence;

import co.edu.pascualbravo.ockly.domain.model.Categoria;
import co.edu.pascualbravo.ockly.domain.model.Producto;
import org.springframework.stereotype.Component;

@Component
public class CatalogoPersistenceMapper {
    public Categoria toDomain(CategoriaEntity entity) {
        Categoria c = new Categoria();
        c.setId(entity.getId()); c.setNombre(entity.getNombre()); c.setDescripcion(entity.getDescripcion());
        return c;
    }
    public CategoriaEntity toEntity(Categoria categoria) {
        CategoriaEntity c = new CategoriaEntity();
        c.setId(categoria.getId()); c.setNombre(categoria.getNombre()); c.setDescripcion(categoria.getDescripcion());
        return c;
    }
    public Producto toDomain(ProductoEntity entity) {
        Producto p = new Producto();
        p.setId(entity.getId()); p.setNombre(entity.getNombre()); p.setDescripcion(entity.getDescripcion());
        p.setPrecio(entity.getPrecio()); p.setStock(entity.getStock()); p.setCategoria(toDomain(entity.getCategoria()));
        return p;
    }
    public ProductoEntity toEntity(Producto producto) {
        ProductoEntity p = new ProductoEntity();
        p.setId(producto.getId()); p.setNombre(producto.getNombre()); p.setDescripcion(producto.getDescripcion());
        p.setPrecio(producto.getPrecio()); p.setStock(producto.getStock()); p.setCategoria(toEntity(producto.getCategoria()));
        return p;
    }
}
