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
import ups.edu.parking.Objetos.Tarifa;
import ups.edu.parking.Gestion.GestionTarifas;

import java.util.List;

@Path("/tarifas")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TarifaService {

    @Inject
    private GestionTarifas gestionTarifas;

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
     * Crea una nueva tarifa.
     */
    @POST
    @Operation(summary = "Crear una nueva tarifa", description = "Registra una nueva tarifa en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Tarifa creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tarifa.class))),
            @APIResponse(responseCode = "400", description = "Error al crear la tarifa")
    })
    public Response crearTarifa(Tarifa tarifa) {
        try {
            gestionTarifas.crearTarifa(tarifa);
            return Response.status(Response.Status.CREATED)
                    .entity(tarifa)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear la tarifa: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Obtiene una tarifa por ID.
     */
    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener una tarifa por ID", description = "Recupera una tarifa específica mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Tarifa encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tarifa.class))),
            @APIResponse(responseCode = "404", description = "Tarifa no encontrada")
    })
    public Response obtenerTarifaPorId(@PathParam("id") Long id) {
        try {
            Tarifa tarifa = gestionTarifas.obtenerTarifaPorId(id);
            if (tarifa != null) {
                return Response.ok(tarifa)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Tarifa no encontrada")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al buscar la tarifa: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Lista todas las tarifas registradas.
     */
    @GET
    @Operation(summary = "Listar todas las tarifas", description = "Obtiene una lista de todas las tarifas registradas en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de tarifas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tarifa.class)))
    })
    public Response listarTarifas() {
        try {
            List<Tarifa> tarifas = gestionTarifas.listarTarifas();
            return Response.ok(tarifas)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener la lista de tarifas: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Elimina una tarifa por su ID.
     */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar una tarifa", description = "Elimina una tarifa registrada en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Tarifa eliminada exitosamente"),
            @APIResponse(responseCode = "400", description = "Error al eliminar la tarifa")
    })
    public Response eliminarTarifa(@PathParam("id") Long id) {
        try {
            boolean eliminado = gestionTarifas.eliminarTarifa(id);
            if (eliminado) {
                return Response.status(Response.Status.NO_CONTENT)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Tarifa no encontrada")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar la tarifa: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
}
