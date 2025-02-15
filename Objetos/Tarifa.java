package ups.edu.parking.Objetos;

import jakarta.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "tarifas")
public class Tarifa implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Column(name = "descripcion", nullable = false, length = 50)
//    private String descripcion;


    @Column(name = "desde", nullable = false)
    private int desde;


    @Column(name = "hasta", nullable = false)
    private int hasta;


    @Column(name = "precio", nullable = false)
    private double precio;

    public int getDesde() {
        return desde;
    }

    public void setDesde(int desde) {
        this.desde = desde;
    }

    public int getHasta() {
        return hasta;
    }

    public void setHasta(int hasta) {
        this.hasta = hasta;
    }

    public Tarifa() {}

    public Tarifa(int desde, int hasta, double precio) {
        this.desde = desde;
        this.hasta = hasta;
        this.precio = precio;
    }
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public double getPrecio() { return precio; }
    public void setPrecio(double precio) { this.precio = precio; }
}
