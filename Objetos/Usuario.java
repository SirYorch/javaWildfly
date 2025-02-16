package ups.edu.parking.Objetos;

import jakarta.persistence.*;
import java.io.Serializable;


@Entity
@Table(name = "usuarios")
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;

    public Usuario() {}


    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Usuario(String uid, String nombre, String telefono, String direccion, String cedula, String placa, String correo, Reserva reserva, String stat) {
        this.uid = uid;
        this.nombre = nombre;
        this.telefono = telefono;
        this.direccion = direccion;
        this.cedula = cedula;
        this.placa = placa;
        this.correo= correo;
        this.stat = stat;
        this.reserva = reserva;
    }

    @Id
    @Column(name = "uid", unique = true, nullable = false, length = 50)
    private String uid;  // Ahora el UID es la clave primaria

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }

    @Column(name = "stat", nullable = false, length = 7)
    private String stat;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "telefono", nullable = false, length = 15)
    private String telefono;

    @Column(name = "direccion", nullable = false, length = 150)
    private String direccion;

    @Column(name = "cedula", nullable = false, unique = true, length = 10)
    private String cedula;

    public Reserva getReserva() {
        return reserva;
    }

    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Column(name = "correo", nullable = false, unique = true, length = 100)
    private String correo;

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = true)
    private Reserva reserva;


    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @OneToOne
    @JoinColumn(name = "lugar_id", nullable = true)
    private Lugar lugar;



    public Lugar getLugar() {
        return lugar;
    }

    public void setLugar(Lugar lugar) {
        this.lugar = lugar;
    }

    //Getters y Setters
    public String getUid() {
        return uid;
    }
    
    @Column(name = "placa", nullable = true, length = 15)
    private String placa;

    public String getPlaca() {
        return placa;
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
