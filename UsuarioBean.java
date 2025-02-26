package ups.edu.parking;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import java.util.ArrayList;
import java.util.List;

@Named
@RequestScoped
public class UsuarioBean {

    private String nombre;
    private String cedula;
    private String telefono;
    private String cedula1;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private TelefonoDAO telefonoDAO;

    public void agregarTelefono() {
        Telefono telefono = new Telefono();
        Usuario usuario = usuarioDAO.buscarUsuarioPorCedula(cedula1);
        if(usuario != null){
            System.out.println(usuario.getNombre());
            telefono.setUsuario(usuario);
            telefono.setNumero(this.telefono);
            telefonoDAO.guardarTelefono(telefono);
        }
        this.telefono = "";
        this.cedula1 = "";
        }

    public void eliminarTelefono(String numero) {
        telefonoDAO.eliminarTelefono(numero);
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCedula1() {
        return cedula1;
    }

    public void setCedula1(String cedula1) {
        this.cedula1 = cedula1;
    }

    public UsuarioDAO getUsuarioDAO() {
        return usuarioDAO;
    }

    public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
        this.usuarioDAO = usuarioDAO;
    }

    public TelefonoDAO getTelefonoDAO() {
        return telefonoDAO;
    }

    public void setTelefonoDAO(TelefonoDAO telefonoDAO) {
        this.telefonoDAO = telefonoDAO;
    }

    public void setUsuariosRegistrados(List<Usuario> usuariosRegistrados) {
        this.usuariosRegistrados = usuariosRegistrados;
    }

    public void registrarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre(nombre);
        usuario.setCedula(cedula);
        usuarioDAO.guardarUsuario(usuario); // ✅ Se guarda correctamente
        System.out.println("Usuario guardado: " + usuario.getNombre());
        List<Telefono> telefonos = telefonoDAO.obtenerTelefonosPorUsuario(usuario.getCedula());
        for (Telefono t : telefonos) {
            System.out.println("Teléfonos: " + t.getNumero());
        }

        // Limpiar formulario
        this.nombre = "";
        this.cedula = "";
    }
    private List<Usuario> usuariosRegistrados = new ArrayList<>();

    public List<Usuario> getUsuariosRegistrados() {
        this.usuariosRegistrados = usuarioDAO.obtenerUsuarios();
        return this.usuariosRegistrados;
    }

    public List<Telefono> telefonosPorUsuario(String index) {

        System.out.println("0"+index);
        try {
            List<Telefono> telefonos = telefonoDAO.obtenerTelefonosPorUsuario(index);
            System.out.println(telefonos);
            return telefonos;
        } catch (Exception e) {
            return new ArrayList<>();
        }

    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

    public void eliminarUsuario(String cedula) {
        for(Telefono t : telefonosPorUsuario(cedula)){
            telefonoDAO.eliminarTelefono(t.getNumero());
        }
        usuarioDAO.eliminarUsuario(cedula);
    }
}
