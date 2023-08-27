package ar.unrn.tp.modelo;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private LocalDateTime fecha;
    @ManyToOne(cascade = CascadeType.PERSIST)
    private Cliente cliente;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Tarjeta tarjeta;
    @OneToMany(cascade = CascadeType.PERSIST)
    private List<Producto> productosVendidos;
    private Double montoTotal;

    public Venta(LocalDateTime fecha, Cliente cliente, Tarjeta tarjeta, List<Producto> productosVendidos, Double montoTotal) {
        this.productosVendidos = productosVendidos;
        this.fecha = fecha;
        this.cliente = cliente;
        this.tarjeta = tarjeta;
        this.montoTotal = montoTotal;
    }

    public Venta() {
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public boolean montoEs(Double montoTotal){
        return this.montoTotal.equals(montoTotal);
    }
}
