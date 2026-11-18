package co.edu.pascualbravo.ockly;

import co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence.CategoriaEntity;
import co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence.ProductoEntity;
import co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence.CategoriaJpaRepository;
import co.edu.pascualbravo.ockly.infrastructure.adapter.out.persistence.ProductoJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class OcklyHexagonalIntegrationTest {
    @Autowired private MockMvc mvc;
    @Autowired private CategoriaJpaRepository categorias;
    @Autowired private ProductoJpaRepository productos;

    @BeforeEach
    void limpiar() {
        productos.deleteAll();
        categorias.deleteAll();
    }

    @Test
    void crearConsultarActualizarYEliminarAmbasEntidades() throws Exception {
        mvc.perform(post("/categorias/guardar")
                .param("nombre", "Cuadernos")
                .param("descripcion", "Útiles escolares"))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/categorias"));
        assertThat(categorias.count()).isEqualTo(1);
        CategoriaEntity categoria = categorias.findAll().get(0);

        mvc.perform(get("/categorias/{id}", categoria.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("categorias/detalle"));

        mvc.perform(post("/productos/guardar")
                .param("nombre", "Cuaderno cuadriculado")
                .param("descripcion", "100 hojas")
                .param("precio", "12000.00")
                .param("stock", "15")
                .param("categoriaId", categoria.getId().toString()))
            .andExpect(status().is3xxRedirection())
            .andExpect(redirectedUrl("/productos"));

        ProductoEntity producto = productos.findAll().get(0);
        assertThat(producto.getCategoria().getId()).isEqualTo(categoria.getId());
        mvc.perform(get("/productos/{id}", producto.getId()))
            .andExpect(status().isOk())
            .andExpect(view().name("productos/detalle"));

        mvc.perform(post("/productos/guardar")
                .param("id", producto.getId().toString())
                .param("nombre", "Cuaderno actualizado")
                .param("descripcion", "100 hojas")
                .param("precio", "14000.00")
                .param("stock", "8")
                .param("categoriaId", categoria.getId().toString()))
            .andExpect(status().is3xxRedirection());
        assertThat(productos.findById(producto.getId()).orElseThrow().getStock()).isEqualTo(8);

        mvc.perform(post("/categorias/guardar")
                .param("id", categoria.getId().toString())
                .param("nombre", "Papelería")
                .param("descripcion", "Nueva descripción"))
            .andExpect(status().is3xxRedirection());
        assertThat(categorias.findById(categoria.getId()).orElseThrow().getNombre()).isEqualTo("Papelería");

        mvc.perform(post("/productos/{id}/eliminar", producto.getId()))
            .andExpect(status().is3xxRedirection());
        assertThat(productos.count()).isZero();

        mvc.perform(post("/categorias/{id}/eliminar", categoria.getId()))
            .andExpect(status().is3xxRedirection());
        assertThat(categorias.count()).isZero();
    }

    @Test
    void impideEliminarCategoriaConProductos() throws Exception {
        CategoriaEntity categoria = new CategoriaEntity();
        categoria.setNombre("Bolígrafos");
        categoria = categorias.save(categoria);
        ProductoEntity producto = new ProductoEntity();
        producto.setNombre("Bolígrafo azul");
        producto.setPrecio(new BigDecimal("2500"));
        producto.setStock(10);
        producto.setCategoria(categoria);
        productos.save(producto);

        mvc.perform(post("/categorias/{id}/eliminar", categoria.getId()))
            .andExpect(status().is3xxRedirection())
            .andExpect(flash().attributeExists("error"));
        assertThat(categorias.count()).isEqualTo(1);
    }

    @Test
    void validaInformacionIncompletaYPrecioInvalido() throws Exception {
        mvc.perform(post("/categorias/guardar").param("nombre", "  "))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasFieldErrors("categoria", "nombre"));

        mvc.perform(post("/productos/guardar")
                .param("nombre", "ProductoEntity")
                .param("precio", "-1")
                .param("stock", "-5"))
            .andExpect(status().isOk())
            .andExpect(model().attributeHasFieldErrors("producto", "precio", "stock", "categoriaId"));
        assertThat(productos.count()).isZero();
    }

    @Test
    void informaRegistrosInexistentesYCategoriaInvalida() throws Exception {
        mvc.perform(get("/categorias/999999")).andExpect(status().isNotFound());
        mvc.perform(get("/productos/999999")).andExpect(status().isNotFound());
        mvc.perform(post("/productos/guardar")
                .param("nombre", "Producto sin categoría real")
                .param("precio", "1000").param("stock", "1").param("categoriaId", "999999"))
            .andExpect(status().isBadRequest());
        assertThat(productos.count()).isZero();
    }

    @Test
    void renderizaListadosFormulariosYRelacionSinSesionJpaAbierta() throws Exception {
        CategoriaEntity categoria = new CategoriaEntity(); categoria.setNombre("Papeles");
        categoria = categorias.save(categoria);
        ProductoEntity producto = new ProductoEntity(); producto.setNombre("Resma");
        producto.setPrecio(new BigDecimal("15000")); producto.setStock(2); producto.setCategoria(categoria);
        producto = productos.save(producto);
        mvc.perform(get("/")).andExpect(status().isOk()).andExpect(model().attribute("productosTotal", 1L));
        mvc.perform(get("/categorias")).andExpect(status().isOk());
        mvc.perform(get("/productos")).andExpect(status().isOk()).andExpect(content().string(org.hamcrest.Matchers.containsString("Papeles")));
        mvc.perform(get("/categorias/nuevo")).andExpect(status().isOk());
        mvc.perform(get("/productos/nuevo")).andExpect(status().isOk());
        mvc.perform(get("/categorias/{id}/editar", categoria.getId())).andExpect(status().isOk());
        mvc.perform(get("/productos/{id}/editar", producto.getId())).andExpect(status().isOk());
        mvc.perform(get("/categorias/{id}", categoria.getId())).andExpect(status().isOk())
            .andExpect(content().string(org.hamcrest.Matchers.containsString("Resma")));
    }
}
