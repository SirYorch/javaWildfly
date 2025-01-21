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
    public Tarifa ObtenerTarifaPorId(Long id) {
        return tarifaDAO.buscarPorId(id);
    }

    public List<Tarifa> listarTarifas() {
        return tarifaDAO.listarTarifas();
    }

    public void eliminarTarifa(Long id) {
        tarifaDAO.eliminarTarifa(id);
    }
}
