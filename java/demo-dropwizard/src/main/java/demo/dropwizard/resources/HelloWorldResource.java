package demo.dropwizard.resources;

import com.codahale.metrics.annotation.Timed;
import demo.dropwizard.views.SayingView;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Path("/hello")
public class HelloWorldResource {
    private final String template;
    private final String defaultName;
    private final AtomicLong counter;

    public HelloWorldResource(String template, String defaultName) {
        this.template = template;
        this.defaultName = defaultName;
        this.counter = new AtomicLong();
    }

    @Path("/json")
    @GET
    @Timed
    @Produces(MediaType.APPLICATION_JSON)
    public Saying sayHello(@QueryParam("name") Optional<String> name) {
        final String value = String.format(template, name.orElse(defaultName));
        return new Saying(counter.incrementAndGet(), value);
    }

    @GET
    @Path("/say/{id}")
    @Produces(MediaType.TEXT_HTML)
    public SayingView getPerson(@PathParam("id") Long id) {
        final String value = String.format(template, id);
        return new SayingView(new Saying(id, value));
    }
}