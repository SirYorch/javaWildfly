package ups.edu.parking.Objetos;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "espacios")
public class Espacio implements Serializable {

    private static final long serialVersionUID = 1L;

    public Espacio() {}

    public Espacio(int filas, int columnas ) {
        this.filas = filas;
        this.columnas = columnas;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "filas", nullable = false)
    private int filas;

    @Column(name = "columnas", nullable = false, length = 20)
    private int columnas;


    public int getFilas() {
        return filas;
    }

    public void setFilas(int filas) {
        this.filas = filas;
    }

    public int getColumnas() {
        return columnas;
    }

    public void setColumnas(int columnas) {
        this.columnas = columnas;
    }
}

