package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.modelo.Categoria;
import ar.unrn.tp.modelo.Marca;
import ar.unrn.tp.modelo.Producto;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ProductoServiceTest {

    @Test
    public void persistirProducto() {
        Categoria cateIndumentaria = new Categoria("Indumentaria");
        Marca marcaNike = new Marca("Nike");
        inTransactionExecute(
                (em) -> {
                    em.persist(cateIndumentaria);
                    em.persist(marcaNike);
                }
        );

        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA("objectdb:myDbTestFile.tmp;drop");
        productoServiceJPA.crearProducto("123", "Remera", 5000.0, cateIndumentaria.getId(), marcaNike.getId());

        inTransactionExecute(
                (em) -> {
                    Producto producto = em.find(Producto.class, 3L);
                    assertTrue(producto.suCodigoEs("123"));
                    assertTrue(producto.suDescripcionEs("Remera"));
                    assertTrue(producto.suPrecioEs(5000.0));
                    assertTrue(producto.suCategoriaEs(cateIndumentaria));
                    assertTrue(producto.suMarcaEs(marcaNike));
                }
        );
    }

    @Test
    public void modificarProductoPersistido() {
        Categoria cateIndumentaria = new Categoria("Indumentaria");
        Marca marcaNike = new Marca("Nike");
        inTransactionExecute(
                (em) -> {
                    em.persist(cateIndumentaria);
                    em.persist(marcaNike);
                }
        );

        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA("objectdb:myDbTestFile.tmp;drop");
        productoServiceJPA.crearProducto("123", "Remera", 5000.0, cateIndumentaria.getId(), marcaNike.getId());
        productoServiceJPA.modificarProducto(3L, "Remera", "123", 4000.0, marcaNike.getId(), cateIndumentaria.getId());

        inTransactionExecute(
                (em) -> {
                    Producto producto = em.find(Producto.class, 3L);
                    assertTrue(producto.suCodigoEs("123"));
                    assertTrue(producto.suDescripcionEs("Remera"));
                    assertTrue(producto.suPrecioEs(4000.0));
                    assertTrue(producto.suCategoriaEs(cateIndumentaria));
                    assertTrue(producto.suMarcaEs(marcaNike));
                }
        );
    }

    @Test
    public void listarProductosPersistidos() {
        Categoria cateIndumentaria = new Categoria("Indumentaria");
        Marca marcaNike = new Marca("Nike");
        inTransactionExecute(
                (em) -> {
                    em.persist(cateIndumentaria);
                    em.persist(marcaNike);
                }
        );
        ProductoServiceJPA productoServiceJPA = new ProductoServiceJPA("objectdb:myDbTestFile.tmp;drop");
        productoServiceJPA.crearProducto("123", "Remera", 5000.0, cateIndumentaria.getId(), marcaNike.getId());
        productoServiceJPA.crearProducto("456", "Pantalon", 9000.0, cateIndumentaria.getId(), marcaNike.getId());
        List<Producto> productosPersistidos = productoServiceJPA.listarProductos();

        assertTrue(!productosPersistidos.isEmpty());
    }

    public void inTransactionExecute(Consumer<EntityManager> bloqueDeCodigo) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            bloqueDeCodigo.accept(em);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            throw e;
        } finally {
            if (em != null && em.isOpen())
                em.close();
        }
    }
}
