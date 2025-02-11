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
import ups.edu.parking.Objetos.Lugar;
import ups.edu.parking.Gestion.GestionLugares;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.Objetos.UsuarioAdmin;
import ups.edu.parking.Objetos.UsuarioCliente;

import java.util.List;

@Path("/lugares")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LugarService {

    @Inject
    private GestionLugares gestionLugares;

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
     * Lista todos los lugares registrados.
     */
    @GET
    @Operation(summary = "Listar todos los lugares", description = "Obtiene una lista de todos los lugares registrados en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Lista de lugares",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Lugar.class)))
    })
    public Response listarLugares() {
        try {
            List<Lugar> lugares = gestionLugares.listarLugares();
            return Response.ok(lugares)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener la lista de lugares: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    @PUT
    @Path("entrar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuarioEntrar(@PathParam("id") String id, Usuario usuario) {
        try {
            Lugar lugarEx = gestionLugares.cambiarEstadoEntrar(usuario,Long.parseLong(id));
            return Response.ok(lugarEx)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
    @PUT
    @Path("reservar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuarioReservar(@PathParam("id") String id, Usuario usuario) {
        try {
            Lugar lugarEx = gestionLugares.cambiarEstadoReservar(usuario,Long.parseLong(id));
            return Response.ok(lugarEx)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
    @PUT
    @Path("arrendar/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuarioArrendar(@PathParam("id") String id, Usuario usuario) {
        try {
            Lugar lugarEx = gestionLugares.cambiarEstadoSalir(usuario,Long.parseLong(id));
            return Response.ok(lugarEx)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
    @PUT
    @Path("salir/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuarioSalir(@PathParam("id") String id, Usuario usuario) {
        try {
            Lugar lugarEx = gestionLugares.cambiarEstadoReservar(usuario,Long.parseLong(id));
            return Response.ok(lugarEx)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }


}
