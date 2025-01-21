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
import ups.edu.parking.Objetos.Horario;
import ups.edu.parking.Gestion.GestionHorarios;

@Path("/horarios")
public class HorarioService {

    @Inject
    private GestionHorarios gestionHorarios;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear un nuevo horario", description = "Registra un nuevo horario en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Horario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class))),
            @APIResponse(responseCode = "400", description = "Error al crear el horario")
    })
    public Response crearHorario(Horario horario) {
        return null;
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar todos los horarios", description = "Obtiene una lista de todos los horarios registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de horarios",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class)))
    })
    public Response listarHorarios() {
        return null;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar un horario existente", description = "Modifica los datos de un horario registrado.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Horario actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class))),
            @APIResponse(responseCode = "404", description = "Horario no encontrado")
    })
    public Response actualizarHorario(Horario horario) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Eliminar un horario", description = "Elimina un horario registrado en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Horario eliminado exitosamente"),
            @APIResponse(responseCode = "400", description = "Error al eliminar el horario")
    })
    public Response eliminarHorario(@PathParam("id") Long id) {
        return null;
    }
}