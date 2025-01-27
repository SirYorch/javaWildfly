package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Objetos.Lugar;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.Gestion.GestionUsuarios;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import ups.edu.parking.Objetos.UsuarioCliente;

import java.util.List;


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
    public Response crearUsuario(UsuarioCliente usuarioCliente) {
        try {
            gestionUsuarios.crearUsuario(usuarioCliente); // Llama al método de gestión para guardar el usuario
            return Response.status(Response.Status.CREATED).entity(usuarioCliente).build(); // Devuelve el usuario creado
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al crear el usuario cliente: " + e.getMessage()).build();
        }
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
        Usuario usuario = gestionUsuarios.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("usuario no encontrado").build();
        }
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    @Operation(summary = "Obtener un usuario por ID", description = "Recupera un usuario específico mediante su ID.")
    @APIResponses({
            @APIResponse(responseCode = "200", description = "Usuario encontrado",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = Usuario.class))),
            @APIResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    public Response obtenerUsuarios() {
        List<Usuario> usuario = gestionUsuarios.listarUsuarios();
        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("usuario no encontrado").build();
        }
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
        try {
            Usuario usuarioExistente = gestionUsuarios.obtenerUsuarioPorId(usuario.getId());
            if (usuarioExistente != null) {
                gestionUsuarios.actualizarUsuario(usuario); // Llama al método de gestión para actualizar
                return Response.ok(usuario).build(); // Devuelve el usuario actualizado
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al actualizar el usuario: " + e.getMessage()).build();
        }
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
        try {
            Usuario usuarioExistente = gestionUsuarios.obtenerUsuarioPorId(id);
            if (usuarioExistente != null) {
                gestionUsuarios.eliminarUsuario(id); // Llama al método de gestión para eliminar
                return Response.noContent().build(); // Devuelve 204 No Content
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("Usuario no encontrado").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Error al eliminar el usuario: " + e.getMessage()).build();
        }
    }
}

