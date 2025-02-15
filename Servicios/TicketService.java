package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Context;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import ups.edu.parking.Objetos.Ticket;
import ups.edu.parking.Gestion.GestionTickets;

import java.util.List;

@Path("/tickets")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TicketService {

    @Inject
    private GestionTickets gestionTickets;

    /**
     * Habilita CORS para todas las solicitudes a este servicio.
     */
    @OPTIONS
    @Path("{path:.*}")
    public Response options(@Context HttpHeaders headers) {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://localhost:4200")
                .header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE, OPTIONS")
                .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                .header("Access-Control-Allow-Credentials", "true")
                .build();
    }


    /**
     * Lista todos los tickets registrados.
     */
    @GET
    @Operation(summary = "Listar todos los tickets", description = "Obtiene una lista de todos los tickets registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de tickets",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class)))
    })
    public Response listarTickets() {
        try {
            List<Ticket> tickets = gestionTickets.listarTickets();
            return Response.ok(tickets)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener la lista de tickets: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
    @GET
    @Path("/{uid}")
    @Operation(summary = "Obtener tickets por usuario", description = "Recupera todos los tickets asociados a un usuario mediante su UID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Tickets encontrados",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Ticket.class))),
            @APIResponse(responseCode = "404", description = "No se encontraron tickets para este usuario")
    })
    public Response obtenerTicketsPorUsuario(@PathParam("uid") String uid) {
        try {
            List<Ticket> tickets = gestionTickets.obtenerTicketsPorUsuario(uid);
            if (!tickets.isEmpty()) {
                return Response.ok(tickets)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("No se encontraron tickets para este usuario")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener los tickets del usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

}
