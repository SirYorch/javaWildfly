package ups.edu.parking;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

import java.util.List;

@ApplicationScoped
public class UsuarioDAO {
    @PersistenceContext(unitName ="PostgresPU")
    private EntityManager em;

    public List<Usuario> obtenerUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    @Transactional
    public void guardarUsuario(Usuario usuario) {
        try{
            em.persist(usuario);
            System.out.println("Usuario " + usuario.getCedula() + " Creado");
        }catch (Exception e){
            Usuario u = em.find(Usuario.class,usuario.getCedula());
            if(u != null) {
                u.setNombre(usuario.getNombre());
                em.merge(usuario);
                System.out.println("Usuario " + usuario.getCedula() + " guardado");
            }

        }
    }


    @Transactional
    public Usuario buscarUsuarioPorCedula(String cedula) {
        return em.find(Usuario.class, cedula);
    }


    @Transactional
    public void actualizarUsuario(Usuario usuario) {
        em.merge(usuario);
    }

    @Transactional
    public void eliminarUsuario(String cedula) {
        Usuario usuario = em.find(Usuario.class, cedula);
        if (usuario != null) {
            em.remove(usuario);
        }
    }
}
