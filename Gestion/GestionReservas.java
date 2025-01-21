package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.Objetos.Reserva;
import ups.edu.parking.DAO.ReservaDAO;

import java.util.List;

@ApplicationScoped
public class GestionReservas {

    @Inject
    private ReservaDAO reservaDAO;

    public void crearReserva(Reserva reserva) {
        reservaDAO.crearReserva(reserva);
    }

    public Reserva obtenerReservaPorId(Long id) {
        return reservaDAO.buscarPorId(id);
    }

    public List<Reserva> listarReservas() {
        return reservaDAO.listarReservas();
    }

    public void eliminarReserva(Long id) {
        reservaDAO.eliminarReserva(id);
    }
}

