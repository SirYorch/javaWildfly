package ups.edu.parking.Objetos;

import jakarta.persistence.*;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "tickets")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    public Ticket() {}

    public Ticket(Long id, Usuario usuario, Date fechaInicio, Date fechaFin, double precio) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuario = usuario;
        this.precio = precio;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(name = "fechaInicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fechaFin", nullable = false)
    private Date fechaFin;

    @Column(name = "precio", nullable = false)
    private double precio;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}

