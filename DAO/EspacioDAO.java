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
    public Espacio actualizarEspacio(Espacio espacio) {
        espacio.setId(1L);
        return em.merge(espacio);
    }

    public Espacio listarEspacios() {
        return em.find(Espacio.class, 1L);
    }
}
