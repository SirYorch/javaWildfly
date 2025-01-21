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
import ups.edu.parking.Gestion.GestionEspacios;
import ups.edu.parking.Objetos.Espacio;


@Path("/espacios")
public class EspacioService {

    @Inject
    private GestionEspacios gestionEspacios;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar un espacio existente", description = "Modifica los datos de un espacio registrado.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Espacio actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Espacio.class))),
            @APIResponse(responseCode = "404", description = "Espacio no encontrado")
    })
    public Response actualizarEspacio(Espacio espacio) {
        Espacio espacioActualizado = gestionEspacios.actualizarEspacio(espacio);
        return Response.ok(espacioActualizado).build();
    }
}


