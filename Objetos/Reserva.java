package ups.edu.parking.Objetos;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "reservas")
public class Reserva implements Serializable {

    private static final long serialVersionUID = 1L;

    public Reserva() {}

    public Reserva(Long id, Ticket ticket, Date inicio,Lugar lugar) {
        this.id = id;
        this.ticket = ticket;
        this.inicio = inicio;
        this.lugar = lugar;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "ticket_id", nullable = false)
    private Ticket ticket;

    @Column(name = "inicio", nullable = false)
    private Date inicio;

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    @ManyToOne
    @JoinColumn(name = "lugar_id", nullable = false)
    private Lugar lugar;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }
}

