package ups.edu.parking;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class TelefonoDAO {

    @PersistenceContext(unitName ="PostgresPU")
    private EntityManager em;

    @Transactional
    public void guardarTelefono(Telefono telefono) {
        try{
            em.persist(telefono);
            System.out.println("Telefono " + telefono.getNumero() + " Creado");
        }catch (Exception e){
            System.out.println("Error al guardar el telefono " + telefono.getNumero());
        }
    }

    public List<Telefono> obtenerTelefonosPorUsuario(String Cedula) {
        return em.createQuery("SELECT t FROM Telefono t WHERE t.usuario.cedula = :usuarioId", Telefono.class)
                .setParameter("usuarioId", Cedula)
                .getResultList();
    }

    @Transactional
    public void eliminarTelefono(String numero) {
        Telefono telefono = em.find(Telefono.class, numero);
        if (telefono != null) {
            em.remove(telefono);
        }
    }
}
