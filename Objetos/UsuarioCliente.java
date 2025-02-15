package ups.edu.parking.Objetos;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("CLIENTE")
public class UsuarioCliente extends Usuario {
    @JsonProperty("placa")
    @Column(name = "placa", nullable = true, length = 15)
    private String placa;

    public UsuarioCliente() {}

    public UsuarioCliente(String uid, String nombre, String telefono, String direccion, String cedula, String placa,String correo,boolean reservado) {
        super(uid, nombre, telefono, direccion, cedula,correo,reservado);
        this.placa = placa;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
