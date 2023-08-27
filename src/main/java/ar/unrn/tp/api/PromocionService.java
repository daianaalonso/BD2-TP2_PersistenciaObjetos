package ar.unrn.tp.api;

import java.time.LocalDate;

public interface PromocionService {
    // validar que las fechas no se superpongan
    void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde,
                                  LocalDate fechaHasta, Double porcentaje);

    // validar que las fechas no se superpongan
    void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate
            fechaHasta, Double porcentaje);
}
