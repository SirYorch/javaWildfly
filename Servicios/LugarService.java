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
import ups.edu.parking.Objetos.Lugar;
import ups.edu.parking.Gestion.GestionLugares;
import java.util.List;

@Path("/lugares")
public class LugarService {

    @Inject
    private GestionLugares gestionLugares;

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener un lugar por ID", description = "Recupera un lugar específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lugar encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Lugar.class))),
            @APIResponse(responseCode = "404", description = "Lugar no encontrado")
    })
    public Response obtenerLugarPorId(@PathParam("id") Long id) {
        Lugar lugar = gestionLugares.obtenerLugarPorId(id);
        if (lugar != null) {
            return Response.ok(lugar).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Lugar no encontrado").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Listar todos los lugares", description = "Obtiene una lista de todos los lugares registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de lugares",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Lugar.class)))
    })
    public Response listarLugares() {
        List<Lugar> lugares = gestionLugares.listarLugares();
        return Response.ok(lugares).build();
    }
}
