package graphrecommender;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    private static String url;

    public static HttpServer startServer() {

        // create a resource config that scans for JAX-RS resources and providers
        // in test package
        final ResourceConfig rc = new ResourceConfig().packages("graphrecommender");

        String host = "0.0.0.0";
        // Base URI the Grizzly HTTP server will listen on
        final String BASE_URI = "http://"+host+":"+(System.getenv("PORT")!=null?System.getenv("PORT"):"8080")+"/";
        url=BASE_URI;

        // create and start a new instance of grizzly http server
        // exposing the Jersey application at BASE_URI
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }


    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();

        System.out.println(String.format("Jersey app started with WADL available at "
                + "%sapplication.wadl", url));
    }
}

