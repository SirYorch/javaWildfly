package ups.edu.parking.Objetos;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("ADMIN")
public class UsuarioAdmin extends Usuario {

    public UsuarioAdmin() {}

    public UsuarioAdmin(Long id, String nombre, String telefono, String direccion, String cedula, String placa) {
        super(id, nombre, telefono, direccion, cedula,placa);
    }
}
