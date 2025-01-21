package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.Gestion.GestionUsuarios;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;


@Path("/usuarios")
public class UsuarioService {

    @Inject
    private GestionUsuarios gestionUsuarios;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Crear un nuevo usuario", description = "Registra un nuevo usuario en el sistema.")
    @APIResponses({
            @APIResponse(responseCode = "201", description = "Usuario creado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @APIResponse(responseCode = "400", description = "Error al crear el usuario")
    })
    public Response crearUsuario(Usuario usuario) {
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener un usuario por ID", description = "Recupera un usuario específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @APIResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public Response obtenerUsuarioPorId(@PathParam("id") Long id) {
        return null;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Actualizar un usuario existente", description = "Modifica los datos de un usuario registrado.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuario actualizado exitosamente",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @APIResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public Response actualizarUsuario(Usuario usuario) {
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Eliminar un usuario", description = "Elimina un usuario registrado en el sistema mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "204", description = "Usuario eliminado exitosamente"),
            @APIResponse(responseCode = "400", description = "Error al eliminar el usuario")
    })
    public Response eliminarUsuario(@PathParam("id") Long id) {
        return null;
    }
}

