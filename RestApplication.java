package ups.edu.parking;

import jakarta.ws.rs.ApplicationPath;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.core.Application;
import jakarta.ws.rs.ext.Provider;

import java.io.IOException;

@ApplicationPath("/api")
public class RestApplication extends Application {
}

