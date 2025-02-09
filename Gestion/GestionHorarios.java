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

    public Horario actualizarHorario(Horario horario) {
        return horarioDAO.actualizarHorario(horario);
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

