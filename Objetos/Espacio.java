package ups.edu.parking.Objetos;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
@Table(name = "espacios")
public class Espacio implements Serializable {

    private static final long serialVersionUID = 1L;

    public Espacio() {}

    public Espacio(Long id, int numero, String letra, String estado) {
        this.id = id;
        this.numero = numero;
        this.letra = letra;
        this.estado = estado;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero", nullable = false)
    private int numero;

    @Column(name = "letra", nullable = false, length = 5)
    private String letra;

    @Column(name = "estado", nullable = false, length = 20)
    private String estado;

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}

