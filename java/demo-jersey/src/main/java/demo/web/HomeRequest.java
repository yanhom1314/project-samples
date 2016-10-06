package demo.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/home")
public class HomeRequest {

    @Path("/user/{username}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String user(@PathParam("username") String username) {
        System.out.println("HomeRequest is Working???");
        return username + "!!!!";
    }


    @Path("/check/{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String example(@PathParam("id") String id) {
        System.out.println("HomeRequest is Working???");
        return id + "$$$$";
    }


//    @Path("/bean/{id]")
//    @POST
//    @Produces(MediaType.TEXT_PLAIN)
//    public String param(@BeanParam MyBeanParam beanParam) {
//        System.out.println("id:" + beanParam.getId() + " header:" + beanParam.getHeaderParam() + " q:" + beanParam.getQueryParam());
//        return beanParam.toString();
//    }
}
