package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Tarifa;

import java.util.List;
@ApplicationScoped
public class TarifaDAO {


    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    @Transactional
    public void crearTarifa(Tarifa tarifa) {
        em.persist(tarifa);
    }

    public Tarifa buscarPorId(Long id) {

        return em.find(Tarifa.class, id);
    }

    public List<Tarifa> listarTarifas() {
        return em.createQuery("SELECT t FROM Tarifa t", Tarifa.class).getResultList();
    }

    @Transactional
    public void eliminarTarifa(Long id) {
        Tarifa tarifa = em.find(Tarifa.class, id);
        if (tarifa!= null) {
            em.remove(tarifa);
        }
    }
}
