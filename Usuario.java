package ups.edu.parking;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@Entity
public class Usuario {

    @Id
    private String cedula;

    private String nombre;



    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCedula() { return cedula; }
    public void setCedula(String cedula) { this.cedula = cedula; }

}
