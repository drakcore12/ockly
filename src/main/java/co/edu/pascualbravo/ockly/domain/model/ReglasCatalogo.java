package co.edu.pascualbravo.ockly.domain.model;

import co.edu.pascualbravo.ockly.domain.exception.DatosInvalidosException;
import java.math.BigDecimal;

public final class ReglasCatalogo {
    private ReglasCatalogo() {}
    public static void categoria(Categoria categoria) {
        if (categoria == null) throw new DatosInvalidosException("La categoría es obligatoria");
        nombre(categoria.getNombre(), 100);
        descripcion(categoria.getDescripcion());
    }
    public static void producto(Producto producto) {
        if (producto == null) throw new DatosInvalidosException("El producto es obligatorio");
        nombre(producto.getNombre(), 120);
        descripcion(producto.getDescripcion());
        if (producto.getPrecio() == null || producto.getPrecio().compareTo(new BigDecimal("0.01")) < 0)
            throw new DatosInvalidosException("El precio debe ser mayor que cero");
        if (producto.getStock() == null || producto.getStock() < 0)
            throw new DatosInvalidosException("Las existencias no pueden ser negativas");
        if (producto.getCategoria() == null || producto.getCategoria().getId() == null)
            throw new DatosInvalidosException("Selecciona una categoría");
    }
    private static void nombre(String nombre, int maximo) {
        if (nombre == null || nombre.isBlank()) throw new DatosInvalidosException("El nombre es obligatorio");
        if (nombre.length() > maximo) throw new DatosInvalidosException("Nombre demasiado largo");
    }
    private static void descripcion(String descripcion) {
        if (descripcion != null && descripcion.length() > 500)
            throw new DatosInvalidosException("Máximo 500 caracteres en la descripción");
    }
}
