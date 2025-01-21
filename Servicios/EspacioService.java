package ups.edu.parking.Servicios;

import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import ups.edu.parking.Gestion.GestionEspacios;
import ups.edu.parking.Objetos.Espacio;

import java.util.List;

@Path("/espacios")
public class EspacioService {

    @Inject
    private GestionEspacios gestionEspacios;

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response actualizarEspacio(Espacio espacio) {
        Espacio espacioActualizado = gestionEspacios.actualizarEspacio(espacio);
        return Response.ok(espacioActualizado).build();
    }

}

