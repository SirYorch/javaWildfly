package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.Objetos.Horario;
import ups.edu.parking.DAO.HorarioDAO;

import java.util.List;

@ApplicationScoped
public class GestionHorarios {

    @Inject
    private HorarioDAO horarioDAO;

    public void crearHorario(Horario horario) {
        horarioDAO.crearHorario(horario);
    }

    public Horario obtenerHorarioPorId(Long id) {
        return horarioDAO.buscarPorId(id);
    }

    public List<Horario> listarHorarios() {
        return horarioDAO.listarHorarios();
    }

    public boolean actualizarHorario(Horario horario) {
        Horario horarioExistente = horarioDAO.buscarPorId(horario.getId());
        if (horarioExistente != null) {
            horarioDAO.actualizarHorario(horario);
            return true;
        }
        return false;
    }

    public boolean eliminarHorario(Long id) {
        Horario horarioExistente = horarioDAO.buscarPorId(id);
        if (horarioExistente != null) {
            horarioDAO.eliminarHorario(id);
            return true;
        }
        return false;
    }
}

