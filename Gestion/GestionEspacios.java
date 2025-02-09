package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.LugarDAO;
import ups.edu.parking.Objetos.Espacio;
import ups.edu.parking.DAO.EspacioDAO;
import ups.edu.parking.Objetos.Horario;
import ups.edu.parking.Objetos.Reserva;

import java.util.List;

@ApplicationScoped
public class GestionEspacios {

    @Inject
    private EspacioDAO espacioDAO;
    @Inject
    private LugarDAO lugarDAO;

    public Espacio actualizarDatos(Espacio espacio){
        //metodo para rehacer los lugares.
        lugarDAO.eliminarLugares(espacio.getFilas(),espacio.getColumnas());
        return espacioDAO.actualizarEspacio(espacio);


    }

    public Espacio listarEspacios() {
        return espacioDAO.listarEspacios();
    }

}
