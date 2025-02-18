package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Context;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.Gestion.GestionUsuarios;

import java.util.List;

@Path("/usuarios")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UsuarioService {

    @Inject
    private GestionUsuarios gestionUsuarios;

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
     * Crea un nuevo usuario en la base de datos.
     */
    @POST
    public Response crearUsuario(Usuario usuarioCliente) {
        try {
            Usuario existente = gestionUsuarios.obtenerUsuarioPorUid(usuarioCliente.getUid());
            if (existente != null) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("El usuario ya existe")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }

            Usuario nuevoUsuario = new Usuario(
                    usuarioCliente.getUid(),
                    usuarioCliente.getNombre(),
                    usuarioCliente.getTelefono(),
                    usuarioCliente.getDireccion(),
                    usuarioCliente.getCedula(),
                    usuarioCliente.getPlaca(),
                    usuarioCliente.getCorreo(),
                    null,
                    "CLIENTE",
                    null,
                    null
            );

            gestionUsuarios.crearUsuario(nuevoUsuario);
            return Response.status(Response.Status.CREATED)
                    .entity(nuevoUsuario)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear el usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Obtiene todos los usuarios registrados.
     */
    @GET
    public Response obtenerUsuarios() {
        try {
            List<Usuario> usuarios = gestionUsuarios.listarUsuarios();
            System.out.println(usuarios);
            return Response.ok(usuarios)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al obtener la lista de usuarios: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    /**
     * Obtiene un usuario por su UID.
     */
    @GET
    @Path("/{uid}")
    public Response obtenerUsuarioPorUid(@PathParam("uid") String uid) {
        try {
            Usuario usuario = gestionUsuarios.obtenerUsuarioPorUid(uid);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }

            // Convertir el usuario a JSON asegurando que se incluyan los subtipos
            return Response.ok(usuario)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al buscar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    @PUT
    @Path("/{uid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(@PathParam("uid") String uid, Usuario usuario) {
        try {
            Usuario usuarioExistente = gestionUsuarios.obtenerUsuarioPorUid(uid);
            if (usuarioExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {

                usuarioExistente.setNombre(usuario.getNombre());
                usuarioExistente.setTelefono(usuario.getTelefono());
                usuarioExistente.setDireccion(usuario.getDireccion());
                usuarioExistente.setCedula(usuario.getCedula());
                usuarioExistente.setStat(usuario.getStat());
                usuarioExistente.setPlaca(usuario.getPlaca());
                gestionUsuarios.actualizarUsuario(usuarioExistente);
            }
            return Response.ok(usuarioExistente)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    @DELETE
    @Path("/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarUsuario(@PathParam("uid") String uid) {
        try {
            Usuario usuario = gestionUsuarios.obtenerUsuarioPorUid(uid);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }
            gestionUsuarios.eliminarUsuario(uid);

            return Response.ok("Usuario eliminado correctamente")
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .header("Access-Control-Allow-Methods", "DELETE, GET, POST, PUT, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al eliminar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .header("Access-Control-Allow-Methods", "DELETE, GET, POST, PUT, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .build();
        }
    }



}


