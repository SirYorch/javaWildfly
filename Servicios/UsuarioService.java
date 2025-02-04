package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.Context;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.Objetos.UsuarioAdmin;
import ups.edu.parking.Objetos.UsuarioCliente;
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
    public Response crearUsuario(UsuarioCliente usuarioCliente) {
        try {
            Usuario existente = gestionUsuarios.obtenerUsuarioPorUid(usuarioCliente.getUid());
            if (existente != null) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("El usuario ya existe")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }

            UsuarioCliente nuevoUsuario = new UsuarioCliente(
                    usuarioCliente.getUid(),
                    usuarioCliente.getNombre(),
                    usuarioCliente.getTelefono(),
                    usuarioCliente.getDireccion(),
                    usuarioCliente.getCedula(),
                    usuarioCliente.getPlaca()
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


    /**
     * Cambia el tipo de usuario entre ADMIN y CLIENTE.
     */
    @PUT
    @Path("/cambiarTipo/{uid}/{tipo_usuario}")
    public Response cambiarTipoUsuario(@PathParam("uid") String uid, @PathParam("tipo_usuario") String nuevoTipo) {
        try {
            Usuario usuario = gestionUsuarios.obtenerUsuarioPorUid(uid);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }

            if (nuevoTipo.equalsIgnoreCase("ADMIN")) {
                usuario = new UsuarioAdmin(usuario.getUid(), usuario.getNombre(), usuario.getTelefono(), usuario.getDireccion(), usuario.getCedula());
            } else if (usuario instanceof UsuarioCliente) { // Verifica que sea un cliente antes de obtener placa
                usuario = new UsuarioCliente(
                        usuario.getUid(),
                        usuario.getNombre(),
                        usuario.getTelefono(),
                        usuario.getDireccion(),
                        usuario.getCedula(),
                        ((UsuarioCliente) usuario).getPlaca()
                );
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("El usuario no puede ser convertido en CLIENTE, no es un cliente válido.")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }

            gestionUsuarios.actualizarUsuario(usuario);

            return Response.ok("Tipo de usuario actualizado correctamente")
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al cambiar el tipo de usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
    @PUT
    @Path("/{uid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(@PathParam("uid") String uid, Usuario usuarioActualizado) {
        try {
            Usuario usuarioExistente = gestionUsuarios.obtenerUsuarioPorUid(uid);

            if (usuarioExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }

            // 🔹 Actualizar los datos generales
            usuarioExistente.setNombre(usuarioActualizado.getNombre());
            usuarioExistente.setTelefono(usuarioActualizado.getTelefono());
            usuarioExistente.setDireccion(usuarioActualizado.getDireccion());
            usuarioExistente.setCedula(usuarioActualizado.getCedula());

            // 🔹 Si el usuario es un CLIENTE, actualizar la placa
            if (usuarioExistente instanceof UsuarioCliente && usuarioActualizado instanceof UsuarioCliente) {
                ((UsuarioCliente) usuarioExistente).setPlaca(((UsuarioCliente) usuarioActualizado).getPlaca());
            }

            // 🔹 Si se envió el cambio de tipo de usuario (ADMIN o CLIENTE)
            if (!usuarioExistente.getClass().equals(usuarioActualizado.getClass())) {
                if ("ADMIN".equalsIgnoreCase(usuarioActualizado.getClass().getSimpleName())) {
                    usuarioExistente = new UsuarioAdmin(
                            usuarioExistente.getUid(),
                            usuarioExistente.getNombre(),
                            usuarioExistente.getTelefono(),
                            usuarioExistente.getDireccion(),
                            usuarioExistente.getCedula()
                    );
                } else {
                    usuarioExistente = new UsuarioCliente(
                            usuarioExistente.getUid(),
                            usuarioExistente.getNombre(),
                            usuarioExistente.getTelefono(),
                            usuarioExistente.getDireccion(),
                            usuarioExistente.getCedula(),
                            ((UsuarioCliente) usuarioActualizado).getPlaca()
                    );
                }
            }

            // 🔹 Guardar cambios en la base de datos
            gestionUsuarios.actualizarUsuario(usuarioExistente);

            return Response.ok(usuarioExistente)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .build();

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity("Error al actualizar usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .header("Access-Control-Allow-Methods", "PUT, GET, POST, DELETE, OPTIONS")
                    .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                    .header("Access-Control-Allow-Credentials", "true")
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


