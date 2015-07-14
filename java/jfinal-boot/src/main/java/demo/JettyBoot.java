package demo;

import com.jfinal.core.JFinalFilter;
import demo.web.DemoConfig;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;

import javax.servlet.DispatcherType;
import java.net.InetSocketAddress;
import java.util.EnumSet;

public class JettyBoot {
    public static final String JFINAL_FILTER_NAME = "jfinal";
    public static final String JFINAL_INIT_PARAM_NAME = "configClass";
    public static final String CONTEXT_PATH = "/demo";

    public static final int HTTP_PORT = 8080;
    public static final String HOSTNAME = "localhost";

    public static void main(String[] args) throws Exception {
        Server server = new Server(new InetSocketAddress(HOSTNAME, HTTP_PORT));

        //handler
        ServletContextHandler handler = new ServletContextHandler();
        handler.setContextPath(CONTEXT_PATH);

        FilterHolder filterHolder = new FilterHolder();
        filterHolder.setName(JFINAL_FILTER_NAME);
        filterHolder.setHeldClass(JFinalFilter.class);
        filterHolder.setInitParameter(JFINAL_INIT_PARAM_NAME, DemoConfig.class.getCanonicalName());
        handler.addFilter(filterHolder, "/*", EnumSet.allOf(DispatcherType.class));

        //server
        server.setHandler(handler);
        server.start();
        server.join();
    }
}
