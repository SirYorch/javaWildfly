package ups.edu.parking.Objetos;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "tipo_usuario")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UsuarioAdmin.class, name = "ADMIN"),
        @JsonSubTypes.Type(value = UsuarioCliente.class, name = "CLIENTE")
})
@JsonIgnoreProperties(ignoreUnknown = true) // Evita errores con campos desconocidos
@Entity
@Table(name = "usuarios")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "tipo_usuario", discriminatorType = DiscriminatorType.STRING)
public abstract class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    public Usuario() {}

    public Usuario(String uid, String nombre, String telefono, String direccion, String cedula) {
        this.uid = uid;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cedula = cedula;
    }

    @Id
    @Column(name = "uid", unique = true, nullable = false, length = 50)
    private String uid;  // Ahora el UID es la clave primaria

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @Column(name = "cedula", nullable = false, unique = true, length = 10)
    private String cedula;


    //Getters y Setters
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
    @Transient // No se almacena en la BD, pero se usa en la lógica
    public String getTipo_usuario() {
        if (this instanceof UsuarioAdmin) {
            return "ADMIN";
        } else if (this instanceof UsuarioCliente) {
            return "CLIENTE";
        }
        return "DESCONOCIDO";
    }


    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }
}
