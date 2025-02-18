package ups.edu.parking.Gestion;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.LugarDAO;
import ups.edu.parking.DAO.TicketDAO;
import ups.edu.parking.DAO.UsuarioDAO;
import ups.edu.parking.Objetos.Arriendo;
import ups.edu.parking.DAO.ArriendoDAO;
import ups.edu.parking.Objetos.Lugar;
import ups.edu.parking.Objetos.Ticket;
import ups.edu.parking.Objetos.Usuario;


import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class GestionArriendos {

    @Inject
    private ArriendoDAO arriendoDAO;

    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private LugarDAO lugarDAO;
    @Inject
    private TicketDAO ticketDAO;

    public void crearArriendo(String uid,long lugar_id, Arriendo arriendo) {
        arriendoDAO.crearArriendo(arriendo);
        Usuario usuario = usuarioDAO.buscarPorUid(uid);
        Arriendo a = arriendoDAO.buscarPorFechas(arriendo.getFechaInicio(),arriendo.getFechaFin());
        usuario.setArriendo(a);
        Lugar lugar = lugarDAO.buscarPorId(lugar_id);
        lugar.setEstado("arrendado");
        lugarDAO.actualizar(lugar);
        usuario.setLugar(lugar);
        usuarioDAO.actualizarUsuario(usuario);


        Date fechaInicio = arriendo.getFechaInicio();
        Date fechaFin = arriendo.getFechaFin();

        // Calcula la diferencia en milisegundos
        long diferenciaEnMilisegundos = fechaFin.getTime() - fechaInicio.getTime();

        // Convierte la diferencia de milisegundos a días
        long dias = TimeUnit.MILLISECONDS.toDays(diferenciaEnMilisegundos);

        double precioDia = 3.0;
        double reduccionDia = 0.03;
        double precio = (dias * precioDia)-(reduccionDia * dias);

        Ticket ti = new Ticket();
        ti.setFechaFin(fechaFin);
        ti.setFechaInicio(fechaInicio);
        ti.setPrecio(precio);
        ti.setUsuario(usuario);
        ticketDAO.crearTicket(ti);

    }

    public Arriendo actualizarArriendo(Arriendo arriendo) {
        return arriendoDAO.actualizarArriendo(arriendo);
    }

    public Arriendo obtenerArriendoPorId(Long id) {
        return arriendoDAO.buscarPorId(id);
    }

    public List<Arriendo> listarArriendos() {
        return arriendoDAO.listarArriendos();
    }

    public boolean eliminarArriendo(String id) {

        try {
            Usuario usuario = usuarioDAO.buscarPorUid(id);
            Lugar lugar = usuario.getLugar();
            usuario.setLugar(null);
            Arriendo arriendo = usuario.getArriendo();
            usuario.setArriendo(null);
            usuarioDAO.actualizarUsuario(usuario);
            arriendoDAO.eliminarArriendo(arriendo.getId());
            lugarDAO.eliminarReservacion(lugar);
            return true;
        }catch (Exception e){
            return false;
        }
    }

}
