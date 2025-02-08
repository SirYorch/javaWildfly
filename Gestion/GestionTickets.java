package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.Objetos.Ticket;
import ups.edu.parking.DAO.TicketDAO;

import java.util.List;

@ApplicationScoped
public class GestionTickets {

    @Inject
    private TicketDAO ticketDAO;

    public void crearTicket(Ticket ticket) {
        ticketDAO.crearTicket(ticket);
    }

    public Ticket obtenerTicketPorId(Long id) {
        return ticketDAO.buscarPorId(id);
    }

    public List<Ticket> listarTickets() {
        return ticketDAO.listarTickets();
    }

    public Ticket actualizarTicket(Ticket ticket) {
        return ticketDAO.actualizarTicket(ticket);
    }

    public void eliminarTicket(Long id) {
        ticketDAO.eliminarTicket(id);
    }

    public List<Ticket> obtenerTicketsPorUsuario(String uid) {
        return ticketDAO.obtenerTicketsPorUsuario(uid);
    }
}
