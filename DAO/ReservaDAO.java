package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Reserva;
import ups.edu.parking.Objetos.Ticket;
import ups.edu.parking.Objetos.Usuario;

import java.util.List;

@ApplicationScoped
public class ReservaDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    @Transactional
    public void crearReserva(Reserva reserva) {
        em.persist(reserva);
    }

    public Reserva buscarPorId(Long id) {
        return em.find(Reserva.class, id);
    }

    public List<Reserva> listarReservas() {
        return em.createQuery("SELECT r FROM Reserva r", Reserva.class).getResultList();
    }

    @Transactional
    public void eliminarReserva(Long id) {
        Reserva reserva = em.find(Reserva.class, id);
        if (reserva != null) {
            em.remove(reserva);
        }
    }

    @Transactional
    public void eliminarReservas() {
        List<Reserva> reservas = listarReservas();
        for (Reserva reserva : reservas) {
            em.remove(reserva);
        }
    }

    public List<Reserva> listarReservasUsuario(Usuario usuario) {

        return em.createQuery("SELECT r FROM Reserva r WHERE r.id = :Reserva_id", Reserva.class)
                .setParameter("Reserva_id", usuario.getReserva().getId())
                .getResultList();

    }

    public void eliminarReservasUsuario(Usuario usuario) {
        List<Reserva> reservas = em.createQuery("SELECT r FROM Reserva r WHERE r.id = :Reserva_id", Reserva.class)
                .setParameter("Reserva_id", usuario.getReserva().getId())
                .getResultList();
        for (Reserva reserva : reservas) {
            em.remove(reserva);
        }
    }
}

