package ups.edu.parking.Gestion;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.*;
import ups.edu.parking.Objetos.*;

import java.sql.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

@ApplicationScoped
public class GestionLugares {

    @Inject
    private LugarDAO lugarDAO;


    @Inject
    private UsuarioDAO usuarioDAO;

    @Inject
    private TicketDAO ticketDAO;
    @Inject
    private ReservaDAO reservaDAO;

    @Inject
    private TarifaDAO tarifaDAO;


    public Lugar obtenerLugarPorId(Long id) {
        return lugarDAO.buscarPorId(id);
    }

    public List<Lugar> listarLugares() {
        return lugarDAO.listarLugares();
    }

    public Lugar cambiarEstadoEntrar(Usuario usuario, Long id) {
        Lugar l = lugarDAO.actualizarEntrar(id);
        usuarioDAO.actualizarUsuarioEntrar(l,usuario);

        List<Ticket> tickets = ticketDAO.obtenerTicketsPorUsuario(usuario.getUid());
        try{
            Ticket t = tickets.get(tickets.size() - 1);
            if(t.getFechaFin() == null) {
                ticketDAO.actualizarTicket(t);
            } else if(t.getFechaFin() != null) {
                Ticket ti = new Ticket();
                ti.setFechaInicio(new Date(System.currentTimeMillis()));
                ti.setPrecio(0);
                ti.setUsuario(usuario);
                ticketDAO.crearTicket(ti);
            }
        }catch (Exception e){

            Ticket ti = new Ticket();
            ti.setFechaInicio(new Date(System.currentTimeMillis()));
            ti.setPrecio(0);
            ti.setUsuario(usuario);
            ticketDAO.crearTicket(ti);
        }


        return l;
    }

    public Lugar cambiarEstadoSalir(Usuario usuario) {
        List<Ticket> tickets = ticketDAO.obtenerTicketsPorUsuario(usuario.getUid());

        try {
            Ticket t = tickets.get(tickets.size() - 1);
            t.setFechaFin(new Date(System.currentTimeMillis()));

            //calculo de precio a pagar con respecto a las tarifas
            List<Tarifa> tarifas = tarifaDAO.listarTarifas();
            java.util.Date fechaInicio = (java.util.Date) t.getFechaInicio();
            java.util.Date fechaFin = (java.util.Date) t.getFechaFin();
            double cantidadTiempo = TimeUnit.MILLISECONDS.toHours(fechaFin.getTime() - fechaInicio.getTime())+0.1;


            for(Tarifa tarifa : tarifas) {
                System.out.println(cantidadTiempo);
                System.out.println(tarifa.getDesde());
                System.out.println(tarifa.getHasta());
                if(cantidadTiempo >= Double.parseDouble(tarifa.getDesde()+"") && cantidadTiempo <Double.parseDouble(tarifa.getHasta()+"")) {

                    t.setPrecio(t.getPrecio()+  tarifa.getPrecio());
                    System.out.println(t.getPrecio());
                    ticketDAO.actualizarTicket(t);
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        Lugar l = lugarDAO.actualizarSalir(usuario.getLugar().getId());
        usuarioDAO.actualizarUsuarioSalir(usuario);
        return l;
    }


    public Lugar cambiarEstadoReservar(Usuario usuario, Long id) {
        List<Ticket> tickets = ticketDAO.obtenerTicketsPorUsuario(usuario.getUid());
        double tarifaPredeterminada = 1.5;  ////valor no colocado dentro de la pagina, sino solo como valor
        Ticket ti = new Ticket();
        ti.setFechaInicio(new Date(System.currentTimeMillis()));
        ti.setPrecio(tarifaPredeterminada);
        ti.setUsuario(usuario);
        ticketDAO.crearTicket(ti);
        Reserva r = new Reserva();
        Lugar l = lugarDAO.actualizarReservar(id);
        r.setLugar(l);
        r.setInicio(new Date(System.currentTimeMillis()));
        r.setTicket(ti);
        reservaDAO.crearReserva(r);
        usuarioDAO.actualizarUsuarioReservar(l, usuario);
        return lugarDAO.actualizarReservar(id);
    }

    public Lugar cambiarEstadoArrendar(Usuario usuario, Long id) {
        return lugarDAO.actualizarArrendar(id);
    }
}

