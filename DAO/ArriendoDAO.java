package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Arriendo;
import ups.edu.parking.Objetos.Reserva;

import java.util.Date;
import java.util.List;

@ApplicationScoped
public class ArriendoDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    @Transactional
    public void crearArriendo(Arriendo arriendo) {
        em.persist(arriendo);
    }

    @Transactional
    public Arriendo actualizarArriendo(Arriendo arriendo) {
        return em.merge(arriendo);
    }

    public Arriendo buscarPorId(Long id) {
        return em.find(Arriendo.class, id);
    }


    public Arriendo buscarPorFechas(Date fechaInicio, Date fechaFin) {
        List<Arriendo> arriendos = em.createQuery("SELECT r FROM Arriendo r WHERE r.fecha_Fin = :fechaFin and r.fecha_Inicio = :fechaInicio ", Arriendo.class)
                .setParameter("fechaFin", fechaFin)
                .setParameter("fechaInicio", fechaInicio)
                .getResultList();
        return arriendos.get(arriendos.size()-1);
    }

    public List<Arriendo> listarArriendos() {
        return em.createQuery("SELECT a FROM Arriendo a", Arriendo.class).getResultList();
    }

    @Transactional
    public void eliminarArriendo(Long id) {
        Arriendo arriendo = em.find(Arriendo.class, id);
        if (arriendo != null) {
            em.remove(arriendo);
        }
    }

    @Transactional
    public void eliminarArriendos() {
        List<Arriendo> arriendos = listarArriendos();
        for (Arriendo arriendo : arriendos) {
            em.remove(arriendo);
        }
    }
}

