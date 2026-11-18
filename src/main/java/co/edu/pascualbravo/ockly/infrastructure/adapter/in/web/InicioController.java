package co.edu.pascualbravo.ockly.infrastructure.adapter.in.web;

import co.edu.pascualbravo.ockly.domain.port.in.GestionarCategoriasUseCase;
import co.edu.pascualbravo.ockly.domain.port.in.GestionarProductosUseCase;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {
    private final GestionarCategoriasUseCase categorias;
    private final GestionarProductosUseCase productos;

    public InicioController(GestionarCategoriasUseCase categorias, GestionarProductosUseCase productos) {
        this.categorias = categorias;
        this.productos = productos;
    }

    @GetMapping("/")
    public String inicio(Model model) {
        model.addAttribute("categoriasTotal", categorias.contar());
        model.addAttribute("productosTotal", productos.contar());
        return "inicio";
    }
}
