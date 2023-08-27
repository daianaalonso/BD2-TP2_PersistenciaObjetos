package ar.unrn.tp.modelo;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Categoria {
    @Id
    @GeneratedValue
    private Long id;
    private String nombre;

    public Categoria(String nombre) {
        this.nombre = nombre;
    }

    protected Categoria() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean esCategoria(Categoria categoria) {
        return this.nombre.equals(categoria.nombre);
    }

    private String nombre() {
        return this.nombre;
    }
}
