package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.Objetos.UsuarioAdmin;
import ups.edu.parking.Objetos.UsuarioCliente;
import ups.edu.parking.Gestion.GestionUsuarios;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import java.util.List;

@Path("/usuarios")
public class UsuarioService {

    @Inject
    private GestionUsuarios gestionUsuarios;

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(UsuarioCliente usuarioCliente) {
        try {
            Usuario existente = gestionUsuarios.obtenerUsuarioPorUid(usuarioCliente.getUid());
            if (existente != null) {
                return Response.status(Response.Status.CONFLICT)
                        .entity("El usuario ya existe")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE")
                        .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                        .header("Access-Control-Allow-Credentials", "true")
                        .build();
            }

            usuarioCliente = new UsuarioCliente(
                    usuarioCliente.getUid(),
                    usuarioCliente.getNombre(),
                    usuarioCliente.getTelefono(),
                    usuarioCliente.getDireccion(),
                    usuarioCliente.getCedula(),
                    usuarioCliente.getPlaca()
            );

            gestionUsuarios.crearUsuario(usuarioCliente);

            return Response.status(Response.Status.CREATED)
                    .entity(usuarioCliente)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .header("Access-Control-Allow-Methods", "POST, GET, PUT, DELETE")
                    .header("Access-Control-Allow-Headers", "Content-Type, Authorization")
                    .header("Access-Control-Allow-Credentials", "true")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al crear el usuario: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    @GET
    @Path("/{uid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorUid(@PathParam("uid") String uid) {
        Usuario usuario = gestionUsuarios.obtenerUsuarioPorUid(uid);
        if (usuario != null) {
            return Response.ok(usuario)
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado")
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }

    @PUT
    @Path("/cambiarRol/{uid}/{rol}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response cambiarRol(@PathParam("uid") String uid, @PathParam("rol") String nuevoRol) {
        try {
            Usuario usuario = gestionUsuarios.obtenerUsuarioPorUid(uid);
            if (usuario == null) {
                return Response.status(Response.Status.NOT_FOUND)
                        .entity("Usuario no encontrado")
                        .header("Access-Control-Allow-Origin", "http://localhost:4200")
                        .build();
            }

            if (nuevoRol.equalsIgnoreCase("ADMIN")) {
                UsuarioAdmin usuarioAdmin = new UsuarioAdmin(
                        usuario.getUid(), usuario.getNombre(),
                        usuario.getTelefono(), usuario.getDireccion(),
                        usuario.getCedula()
                );
                gestionUsuarios.actualizarUsuario(usuarioAdmin);
            } else {
                UsuarioCliente usuarioCliente = new UsuarioCliente(
                        usuario.getUid(), usuario.getNombre(),
                        usuario.getTelefono(), usuario.getDireccion(),
                        usuario.getCedula(), ((UsuarioCliente) usuario).getPlaca()
                );
                gestionUsuarios.actualizarUsuario(usuarioCliente);
            }

            return Response.ok("Rol actualizado correctamente")
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        } catch (Exception e) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("Error al cambiar el rol: " + e.getMessage())
                    .header("Access-Control-Allow-Origin", "http://localhost:4200")
                    .build();
        }
    }
}
