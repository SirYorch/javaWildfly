package ups.edu.parking.Objetos;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tarifas")
public class Tarifa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "descripcion", nullable = false, length = 50)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private double precio;

    public Tarifa() {}

    public Tarifa(String descripcion, double precio) {
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
