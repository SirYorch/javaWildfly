package ups.edu.parking;

import jakarta.persistence.*;

@Entity
public class Telefono {
    @Id
    private String numero;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    public String getNumero() { return numero; }
    public void setNumero(String numero) { this.numero = numero; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }


}

