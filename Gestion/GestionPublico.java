package ups.edu.parking.Gestion;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import ups.edu.parking.DAO.PublicoDAO;
import ups.edu.parking.DAO.UsuarioDAO;
import ups.edu.parking.Objetos.Arriendo;
import ups.edu.parking.Objetos.Publico;
import ups.edu.parking.Objetos.Usuario;
import ups.edu.parking.Servicios.MailService;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class GestionPublico {


    @Inject
    private PublicoDAO publicoDAO;

    @Inject
    private MailService mailService;
    @Inject
    private UsuarioDAO usuarioDAO;

    public Publico actualizarPublico(Publico publico) {
        return publicoDAO.actualizarPublico(publico);
    }

    public Publico getDatos(

    ) {
        return publicoDAO.buscarDatos();
    }

    public Publico enviarCorreos() {
        String to = "";
        String subject = "Cierre del parqueadero"+ LocalDate.now();
        String body = "EL parqueadero se encuentra por cerrar. por favor retire su vehiculo.";

        List<Usuario> usuarios = usuarioDAO.listarUsuarios();
        try {
            for(Usuario usuario : usuarios) {
                if((usuario.getLugar()!=null)&&(usuario.getReserva()==null)&&(usuario.getArriendo()==null) ) {
                    mailService.sendEmail(usuario.getCorreo(), subject, body);
                }
            }
            return publicoDAO.buscarDatos();
        } catch (Exception e) {
            return null;
        }
    }
}
