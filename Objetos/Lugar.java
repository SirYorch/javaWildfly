package ups.edu.parking.Objetos;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "lugares")
public class Lugar implements Serializable {

    private static final long serialVersionUID = 1L;


    public Lugar() {}

    public Lugar (Long id, String estado, String posicion) {
        this.id = id;
        this.estado = estado;
        this.posicion = posicion;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "estado", nullable = false, length = 100)
    private String estado;

    @Column(name = "posicion", nullable = false, length = 100)
    private String posicion;


    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }
    public void setPosicion(String posicion) {
        this.posicion = posicion;
    }

    public String getPosicion() {
        return posicion;
    }
}
