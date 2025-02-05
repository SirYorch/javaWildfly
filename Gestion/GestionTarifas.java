package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.ReservaDAO;
import ups.edu.parking.DAO.TarifaDAO;
import ups.edu.parking.Objetos.Horario;
import ups.edu.parking.Objetos.Tarifa;

import java.util.List;


@ApplicationScoped
public class GestionTarifas {

    @Inject
    private TarifaDAO tarifaDAO;

    public void crearTarifa(Tarifa tarifa) {
        tarifaDAO.crearTarifa(tarifa);
    }
    public Tarifa obtenerTarifaPorId(Long id) {
        return tarifaDAO.obtenerPorId(id);
    }

    public List<Tarifa> listarTarifas() {
        return tarifaDAO.listarTarifas();
    }

    public boolean eliminarTarifa(Long id) {
        Tarifa tarifa = tarifaDAO.obtenerPorId(id);
        if (tarifa != null) {
            tarifaDAO.eliminarTarifa(id);
            return true;
        }
        return false;
    }
    public Tarifa actualizarTarifa(Long id, Tarifa tarifa) {
        return tarifaDAO.actualizarTarifa(id, tarifa);
    }
}
