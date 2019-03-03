package graphrecommender;



import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;

@Path("/recommendation")
public class RecommendationResource {
    @GET
    @Path("/ping")
    public Response ping() {
        return Response.ok().entity("Service online").build();
    }

    @POST
    @Path("/post/")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response post(Request request) throws IOException {
        Grafo grafo= new Grafo(request);
        HashMap<String, Double> map = grafo.Pagerank();
        return Response.ok().entity(new Return(map)).build();
    }
}