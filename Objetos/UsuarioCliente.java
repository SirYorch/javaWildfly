package ups.edu.parking.Objetos;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENTE")
public class UsuarioCliente extends Usuario {

    @Column(name = "placa", nullable = true, length = 15)
    private String placa;

    public UsuarioCliente() {}

    public UsuarioCliente(Long id, String nombre, String telefono, String direccion, String cedula) {
        super(id, nombre, telefono, direccion, cedula);
    }
}

