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
import ups.edu.parking.Objetos.Horario;
import ups.edu.parking.Gestion.GestionHorarios;

import java.util.List;

@Path("/horarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class HorarioService {

    @Inject
    private GestionHorarios gestionHorarios;

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
     * Crea un nuevo horario.
     */
    @POST
    @Operation(summary = "Crear un nuevo horario", description = "Registra un nuevo horario en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Horario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class))),
            @APIResponse(responseCode = "400", description = "Error al crear el horario")
    })
    public Response crearHorario(Horario horario) {
        try {
            gestionHorarios.crearHorario(horario);

            return Response.status(Response.Status.CREATED)
                    .entity(horario)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear el horario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Lista todos los horarios registrados.
     */
    @GET
    @Operation(summary = "Listar todos los horarios", description = "Obtiene una lista de todos los horarios registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de horarios",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Horario.class)))
    })
    public Response listarHorarios() {
        try {
            List<Horario> horarios = gestionHorarios.listarHorarios();
            return Response.ok(horarios)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener la lista de horarios: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(@PathParam("id") String id, Horario horario) {
        try {
            Horario hor = gestionHorarios.obtenerHorarioPorId(Long.parseLong(id));
            hor.setId(horario.getId());
            hor.setFechaFin(horario.getFechaFin());
            hor.setFechaInicio(horario.getFechaInicio());
            hor.setNombre(horario.getNombre());

            if (hor == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
            gestionHorarios.actualizarHorario(hor);

            return Response.ok(hor)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Elimina un horario por su ID.
     */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un horario", description = "Elimina un horario registrado en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Horario eliminado exitosamente"),
            @APIResponse(responseCode = "404", description = "Horario no encontrado")
    })
    public Response eliminarHorario(@PathParam("id") Long id) {
        try {
            boolean eliminado = gestionHorarios.eliminarHorario(id);
            if (eliminado) {
                return Response.status(Response.Status.NO_CONTENT)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Horario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar el horario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
}
