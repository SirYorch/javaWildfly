package ups.edu.parking;

import jakarta.enterprise.context.SessionScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.DAO.UsuarioDAO;
import java.io.Serializable;
import java.util.List;

@Named
@SessionScoped
public class MiBean implements Serializable {

    @Inject
    private UsuarioDAO usuarioDAO;

    private List<Usuario> usuarios;
    private Usuario usuarioSeleccionado;
    private String mensaje;

    public void recargar() {
        usuarios = usuarioDAO.listarUsuarios();
        System.out.println("Usuarios cargados: " + usuarios.size());
    }

    public void seleccionarUsuario(Usuario usuario) {
        this.usuarioSeleccionado = usuario;
        mensaje = "Usuario seleccionado: " + usuario.getNombre();
        System.out.println("Usuario seleccionado: " + usuario.getNombre());
    }

    public void makeAdmin(){
        if (usuarioSeleccionado != null) {
            usuarioSeleccionado.setStat("ADMIN");
            usuarioDAO.actualizarUsuario(usuarioSeleccionado);
            System.out.println("Usuario " + usuarioSeleccionado.getNombre() + " ascendido a ADMIN.");
        } else {
            System.out.println("No se ha seleccionado un usuario.");
        }
    }

    public List<Usuario> getUsuarios() {
        if (usuarios == null) {
            recargar();
        }
        return usuarios;
    }

    public Usuario getUsuarioSeleccionado() { return usuarioSeleccionado; }
}
