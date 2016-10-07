package demo.web;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.BeanParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloRequest {
    @Path("{id}")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String param(@BeanParam MyBeanParam beanParam) {
        System.out.println("id:" + beanParam.getId() + " header:" + beanParam.getHeaderParam() + " q:" + beanParam.getQueryParam());
        return beanParam.toString();
    }
}
