package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Usuario;

import java.util.List;

@ApplicationScoped
public class UsuarioDAO {

    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

    @Transactional
    public void crearUsuario(Usuario usuario) {
        try {
            em.persist(usuario);
            em.flush(); // Forzar la inserción inmediata en la base de datos
        } catch (Exception e) {
            e.printStackTrace(); // Para ver errores en la consola de WildFly
        }
    }
    @Transactional
    public Usuario actualizarUsuario(Usuario usuario) {
        return em.merge(usuario);
    }

    public Usuario buscarPorUid(String uid) {
        List<Usuario> usuarios = em.createQuery("SELECT u FROM Usuario u WHERE u.uid = :uid", Usuario.class)
                .setParameter("uid", uid)
                .getResultList();
        return usuarios.isEmpty() ? null : usuarios.get(0);
    }

    public List<Usuario> listarUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    @Transactional
    public void eliminarUsuario(String uid) {
        Usuario usuario = buscarPorUid(uid);
        if (usuario != null) {
            em.remove(usuario);
        }
    }

}
