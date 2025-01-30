package ups.edu.parking.Objetos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class UsuarioAdmin extends Usuario {

    public UsuarioAdmin() {}

    public UsuarioAdmin(String uid, String nombre, String telefono, String direccion, String cedula) {
        super(uid, nombre, telefono, direccion, cedula);
    }
}
