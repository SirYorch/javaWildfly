package ups.edu.parking.Gestion;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.LugarDAO;
import ups.edu.parking.Objetos.Lugar;

import java.util.List;

@ApplicationScoped
public class GestionLugares {

    @Inject
    private LugarDAO lugarDAO;



    public Lugar obtenerLugarPorId(Long id) {
        return lugarDAO.buscarPorId(id);
    }

    public List<Lugar> listarLugares() {
        return lugarDAO.listarLugares();
    }

    public Lugar cambiarEstado(Lugar lugar) {
        return lugarDAO.actualizar(lugar);
    }
}

