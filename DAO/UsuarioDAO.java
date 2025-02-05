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
    public Usuario buscarPorUid(String uid) {
        System.out.println(" Ejecutando consulta en la base de datos para UID: " + uid);
        List<Usuario> usuarios = em.createQuery(
                        "SELECT u FROM Usuario u WHERE u.uid = :uid", Usuario.class)
                .setParameter("uid", uid)
                .getResultList();

        if (usuarios.isEmpty()) {
            System.out.println(" Usuario no encontrado en la base de datos.");
            return null;
        } else {
            System.out.println(" Usuario encontrado en la base de datos: " + usuarios.get(0).getNombre());
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
                            usuario.getCedula()
                    );
                } else {
                    usuarioExistente = new ups.edu.parking.Objetos.UsuarioCliente(
                            usuario.getUid(),
                            usuario.getNombre(),
                            usuario.getTelefono(),
                            usuario.getDireccion(),
                            usuario.getCedula(),
                            ((ups.edu.parking.Objetos.UsuarioCliente) usuario).getPlaca()
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

}
