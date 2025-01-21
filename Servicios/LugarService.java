package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.Gestion.GestionLugar;
import ups.edu.parking.Objetos.Lugar;

import java.util.List;

public class LugarService {

    @Inject
    private GestionReservas gestionReservas;

    /**
     * Crea una nueva reserva en el sistema.
     * @param reserva Objeto Reserva a crear.
     * @return Respuesta HTTP con el estado de la operación.
     */

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response obtenerLugarPorId(@PathParam("id") Long id) {
        return null;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listarLugar() {
        return null;
    }

}
