package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Espacio;

import java.util.List;

@ApplicationScoped
public class EspacioDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    @Transactional
    public void crearEspacio(Espacio espacio) {
        em.persist(espacio);
    }

    @Transactional
    public Espacio actualizarEspacio(Espacio espacio) {
        return em.merge(espacio);
    }

    public Espacio buscarPorId(Long id) {
        return em.find(Espacio.class, id);
    }

    public List<Espacio> listarEspacios() {
        return em.createQuery("SELECT e FROM Espacio e", Espacio.class).getResultList();
    }

    @Transactional
    public void eliminarEspacio(Long id) {
        Espacio espacio = em.find(Espacio.class, id);
        if (espacio != null) {
            em.remove(espacio);
        }
    }
}
