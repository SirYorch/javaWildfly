package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Ticket;

import java.util.List;

@ApplicationScoped
public class TicketDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    @Transactional
    public void crearTicket(Ticket ticket) {
        em.persist(ticket);
    }

    public Ticket buscarPorId(Long id) {
        return em.find(Ticket.class, id);
    }

    public List<Ticket> listarTickets() {
        return em.createQuery("SELECT t FROM Ticket t", Ticket.class).getResultList();
    }

    @Transactional
    public Ticket actualizarTicket(Ticket ticket) {
        return em.merge(ticket);
    }

    @Transactional
    public void eliminarTicket(Long id) {
        Ticket ticket = em.find(Ticket.class, id);
        if (ticket != null) {
            em.remove(ticket);
        }
    }
    public List<Ticket> obtenerTicketsPorUsuario(String uid) {
        return em.createQuery(
                        "SELECT t FROM Ticket t WHERE t.usuario.uid = :uid", Ticket.class)
                .setParameter("uid", uid)
                .getResultList();
    }
}

