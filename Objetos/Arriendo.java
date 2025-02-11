package ups.edu.parking.Objetos;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "arriendos")
public class Arriendo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Arriendo() {}

    public Arriendo(Long id, Date fechaInicio, Date fechaFin, UsuarioCliente usuario, Lugar lugar) {
        this.id = id;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.usuario = usuario;
        this.lugar = lugar;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio", nullable = false)
    private Date fechaInicio;

    @Column(name = "fecha_fin", nullable = false)
    private Date fechaFin;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuarioCliente usuario;

    @OneToOne
    @JoinColumn(name = "lugar_id", nullable = false)
    private Lugar lugar;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public UsuarioCliente getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioCliente usuario) {
        this.usuario = usuario;
    }

    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }
}
