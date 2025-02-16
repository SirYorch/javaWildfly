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


    public List<Reserva> listarReservaUsuario(Usuario usuario) {
        return reservaDAO.listarReservasUsuario(usuario);
    }

    public List<Reserva> listarReservas() {
        return reservaDAO.listarReservas();
    }

    public boolean eliminarReservas(Usuario usuario) {
        Reserva reserva = reservaDAO.buscarPorId(usuario.getReserva().getId());
        if (reserva != null) {
            reservaDAO.eliminarReserva(usuario.getReserva().getId());

            //eliminacion de reserva en el usuario
            usuarioDAO.eliminarReservacion(usuario);


            //cambio de estado de reserva en el Lugar
            lugarDAO.eliminarReservacion(reserva.getLugar());


            return true;
        }
        return false;
    }

    public boolean eliminarReserva(Long id) {
        return false;
    }
}

