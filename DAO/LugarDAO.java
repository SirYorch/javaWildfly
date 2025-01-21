package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Lugar;

import java.util.List;

@ApplicationScoped
public class LugarDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;


    @Transactional

    public Lugar buscarPorId(Long id) {
        return em.find(Lugar.class, id);
    }

    public List<Lugar> listarLugares() {
        return em.createQuery("SELECT e FROM Lugar e", Lugar.class).getResultList();
    }

}
