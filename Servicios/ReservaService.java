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
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Context;
import ups.edu.parking.Objetos.Reserva;
import ups.edu.parking.Gestion.GestionReservas;

import java.util.List;

@Path("/reservas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ReservaService {

    @Inject
    private GestionReservas gestionReservas;

    /**
     * Habilita CORS globalmente para todas las solicitudes a este servicio.
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
     * Crea una nueva reserva.
     */
    @POST
    @Operation(summary = "Crear una nueva reserva", description = "Registra una nueva reserva en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Reserva creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))),
            @APIResponse(responseCode = "400", description = "Error al crear la reserva")
    })
    public Response crearReserva(Reserva reserva) {
        try {
            gestionReservas.crearReserva(reserva);
            return Response.status(Response.Status.CREATED)
                    .entity(reserva)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear la reserva: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }



    /**
     * Lista todas las reservas registradas.
     */
    @GET
    @Operation(summary = "Listar todas las reservas", description = "Obtiene una lista de todas las reservas registradas en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de reservas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class)))
    })
    public Response listarReservas() {
        try {
            List<Reserva> reservas = gestionReservas.listarReservas();
            return Response.ok(reservas)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener la lista de reservas: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Elimina una reserva por su ID.
     */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva registrada en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
            @APIResponse(responseCode = "400", description = "Error al eliminar la reserva")
    })
    public Response eliminarReserva(@PathParam("id") Long id) {
        try {
            boolean eliminado = gestionReservas.eliminarReserva(id);
            if (eliminado) {
                return Response.status(Response.Status.NO_CONTENT)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Reserva no encontrada")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar la reserva: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
}
