package ups.edu.parking.Objetos;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "publico")
public class Publico implements Serializable {
    @Id
    private Long id;

    //a pesar de crearse una tabla solo se accedera al primer elemento tanto para lectura como para escritura.

    @Column(name = "Entrada1", nullable = false)
    private Date fechaInicio1; //Fecha usada para lunes a viernes
    @Column(name = "Salida1", nullable = false)
    private Date fechaFin1;
    @Column(name = "Entrada2", nullable = false)
    private Date fechaInicio2;  // fecha usada para sabados y domingos
    @Column(name = "Salida2", nullable = false)
    private Date fechaFin2;

    @Column(name = "motd", nullable = false, length = 500)
    private String motd;

    public Date getFechaInicio1() {
        return fechaInicio1;
    }

    public void setFechaInicio1(Date fechaInicio1) {
        this.fechaInicio1 = fechaInicio1;
    }

    public Date getFechaFin1() {
        return fechaFin1;
    }

    public void setFechaFin1(Date fechaFin1) {
        this.fechaFin1 = fechaFin1;
    }

    public Date getFechaInicio2() {
        return fechaInicio2;
    }

    public void setFechaInicio2(Date fechaInicio2) {
        this.fechaInicio2 = fechaInicio2;
    }

    public Date getFechaFin2() {
        return fechaFin2;
    }

    public void setFechaFin2(Date fechaFin2) {
        this.fechaFin2 = fechaFin2;
    }

    public String getMotd() {
        return motd;
    }

    public void setMotd(String motd) {
        this.motd = motd;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
