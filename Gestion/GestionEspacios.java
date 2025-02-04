package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.Objetos.Espacio;
import ups.edu.parking.DAO.EspacioDAO;
import ups.edu.parking.Objetos.Horario;
import ups.edu.parking.Objetos.Reserva;

import java.util.List;

@ApplicationScoped
public class GestionEspacios {

    @Inject
    private EspacioDAO espacioDAO;

    public void crearEspacio(Espacio espacio) {
        espacioDAO.crearEspacio(espacio);
    }


    public Espacio obtenerEspacioPorId(Long id) {
        return espacioDAO.buscarPorId(id);
    }

    public List<Espacio> listarEspacios() {
        return espacioDAO.listarEspacios();
    }

    public boolean eliminarEspacio(Long id) {
        Espacio espacio = espacioDAO.buscarPorId(id);
        if (espacio != null) {
            espacioDAO.eliminarEspacio(id);
            return true;
        }
        return false;
    }}
