package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.EspacioDAO;
import ups.edu.parking.Objetos.Espacio;

import java.util.List;

@ApplicationScoped
public class GestionEspacios {

    @Inject
    private EspacioDAO espacioDAO;

    public void crearEspacio(Espacio espacio) {
        espacioDAO.crearEspacio(espacio);
    }

    public Espacio actualizarEspacio(Espacio espacio) {
        return espacioDAO.actualizarEspacio(espacio);
    }

    public Espacio obtenerEspacioPorId(Long id) {
        return espacioDAO.buscarPorId(id);
    }

    public List<Espacio> listarEspacios() {
        return espacioDAO.listarEspacios();
    }

    public void eliminarEspacio(Long id) {
        espacioDAO.eliminarEspacio(id);
    }
}
