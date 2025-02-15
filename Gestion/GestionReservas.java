package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.LugarDAO;
import ups.edu.parking.DAO.TicketDAO;
import ups.edu.parking.DAO.UsuarioDAO;
import ups.edu.parking.Objetos.Reserva;
import ups.edu.parking.DAO.ReservaDAO;
import ups.edu.parking.Objetos.Usuario;

import java.util.List;

@ApplicationScoped
public class GestionReservas {

    @Inject
    private ReservaDAO reservaDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private LugarDAO lugarDAO;

    @Inject
    private TicketDAO ticketDAO;


    public void crearReserva(Reserva reserva) {
        //se crea tambien el ticket, con un precio de tarifa reducida, que no hemos colocado aun.
        //se vincula la reserva con el ticket.

        reservaDAO.crearReserva(reserva);
    }


    public List<Reserva> listarReservas() {
        return reservaDAO.listarReservas();
    }

    public boolean eliminarReserva(Long id) {
        Reserva reserva = reservaDAO.buscarPorId(id);
        if (reserva != null) {
            reservaDAO.eliminarReserva(id);

            //eliminacion de reserva en el usuario
            usuarioDAO.eliminarReservacion(reserva.getTicket().getUsuario());


            //cambio de estado de reserva en el Lugar
            lugarDAO.eliminarReservacion(reserva.getLugar());

            //eliminar Ticket
            ticketDAO.eliminarTicket(reserva.getTicket().getId());

            return true;
        }
        return false;
    }
}

