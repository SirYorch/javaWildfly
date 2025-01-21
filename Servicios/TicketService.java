package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Gestion.GestionTickets;
import ups.edu.parking.Objetos.Ticket;

import java.util.List;

@Path("/tickets")
public class TicketService {

    @Inject
    private GestionTickets gestionTickets;

    /**
     * Crea un nuevo ticket en el sistema.
     * @param ticket Objeto Ticket a crear.
     * @return Respuesta HTTP con el estado de la operación.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearTicket(Ticket ticket) {
        gestionTickets.crearTicket(ticket);
        return Response.status(Response.Status.CREATED).entity(ticket).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerTicketPorId(@PathParam("id") Long id) {
        Ticket ticket = gestionTickets.obtenerTicketPorId(id);
        if (ticket != null) {
            return Response.ok(ticket).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Ticket no encontrado").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarTickets() {
        List<Ticket> tickets = gestionTickets.listarTickets();
        return Response.ok(tickets).build();
    }

}

