package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Gestion.GestionArriendos;
import ups.edu.parking.Objetos.Arriendo;

import java.util.List;

@Path("/arriendos")
public class ArriendoService {

    @Inject
    private GestionArriendos gestionArriendos;

    /**
     * Crea un nuevo arriendo en el sistema.
     * @param arriendo Objeto Arriendo a crear.
     * @return Respuesta HTTP con el estado de la operación.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearArriendo(Arriendo arriendo) {
        // Método vacío para ser implementado
        return null;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerArriendoPorId(@PathParam("id") Long id) {
        Arriendo arriendo = gestionArriendos.obtenerArriendoPorId(id);
        if (arriendo != null) {
            return Response.ok(arriendo).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("Arriendo no encontrado").build();
        }
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarArriendos() {
        List<Arriendo> arriendos = gestionArriendos.listarArriendos();
        return Response.ok(arriendos).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarArriendo(@PathParam("id") Long id) {
        // Método vacío para ser implementado
        return null;
    }
}
