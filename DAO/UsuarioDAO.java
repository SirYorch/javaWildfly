package ups.edu.parking.DAO;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import ups.edu.parking.Objetos.Lugar;
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
            Usuario usuarioExistente = buscarPorUid(usuario.getUid());
            if (usuarioExistente == null) {
                return null; // Usuario no encontrado
            }

            //  Si se cambia de CLIENTE a ADMIN, o viceversa, se necesita recrear el objeto
            if ((usuarioExistente instanceof ups.edu.parking.Objetos.UsuarioCliente && usuario instanceof ups.edu.parking.Objetos.UsuarioAdmin) ||
                    (usuarioExistente instanceof ups.edu.parking.Objetos.UsuarioAdmin && usuario instanceof ups.edu.parking.Objetos.UsuarioCliente)) {

                // Primero eliminamos el usuario existente
                em.remove(usuarioExistente);
                em.flush();

                // Luego creamos el nuevo usuario con el nuevo tipo
                if (usuario instanceof ups.edu.parking.Objetos.UsuarioAdmin) {
                    usuarioExistente = new ups.edu.parking.Objetos.UsuarioAdmin(
                            usuario.getUid(),
                            usuario.getNombre(),
                            usuario.getTelefono(),
                            usuario.getDireccion(),
                            usuario.getCedula(),
                            usuario.getCorreo(),
                            usuario.isReservado()
                    );
                } else {
                    usuarioExistente = new ups.edu.parking.Objetos.UsuarioCliente(
                            usuario.getUid(),
                            usuario.getNombre(),
                            usuario.getTelefono(),
                            usuario.getDireccion(),
                            usuario.getCedula(),
                            usuario.getCorreo(),
                            ((ups.edu.parking.Objetos.UsuarioCliente) usuario).getPlaca(),
                            usuario.isReservado()
                    );
                }

                //  Guardamos el nuevo usuario
                em.persist(usuarioExistente);
                em.flush();
                return usuarioExistente;
            }

            //  Si sigue siendo del mismo tipo, solo actualizamos los datos
            usuarioExistente.setNombre(usuario.getNombre());
            usuarioExistente.setTelefono(usuario.getTelefono());
            usuarioExistente.setDireccion(usuario.getDireccion());
            usuarioExistente.setCedula(usuario.getCedula());

            if (usuarioExistente instanceof ups.edu.parking.Objetos.UsuarioCliente) {
                ((ups.edu.parking.Objetos.UsuarioCliente) usuarioExistente)
                        .setPlaca(((ups.edu.parking.Objetos.UsuarioCliente) usuario).getPlaca());
            }

            return em.merge(usuarioExistente);
        } catch (Exception e) {
            e.printStackTrace();
            return null; // Manejo de errores
        }
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



    @Transactional
    public Usuario actualizarUsuarioEntrar(Lugar lugar, Usuario usuarioc) {
        Usuario usuario = buscarPorUid(usuarioc.getUid());
        if (usuario != null) {
            usuario.setLugar(lugar);
            usuario.setReservado(false);
            return em.merge(usuario);
        } else {
        return null;}
    }

    @Transactional
    public Usuario actualizarUsuarioReservar(Lugar lugar, Usuario usuarioc) {
        Usuario usuario = buscarPorUid(usuarioc.getUid());
        if (usuario != null) {
            usuario.setLugar(lugar);
            usuario.setReservado(true);
            return em.merge(usuario);
        } else {
            return null;}
    }

    @Transactional
    public Usuario actualizarUsuarioSalir(Usuario usuarioc) {
        System.out.println(usuarioc.getUid());
        Usuario usuario = buscarPorUid(usuarioc.getUid());
        System.out.println(usuario.getNombre());
        if (usuario != null) {
            usuario.setLugar(null);
            return em.merge(usuario);
        } else {
            return null;}
    }


    @Transactional
    public void eliminarReservacion(Usuario usuario) {
        usuario.setReservado(false);
        usuario.setLugar(null);
        em.merge(usuario);
    }
}
