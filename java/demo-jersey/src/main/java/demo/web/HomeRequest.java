package demo.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("home")
public class HomeRequest {

    @Path("{username}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String example(@PathParam("username") String username) {
        System.out.println("HomeRequest is Working???");
        return username + "!!!!";
    }
}
