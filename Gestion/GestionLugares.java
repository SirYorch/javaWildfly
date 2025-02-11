package ups.edu.parking.Gestion;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.LugarDAO;
import ups.edu.parking.DAO.UsuarioDAO;
import ups.edu.parking.Objetos.Lugar;
import ups.edu.parking.Objetos.Usuario;

import java.util.List;

@ApplicationScoped
public class GestionLugares {

    @Inject
    private LugarDAO lugarDAO;


    @Inject
    private UsuarioDAO usuarioDAO;


    public Lugar obtenerLugarPorId(Long id) {
        return lugarDAO.buscarPorId(id);
    }

    public List<Lugar> listarLugares() {
        return lugarDAO.listarLugares();
    }

    public Lugar cambiarEstadoEntrar(Usuario usuario, Long id) {

        return lugarDAO.actualizarEntrar(id);
    }

    public Lugar cambiarEstadoSalir(Usuario usuario, Long id) {
        return lugarDAO.actualizarSalir(id);
    }


    public Lugar cambiarEstadoReservar(Usuario usuario, Long id) {
        return lugarDAO.actualizarReservar(id);
    }

    public Lugar cambiarEstadoArrendar(Usuario usuario, Long id) {
        return lugarDAO.actualizarArrendar(id);
    }
}

