package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Gestion.GestionReservas;
import ups.edu.parking.Objetos.Reserva;

import java.util.List;

@Path("/reservas")
public class ReservaService {

    @Inject
    private GestionReservas gestionReservas;

    /**
     * Crea una nueva reserva en el sistema.
     * @param reserva Objeto Reserva a crear.
     * @return Respuesta HTTP con el estado de la operación.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearReserva(Reserva reserva) {
        gestionReservas.crearReserva(reserva);
        return Response.status(Response.Status.CREATED).entity(reserva).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerReservaPorId(@PathParam("id") Long id) {
        Reserva reserva = gestionReservas.obtenerReservaPorId(id);
        if (reserva != null) {
            return Response.ok(reserva).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Reserva no encontrada").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarReservas() {
        List<Reserva> reservas = gestionReservas.listarReservas();
        return Response.ok(reservas).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarReserva(@PathParam("id") Long id) {
        gestionReservas.eliminarReserva(id);
        return Response.noContent().build();
    }
}
