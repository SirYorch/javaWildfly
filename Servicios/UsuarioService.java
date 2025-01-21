package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Gestion.GestionUsuarios;
import ups.edu.parking.Objetos.Usuario;

@Path("/usuarios")
public class UsuarioService {

    @Inject
    private GestionUsuarios gestionUsuarios;

    /**
     * Crea un nuevo usuario en el sistema.
     * @param usuario Objeto Usuario a crear.
     * @return Respuesta HTTP con el estado de la operación.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearUsuario(Usuario usuario) {
        // Método vacío para ser implementado
        return null;
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerUsuarioPorId(@PathParam("id") Long id) {
        Usuario usuario = gestionUsuarios.obtenerUsuarioPorId(id);
        if (usuario != null) {
            return Response.ok(usuario).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Usuario no encontrado").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarUsuarios() {
        List<Usuario> usuarios = gestionUsuarios.obtenerUsuarios();
        return Response.ok(usuarios).build();
    }    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarUsuario(Usuario usuario) {
        // Método vacío para ser implementado
        return null;
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarUsuario(@PathParam("id") Long id) {
        // Método vacío para ser implementado
        return null;
    }
}

