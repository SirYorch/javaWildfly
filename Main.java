package ups.edu.parking;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import jakarta.inject.Inject;
import ups.edu.parking.DAO.UsuarioDAO;

@Singleton
@Startup
public class Main {

    @Inject
    UsuarioDAO usuarioDAO;


    @PostConstruct
    public void init() {
        usuarioDAO.listarUsuarios().forEach(System.out::println);
        System.out.println("HolaMundo");
        usuarioDAO.listarUsuarios().forEach(System.out::println);
    }
}