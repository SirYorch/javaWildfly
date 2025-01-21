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
import ups.edu.parking.Objetos.Reserva;
import ups.edu.parking.Gestion.GestionReservas;
import java.util.List;


@Path("/reservas")
public class ReservaService {

    @Inject
    private GestionReservas gestionReservas;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear una nueva reserva", description = "Registra una nueva reserva en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Reserva creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))),
            @APIResponse(responseCode = "400", description = "Error al crear la reserva")
    })
    public Response crearReserva(Reserva reserva) {
        gestionReservas.crearReserva(reserva);
        return Response.status(Response.Status.CREATED).entity(reserva).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener una reserva por ID", description = "Recupera una reserva específica mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Reserva encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class))),
            @APIResponse(responseCode = "404", description = "Reserva no encontrada")
    })
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
    @Operation(summary = "Listar todas las reservas", description = "Obtiene una lista de todas las reservas registradas en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de reservas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Reserva.class)))
    })
    public Response listarReservas() {
        List<Reserva> reservas = gestionReservas.listarReservas();
        return Response.ok(reservas).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Eliminar una reserva", description = "Elimina una reserva registrada en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Reserva eliminada exitosamente"),
            @APIResponse(responseCode = "400", description = "Error al eliminar la reserva")
    })
    public Response eliminarReserva(@PathParam("id") Long id) {
        gestionReservas.eliminarReserva(id);
        return Response.noContent().build();
    }
}
