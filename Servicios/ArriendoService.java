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
import ups.edu.parking.Gestion.GestionArriendos;
import ups.edu.parking.Objetos.Arriendo;

import java.util.List;

@Path("/arriendos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ArriendoService {

    @Inject
    private GestionArriendos gestionArriendos;

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
     * Crea un nuevo arriendo.
     */
    @POST
    @Path("/{uid}/{lugar_id}")
    @Operation(summary = "Crear un nuevo arriendo", description = "Registra un nuevo arriendo en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Arriendo creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Arriendo.class))),
            @APIResponse(responseCode = "400", description = "Error al crear el arriendo")
    })
    public Response crearArriendo(@PathParam("uid") String uid,@PathParam("lugar_id") String lugar, Arriendo arriendo) {
        try {
            System.out.println(uid);
            gestionArriendos.crearArriendo(uid,Long.parseLong(lugar), arriendo);
            return Response.status(Response.Status.CREATED)
                    .entity(arriendo)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear el arriendo: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Obtiene un arriendo por ID.
     */
    @GET
    @Path("/{id}")
    @Operation(summary = "Obtener un arriendo por ID", description = "Recupera un arriendo específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Arriendo encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Arriendo.class))),
            @APIResponse(responseCode = "404", description = "Arriendo no encontrado")
    })
    public Response obtenerArriendoPorId(@PathParam("id") Long id) {
        try {
            Arriendo arriendo = gestionArriendos.obtenerArriendoPorId(id);
            if (arriendo != null) {
                return Response.ok(arriendo)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Arriendo no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al buscar el arriendo: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Lista todos los arriendos registrados.
     */


    /**
     * Elimina un arriendo por su ID.
     */
    @DELETE
    @Path("/{uid}")
    @Operation(summary = "Eliminar un arriendo", description = "Elimina un arriendo registrado en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Arriendo eliminado exitosamente"),
            @APIResponse(responseCode = "404", description = "Arriendo no encontrado")
    })
    public Response eliminarArriendo(@PathParam("uid") String id) {
        try {
            boolean eliminado = gestionArriendos.eliminarArriendo(id);
            if (eliminado) {
                return Response.status(Response.Status.NO_CONTENT)
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Arriendo no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar el arriendo: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
}
