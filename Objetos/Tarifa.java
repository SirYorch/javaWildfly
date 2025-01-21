package ups.edu.parking.Objetos;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "tarifas")
public class Tarifa implements Serializable {

    public Tarifa() {}

    public Tarifa(int id, int tiempo, double precio) {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tiempo", nullable = false)
    private int tiempo;

    @Column(name = "precio", nullable = false)
    private double precio;

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
