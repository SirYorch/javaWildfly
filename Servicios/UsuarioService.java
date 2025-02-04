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
                System.out.println(" Usuario no encontrado en la base de datos.");

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

            if ("ADMIN".equalsIgnoreCase(nuevoTipo) && usuario instanceof UsuarioCliente) {
                usuario = new UsuarioAdmin(
                        usuario.getUid(),
                        usuario.getNombre(),
                        usuario.getTelefono(),
                        usuario.getDireccion(),
                        usuario.getCedula()
                );
            } else if ("CLIENTE".equalsIgnoreCase(nuevoTipo) && usuario instanceof UsuarioAdmin) {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("No se puede convertir un ADMIN en CLIENTE automáticamente")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST)
                        .entity("Tipo de usuario no válido")
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
    public Response actualizarUsuario(@PathParam("uid") String uid, Usuario usuario) {
        try {
            Usuario usuarioExistente = gestionUsuarios.obtenerUsuarioPorUid(uid);
            if (usuarioExistente == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }

            // 🔹 Verificar si se quiere cambiar a ADMIN
            if (usuario instanceof UsuarioAdmin && usuarioExistente instanceof UsuarioCliente) {
                usuarioExistente = new UsuarioAdmin(
                        usuario.getUid(),
                        usuario.getNombre(),
                        usuario.getTelefono(),
                        usuario.getDireccion(),
                        usuario.getCedula()
                );
            }
            // 🔹 Si sigue siendo CLIENTE, validar la placa
            else if (usuario instanceof UsuarioCliente && usuarioExistente instanceof UsuarioCliente) {
                ((UsuarioCliente) usuarioExistente).setPlaca(((UsuarioCliente) usuario).getPlaca());
            }

            // 🔹 Actualizar información general
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setCedula(usuario.getCedula());

            // 🔹 Guardar cambios en la base de datos
            gestionUsuarios.actualizarUsuario(usuarioExistente);

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


