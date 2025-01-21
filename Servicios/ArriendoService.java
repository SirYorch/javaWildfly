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
import ups.edu.parking.Gestion.GestionArriendos;
import java.util.List;

@Path("/arriendos")
public class ArriendoService {

    @Inject
    private GestionArriendos gestionArriendos;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear un nuevo arriendo", description = "Registra un nuevo arriendo en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Arriendo creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Arriendo.class))),
            @APIResponse(responseCode = "400", description = "Error al crear el arriendo")
    })
    public Response crearArriendo(Arriendo arriendo) {
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener un arriendo por ID", description = "Recupera un arriendo específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Arriendo encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Arriendo.class))),
            @APIResponse(responseCode = "404", description = "Arriendo no encontrado")
    })
    public Response obtenerArriendoPorId(@PathParam("id") Long id) {
        Arriendo arriendo = gestionArriendos.obtenerArriendoPorId(id);
        if (arriendo != null) {
            return Response.ok(arriendo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Arriendo no encontrado").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar todos los arriendos", description = "Obtiene una lista de todos los arriendos registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de arriendos",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Arriendo.class)))
    })
    public Response listarArriendos() {
        List<Arriendo> arriendos = gestionArriendos.listarArriendos();
        return Response.ok(arriendos).build();
    }



    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Eliminar un arriendo", description = "Elimina un arriendo registrado en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Arriendo eliminado exitosamente"),
            @APIResponse(responseCode = "400", description = "Error al eliminar el arriendo")
    })
    public Response eliminarArriendo(@PathParam("id") Long id) {
        return null;
    }
}

