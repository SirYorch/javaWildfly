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
    @PUT
    @Operation(summary = "Actuaalizar valores de espacios", description = "actuaaliza valor de filas o columnas. y rehace lugares.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Espacio encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Espacio.class))),
            @APIResponse(responseCode = "404", description = "Espacio no encontrado")
    })
    public Response actualizarDatos(Espacio espacio) {
        try {
            Espacio espacioex = gestionEspacios.actualizarDatos(espacio);
            if (espacioex != null) {
                return Response.ok(espacioex)
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

    @GET
    @Operation(summary = "Obtener un espacio por ID", description = "Recupera un espacio específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Espacio encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Espacio.class))),
            @APIResponse(responseCode = "404", description = "Espacio no encontrado")
    })
    public Response obtenerEspacioPorId() {
        try {
            Espacio espacio = gestionEspacios.listarEspacios();
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


}
