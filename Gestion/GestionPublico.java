package ups.edu.parking.Gestion;


import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.PublicoDAO;
import ups.edu.parking.Objetos.Arriendo;
import ups.edu.parking.Objetos.Publico;

@ApplicationScoped
public class GestionPublico {


    @Inject
    private PublicoDAO publicoDAO;

    public Publico actualizarPublico(Publico publico) {
        return publicoDAO.actualizarPublico(publico);
    }

    public Publico getDatos(

    ) {
        return publicoDAO.buscarDatos();
    }

}
