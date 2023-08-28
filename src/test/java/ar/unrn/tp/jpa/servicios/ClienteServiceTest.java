package ar.unrn.tp.jpa.servicios;

import ar.unrn.tp.modelo.Cliente;
import ar.unrn.tp.modelo.Tarjeta;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.function.Consumer;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ClienteServiceTest {
    /*private EntityManagerFactory emf;

    @BeforeEach
    public void setUp() {
        emf = Persistence.createEntityManagerFactory("objectdb:myDbTestFile.tmp;drop");
    }*/

    @Test
    public void persistirCliente() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA("objectdb:myDbTestFile.tmp;drop");
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448077", "dalonso@gmail.com");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.suNombreEs("Daiana"));
                    assertTrue(cliente.suApellidoEs("Alonso"));
                    assertTrue(cliente.suDniEs("42448077"));
                    assertTrue(cliente.suEmailEs("dalonso@gmail.com"));
                }
        );
    }

    @Test
    public void modificarClientePersistido() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA("objectdb:myDbTestFile.tmp;drop");
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448076", "dalonso@gmail.com");
        clienteServiceJPA.modificarCliente(1L, "Dai", "Ramos", "42448078", "dramos@gmail.com");
        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.suNombreEs("Dai"));
                    assertTrue(cliente.suApellidoEs("Ramos"));
                    assertTrue(cliente.suDniEs("42448078"));
                    assertTrue(cliente.suEmailEs("dramos@gmail.com"));
                }
        );
    }

    @Test
    public void agregarTarjetaAClientePersistido() {
        String nombreTarjeta = "VISA";
        String nroTarjeta = "123456789";
        Tarjeta tarjetaVisa = new Tarjeta(nombreTarjeta, Integer.parseInt(nroTarjeta));

        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA("objectdb:myDbTestFile.tmp;drop");
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448070", "dalonso@gmail.com");
        clienteServiceJPA.agregarTarjeta(1L, nroTarjeta, nombreTarjeta);

        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.suNombreEs("Daiana"));
                    assertTrue(cliente.suApellidoEs("Alonso"));
                    assertTrue(cliente.suDniEs("42448070"));
                    assertTrue(cliente.suEmailEs("dalonso@gmail.com"));
                    assertTrue(cliente.miTarjeta(tarjetaVisa));
                }
        );
    }

    //es necesario????
   @Test
    public void listarTarjetasDeClientePersistido() {
        ClienteServiceJPA clienteServiceJPA = new ClienteServiceJPA("objectdb:myDbTestFile.tmp;drop");
        clienteServiceJPA.crearCliente("Daiana", "Alonso", "42448073", "dalonso@gmail.com");
        clienteServiceJPA.agregarTarjeta(1L, "123456789", "VISA");
        List<Tarjeta> tarjetas = clienteServiceJPA.listarTarjetas(1L);

        inTransactionExecute(
                (em) -> {
                    Cliente cliente = em.find(Cliente.class, 1L);
                    assertTrue(cliente.suNombreEs("Daiana"));
                    assertTrue(cliente.suApellidoEs("Alonso"));
                    assertTrue(cliente.suDniEs("42448073"));
                    assertTrue(cliente.suEmailEs("dalonso@gmail.com"));
                    assertTrue(!tarjetas.isEmpty());
                }
        );
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
    /*@AfterEach
    public void tearDown() {
        emf.close();
    }*/

}
