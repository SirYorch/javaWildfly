package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.*;
import ups.edu.parking.Objetos.Espacio;
import ups.edu.parking.Objetos.Horario;
import ups.edu.parking.Objetos.Reserva;
import ups.edu.parking.Servicios.UsuarioService;

import java.util.List;

@ApplicationScoped
public class GestionEspacios {

    @Inject
    private EspacioDAO espacioDAO;
    @Inject
    private LugarDAO lugarDAO;
    @Inject
    private ReservaDAO reservaDAO;
    @Inject
    private ArriendoDAO arriendoDAO;
    @Inject
    private UsuarioDAO usuarioDAO;

    public Espacio actualizarDatos(Espacio espacio){

        System.out.println("Intento de eliminacion de la espacio");
        //colocar todos los usuarios sin lugar, eliminar funcinoes de reservado
        usuarioDAO.formatUsuarios();
        System.out.println("Se eliminan los usuarios");


        //eliiminar todas las reservas
        reservaDAO.eliminarReservas();
        System.out.println("Eliminacion de reservas");

        //eliminar todos los arriendos
        arriendoDAO.eliminarArriendos();
        System.out.println("Se eliminan los arriendos");



        //metodo para rehacer los lugares.
        lugarDAO.eliminarLugares(espacio.getFilas(),espacio.getColumnas());
        return espacioDAO.actualizarEspacio(espacio);


    }

    public Espacio listarEspacios() {
        return espacioDAO.listarEspacios();
    }

}
