package ups.edu.parking.Servicios;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Objetos.Ticket;
import ups.edu.parking.Gestion.GestionTickets;
import java.util.List;

@Path("/tickets")
public class TicketService {

    @Inject
    private GestionTickets gestionTickets;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear un nuevo ticket", description = "Registra un nuevo ticket en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Ticket creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
            @APIResponse(responseCode = "400", description = "Error al crear el ticket")
    })
    public Response crearTicket(Ticket ticket) {
        gestionTickets.crearTicket(ticket);
        return Response.status(Response.Status.CREATED).entity(ticket).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener un ticket por ID", description = "Recupera un ticket específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Ticket encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
            @APIResponse(responseCode = "404", description = "Ticket no encontrado")
    })
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
    @Operation(summary = "Listar todos los tickets", description = "Obtiene una lista de todos los tickets registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de tickets",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class)))
    })
    public Response listarTickets() {
        List<Ticket> tickets = gestionTickets.listarTickets();
        return Response.ok(tickets).build();
    }
}
