package ar.unrn.tp.modelo;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class MarcaPromocion extends Promocion {

    @ManyToOne(cascade = CascadeType.PERSIST)
    private Marca marca;

    public MarcaPromocion(LocalDate fechaInicio, LocalDate fechaFin, Double porcentaje, Marca marca) {
        super(fechaInicio, fechaFin, porcentaje);
        this.marca = marca;
    }

    protected MarcaPromocion() {

    }

    public double aplicarDescuento(Producto producto) {
        if (producto.suMarcaEs(this.marca)) {
            return descuento();
        }
        return 0;
    }

    private double descuento() {
        if (estaEnCurso())
            return super.porcentaje();
        return 0;
    }

    public boolean suMarcaEs(Marca marca) {
        return this.marca.esMarca(marca);
    }
}
