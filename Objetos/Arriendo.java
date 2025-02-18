package ups.edu.parking.Objetos;

import jakarta.persistence.*;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "arriendos")
public class Arriendo implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public Arriendo() {}

    public Arriendo(Long id, Date fechaInicio, Date fechaFin) {
        this.id = id;
        this.fecha_Inicio = fechaInicio;
        this.fecha_Fin = fechaFin;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha_inicio", nullable = false)
    private Date fecha_Inicio;

    @Column(name = "fecha_fin", nullable = false)
    private Date fecha_Fin;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getFechaInicio() {
        return fecha_Inicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fecha_Inicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fecha_Fin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fecha_Fin = fechaFin;
    }
}
