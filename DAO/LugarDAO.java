package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Arriendo;
import ups.edu.parking.Objetos.Lugar;
import ups.edu.parking.Objetos.Tarifa;

import java.util.List;

@ApplicationScoped
public class LugarDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;


    @Transactional

    public Lugar buscarPorId(Long id) {
        return em.find(Lugar.class, id);
    }

    public List<Lugar> listarLugares() {
        return em.createQuery("SELECT e FROM Lugar e order by e.id", Lugar.class).getResultList();
    }

    @Transactional
    public Lugar actualizar(Lugar lugar) {
        return em.merge(lugar);
    }

    @Transactional
    public void crearLugares(int filas, int columnas) {
        char letraInicial = 'A'; // Letra inicial para las filas (A, B, C, ...)
        Long contador = 0L;
        for (int i = 0; i < filas; i++) {
            char filaActual = (char) (letraInicial + i); // Calcula la letra de la fila actual

            for (int j = 1; j <= columnas; j++) {
                contador++;
                String posicion = filaActual + String.valueOf(j); // Crea la posición (A1, A2, ..., B1, B2, ...)
                Lugar lugar = new Lugar();
                lugar.setId(contador);
                lugar.setEstado("disponible"); // Estado por defecto
                lugar.setPosicion(posicion); // Asigna la posición

                em.persist(lugar); // Persiste el lugar en la base de datos
            }
        }
    }

    @Transactional
    public boolean eliminarLugares(int filas, int columnas) {
        System.out.println("Se estan creando los lugares");
        for(Lugar lugar : listarLugares()) {
            em.remove(lugar);
        }
        if(listarLugares().isEmpty()) {
            crearLugares(filas, columnas);
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public Lugar actualizarEntrar(Long id){
        Lugar l = em.find(Lugar.class, id);
        l.setEstado("ocupado");
        return em.merge(l);
    };


    @Transactional
    public Lugar actualizarSalir(Long id){
        Lugar l = em.find(Lugar.class, id);
        l.setEstado("disponible");
        return em.merge(l);
    };

    @Transactional
    public Lugar actualizarReservar(Long id){
        Lugar l = em.find(Lugar.class, id);
        l.setEstado("reservado");
        return em.merge(l);
    };

    @Transactional
    public Lugar actualizarArrendar(Long id){
        Lugar l = em.find(Lugar.class, id);
        l.setEstado("arrendado");
        return em.merge(l);
    };

    @Transactional
    public void eliminarReservacion(Lugar lugar) {
        lugar.setEstado("disponible");
        em.merge(lugar);
    }
}

