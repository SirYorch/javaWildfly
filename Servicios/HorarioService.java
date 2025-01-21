package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Gestion.GestionHorarios;
import ups.edu.parking.Objetos.Horario;


import java.util.List;

@Path("/horarios")
public class HorarioService {

    @Inject
    private GestionHorarios gestionHorarios;

    /**
     * Crea un nuevo horario en el sistema.
     * @param horario Objeto Horario a crear.
     * @return Respuesta HTTP con el estado de la operación.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response crearHorario(Horario horario) {
        gestionHorarios.crearHorario(horario);
        return Response.status(Response.Status.CREATED).entity(horario).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarHorarios() {
        List<Horario> horarios = gestionHorarios.listarHorarios();
        return Response.ok(horarios).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarHorario(Horario horario) {
        Horario horarioActualizado = gestionHorarios.actualizarHorario(horario);
        return Response.ok(horarioActualizado).build();
    }

    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response eliminarHorario(@PathParam("id") Long id) {
        gestionHorarios.eliminarHorario(id);
        return Response.noContent().build();
    }
}

