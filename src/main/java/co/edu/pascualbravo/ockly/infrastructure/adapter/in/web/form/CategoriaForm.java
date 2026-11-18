package co.edu.pascualbravo.ockly.infrastructure.adapter.in.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import co.edu.pascualbravo.ockly.domain.model.Categoria;

public class CategoriaForm {
    public static CategoriaForm from(Categoria categoria) {
        CategoriaForm f = new CategoriaForm();
        f.setId(categoria.getId()); f.setNombre(categoria.getNombre()); f.setDescripcion(categoria.getDescripcion());
        return f;
    }
    public Categoria toDomain() {
        Categoria c = new Categoria(); c.setId(id); c.setNombre(nombre); c.setDescripcion(descripcion); return c;
    }

    private Long id;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 100, message = "Máximo 100 caracteres")

    private String nombre;

    @Size(max = 500, message = "Máximo 500 caracteres")

    private String descripcion;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

}
