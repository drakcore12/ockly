package co.edu.pascualbravo.ockly;

import co.edu.pascualbravo.ockly.application.service.*;
import co.edu.pascualbravo.ockly.domain.exception.*;
import co.edu.pascualbravo.ockly.domain.model.*;
import co.edu.pascualbravo.ockly.domain.port.out.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

// Estos casos se ejecutan sin Spring, H2 ni MySQL.
@ExtendWith(MockitoExtension.class)
class GestionarCatalogoServiceTest {
    @Mock CategoriaRepositoryPort categorias;
    @Mock ProductoRepositoryPort productos;
    GestionarCategoriasService servicioCategorias;
    GestionarProductosService servicioProductos;
    Categoria categoria;

    @BeforeEach void preparar() {
        servicioCategorias = new GestionarCategoriasService(categorias, productos);
        servicioProductos = new GestionarProductosService(productos, categorias);
        categoria = new Categoria(); categoria.setId(7L); categoria.setNombre("Cuadernos");
    }
    Producto producto() {
        Producto p = new Producto(); p.setNombre("Cuaderno"); p.setPrecio(new BigDecimal("12000"));
        p.setStock(15); p.setCategoria(categoria); return p;
    }
    @Test void listaDesdeElPuerto() {
        when(categorias.listar()).thenReturn(List.of(categoria));
        assertThat(servicioCategorias.listar()).containsExactly(categoria);
    }
    @Test void guardaCategoriaConNombreLimpio() {
        Categoria nueva = new Categoria(); nueva.setNombre("  Colores  ");
        when(categorias.guardar(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(servicioCategorias.guardar(nueva).getNombre()).isEqualTo("Colores");
    }
    @ParameterizedTest @NullAndEmptySource @ValueSource(strings={" ","\t"})
    void rechazaNombreVacioSinPersistir(String nombre) {
        categoria.setNombre(nombre);
        assertThatThrownBy(() -> servicioCategorias.guardar(categoria)).isInstanceOf(DatosInvalidosException.class);
        verifyNoInteractions(categorias, productos);
    }
    @Test void categoriaInexistenteTieneExcepcionDelDominio() {
        when(categorias.buscar(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicioCategorias.buscar(99L)).isInstanceOf(RegistroNoEncontradoException.class);
    }
    @Test void impideBorrarCategoriaConProductos() {
        when(categorias.buscar(7L)).thenReturn(Optional.of(categoria));
        when(productos.contarPorCategoria(7L)).thenReturn(1L);
        assertThatThrownBy(() -> servicioCategorias.eliminar(7L)).isInstanceOf(CategoriaConProductosException.class);
        verify(categorias, never()).eliminar(any());
    }
    @Test void borraCategoriaVacia() {
        when(categorias.buscar(7L)).thenReturn(Optional.of(categoria));
        servicioCategorias.eliminar(7L);
        verify(categorias).eliminar(7L);
    }
    @Test void conservaRelacionAlGuardarProducto() {
        when(categorias.buscar(7L)).thenReturn(Optional.of(categoria));
        when(productos.guardar(any())).thenAnswer(i -> i.getArgument(0));
        assertThat(servicioProductos.guardar(producto()).getCategoria()).isSameAs(categoria);
    }
    @ParameterizedTest @ValueSource(strings={"-1","0"})
    void rechazaPrecioInvalidoSinAccederALaBase(String precio) {
        Producto p=producto(); p.setPrecio(new BigDecimal(precio));
        assertThatThrownBy(() -> servicioProductos.guardar(p)).isInstanceOf(DatosInvalidosException.class);
        verifyNoInteractions(categorias, productos);
    }
    @Test void rechazaStockNegativo() {
        Producto p=producto(); p.setStock(-1);
        assertThatThrownBy(() -> servicioProductos.guardar(p)).isInstanceOf(DatosInvalidosException.class);
        verifyNoInteractions(categorias, productos);
    }
    @Test void rechazaCategoriaNoRegistrada() {
        when(categorias.buscar(7L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> servicioProductos.guardar(producto())).isInstanceOf(DatosInvalidosException.class);
        verify(productos, never()).guardar(any());
    }
    @Test void noCreaUnProductoAlIntentarEditarUnoInexistente() {
        when(categorias.buscar(7L)).thenReturn(Optional.of(categoria));
        when(productos.buscar(99L)).thenReturn(Optional.empty());
        Producto p=producto(); p.setId(99L);
        assertThatThrownBy(() -> servicioProductos.guardar(p)).isInstanceOf(RegistroNoEncontradoException.class);
        verify(productos, never()).guardar(any());
    }
    @Test void listaProductosDeUnaCategoria() {
        Producto p=producto(); when(productos.porCategoria(7L)).thenReturn(List.of(p));
        assertThat(servicioProductos.porCategoria(7L)).containsExactly(p);
    }
    @Test void eliminaProductoExistente() {
        when(productos.buscar(2L)).thenReturn(Optional.of(producto()));
        servicioProductos.eliminar(2L); verify(productos).eliminar(2L);
    }
}
