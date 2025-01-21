package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Horario;

import java.util.List;

@ApplicationScoped
public class HorarioDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    @Transactional
    public void crearHorario(Horario horario) {
        em.persist(horario);
    }

    public Horario buscarPorId(Long id) {
        return em.find(Horario.class, id);
    }

    public List<Horario> listarHorarios() {
        return em.createQuery("SELECT h FROM Horario h", Horario.class).getResultList();
    }

    @Transactional
    public Horario actualizarHorario(Horario horario) {
        return em.merge(horario);
    }

    @Transactional
    public void eliminarHorario(Long id) {
        Horario horario = em.find(Horario.class, id);
        if (horario != null) {
            em.remove(horario);
        }
    }
}

