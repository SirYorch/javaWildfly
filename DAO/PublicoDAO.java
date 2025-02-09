package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Arriendo;
import ups.edu.parking.Objetos.Publico;

@ApplicationScoped
public class PublicoDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    @Transactional
    public Publico actualizarPublico(Publico publico) {

        return em.merge(publico);
    }
@Transactional
    public Publico buscarDatos() {
        return em.find(Publico.class, 1L);
    }

}
