package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Lugar;
import ups.edu.parking.Objetos.Reserva;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.Servicios.UsuarioService;

import java.util.List;

@ApplicationScoped
public class UsuarioDAO {

//    private final UsuarioService usuarioService;
    @PersistenceContext(unitName = "PostgresPU")
    private EntityManager em;

//    @jakarta.inject.Inject
//    public UsuarioDAO(UsuarioService usuarioService) {
//        this.usuarioService = usuarioService;
//    }

    @Transactional
    public void crearUsuario(Usuario usuario) {
        try {
            em.persist(usuario);
            em.flush(); // Forzar la inserción inmediata en la base de datos
        } catch (Exception e) {
            e.printStackTrace(); // Para ver errores en la consola de WildFly
        }
    }
    public Usuario buscarPorUid(String uid) {

        List<Usuario> usuarios = em.createQuery(
                        "SELECT u FROM Usuario u WHERE u.uid = :uid", Usuario.class)
                .setParameter("uid", uid)
                .getResultList();

        if (usuarios.isEmpty()) {

            return null;
        } else {

            return usuarios.get(0);
        }
    }

    @Transactional
    public Usuario actualizarUsuario(Usuario usuario) {
        try {
            return em.merge(usuario);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo de errores
        }
    }




    public List<Usuario> listarUsuarios() {
        return em.createQuery("SELECT u FROM Usuario u", Usuario.class).getResultList();
    }

    @Transactional
    public void formatUsuarios() {
        List<Usuario> usuarios = listarUsuarios();
        for (Usuario usuario : usuarios) {
            usuario.setLugar(null);
            usuario.setReserva(null);
            em.merge(usuario);
        }
    }

    @Transactional
    public void eliminarUsuario(String uid) {
        Usuario usuario = buscarPorUid(uid);
        if (usuario != null) {
            em.remove(usuario);
        }
    }



    @Transactional
    public Usuario actualizarUsuarioEntrar(Lugar lugar, Usuario usuarioc,boolean reservado) {
        Usuario usuario = buscarPorUid(usuarioc.getUid());
        if (usuario != null) {
            usuario.setLugar(lugar);
            if(reservado) {
                usuario.setReserva(null);
            }
            return em.merge(usuario);
        } else {
        return null;}
    }

    @Transactional
    public Usuario actualizarUsuarioReservar(Lugar lugar, Usuario usuarioc, Reserva reserva) {
        Usuario usuario = buscarPorUid(usuarioc.getUid());
        if (usuario != null) {
            usuario.setLugar(lugar);
            usuario.setReserva(reserva);
            return em.merge(usuario);
        } else {
            return null;}
    }

    @Transactional
    public Usuario actualizarUsuarioSalir(Usuario usuarioc) {
        Usuario usuario = buscarPorUid(usuarioc.getUid());
        if (usuario != null) {
            usuario.setLugar(null);
            usuario.setReserva(null);
            return em.merge(usuario);
        } else {
            return null;}
    }


    @Transactional
    public void eliminarReservacion(Usuario usuario) {
        usuario.setReserva(null);
        usuario.setLugar(null);
        em.merge(usuario);
    }
}
