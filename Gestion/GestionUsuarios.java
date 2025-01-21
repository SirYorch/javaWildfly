package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.DAO.UsuarioDAO;

import java.util.List;

@ApplicationScoped
public class GestionUsuarios {

    @Inject
    private UsuarioDAO usuarioDAO;

    public void crearUsuario(Usuario usuario) {
        usuarioDAO.crearUsuario(usuario);
    }

    public Usuario actualizarUsuario(Usuario usuario) {
        return usuarioDAO.actualizarUsuario(usuario);
    }

    public Usuario obtenerUsuarioPorId(Long id) {
        return usuarioDAO.buscarPorId(id);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public void eliminarUsuario(Long id) {
        usuarioDAO.eliminarUsuario(id);
    }
}

