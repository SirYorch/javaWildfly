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

import ups.edu.parking.Objetos.Arriendo;
import ups.edu.parking.Objetos.Tarifa;
import ups.edu.parking.Gestion.GestionTarifas;

import java.util.List;

@Path("/tarifas")
public class TarifaService {

    @Inject
    private GestionTarifas gestionTarifas;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear una nueva tarifa", description = "Registra una nueva tarifa en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Tarifa creada exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tarifa.class))),
            @APIResponse(responseCode = "400", description = "Error al crear la tarifa")
    })
    public Response crearTarifa(Tarifa tarifa) {
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener una tarifa por ID", description = "Recupera una tarifa específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Tarifa encontrada",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tarifa.class))),
            @APIResponse(responseCode = "404", description = "Tarifa no encontrada")
    })
    public Response obtenerTarifaPorId(@PathParam("id") Long id) {
        Tarifa tarifa = gestionTarifas.obtenerTarifaPorId(id);
        if (tarifa != null) {
            return Response.ok(tarifa).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Tarifa no encontrado").build();
        }
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar todos las tarifas", description = "Obtiene una lista de todas las tarifas registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de tarifas",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Tarifa.class)))
    })
    public Response listarTarifas() {
        List<Tarifa> tarifas = gestionTarifas.listarTarifas();
        return Response.ok(tarifas).build();
    }
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Eliminar una tarifa", description = "Elimina una tarifa registrado en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Tarifa eliminada exitosamente"),
            @APIResponse(responseCode = "400", description = "Error al eliminar la tarifa")
    })
    public Response eliminarTarifa(@PathParam("id") Long id) {
        return null;
    }
}
