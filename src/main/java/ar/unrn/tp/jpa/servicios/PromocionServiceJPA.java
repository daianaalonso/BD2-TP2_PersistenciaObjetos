package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.api.PromocionService;
import ar.unrn.tp.modelo.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.time.LocalDate;

public class PromocionServiceJPA implements PromocionService {

    private String servicio;

    public PromocionServiceJPA(String servicio) {
        this.servicio = servicio;
    }

    @Override
    public void crearDescuentoSobreTotal(String marcaTarjeta, LocalDate fechaDesde, LocalDate fechaHasta, Double porcentaje) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(this.servicio);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Promocion p = new PagoPromocion(fechaDesde, fechaHasta, porcentaje, new Tarjeta(marcaTarjeta));
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }

    @Override
    public void crearDescuento(String marcaProducto, LocalDate fechaDesde, LocalDate fechaHasta, Double porcentaje) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory(this.servicio);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            MarcaPromocion p = new MarcaPromocion(fechaDesde, fechaHasta, porcentaje, new Marca(marcaProducto));
            em.persist(p);
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw new RuntimeException(e);
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}