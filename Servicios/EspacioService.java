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
import ups.edu.parking.Gestion.GestionEspacios;
import ups.edu.parking.Objetos.Espacio;

import java.util.List;

@Path("/espacios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EspacioService {

    @Inject
    private GestionEspacios gestionEspacios;

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
     * Obtiene un espacio por ID.
     */
    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener un espacio por ID", description = "Recupera un espacio específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Espacio encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Espacio.class))),
            @APIResponse(responseCode = "404", description = "Espacio no encontrado")
    })
    public Response obtenerEspacioPorId(@PathParam("id") Long id) {
        try {
            Espacio espacio = gestionEspacios.obtenerEspacioPorId(id);
            if (espacio != null) {
                return Response.ok(espacio)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Espacio no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al buscar el espacio: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Lista todos los espacios registrados.
     */
    @GET
    @Operation(summary = "Listar todos los espacios", description = "Obtiene una lista de todos los espacios registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de espacios",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Espacio.class)))
    })
    public Response listarEspacios() {
        try {
            List<Espacio> espacios = gestionEspacios.listarEspacios();
            return Response.ok(espacios)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener la lista de espacios: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Crea un nuevo espacio.
     */
    @POST
    @Operation(summary = "Crear un nuevo espacio", description = "Registra un nuevo espacio en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Espacio creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Espacio.class))),
            @APIResponse(responseCode = "400", description = "Error al crear el espacio")
    })
    public Response crearEspacio(Espacio espacio) {
        try {
            gestionEspacios.crearEspacio(espacio);
            return Response.status(Response.Status.CREATED)
                    .entity(espacio)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear el espacio: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Elimina un espacio por su ID.
     */
    @DELETE
    @Path("/{id}")
    @Operation(summary = "Eliminar un espacio", description = "Elimina un espacio registrado en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Espacio eliminado exitosamente"),
            @APIResponse(responseCode = "404", description = "Espacio no encontrado")
    })
    public Response eliminarEspacio(@PathParam("id") Long id) {
        try {
            boolean eliminado = gestionEspacios.eliminarEspacio(id);
            if (eliminado) {
                return Response.status(Response.Status.NO_CONTENT)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Espacio no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar el espacio: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
}
