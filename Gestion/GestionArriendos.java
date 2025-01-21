package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.ArriendoDAO;
import ups.edu.parking.Objetos.Arriendo;

import java.util.List;

@ApplicationScoped
public class GestionArriendos {

    @Inject
    private ArriendoDAO arriendoDAO;

    public void crearArriendo(Arriendo arriendo) {
        arriendoDAO.crearArriendo(arriendo);
    }

    public Arriendo actualizarArriendo(Arriendo arriendo) {
        return arriendoDAO.actualizarArriendo(arriendo);
    }

    public Arriendo obtenerArriendoPorId(Long id) {
        return arriendoDAO.buscarPorId(id);
    }

    public List<Arriendo> listarArriendos() {
        return arriendoDAO.listarArriendos();
    }

    public void eliminarArriendo(Long id) {
        arriendoDAO.eliminarArriendo(id);
    }
}
