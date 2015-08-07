package ${package};

import com.jfinal.core.JFinalFilter;
import demo.web.DemoConfig;
import io.undertow.Handlers;
import io.undertow.Undertow;
import io.undertow.server.handlers.PathHandler;
import io.undertow.servlet.Servlets;
import io.undertow.servlet.api.DeploymentInfo;
import io.undertow.servlet.api.DeploymentManager;

import javax.servlet.DispatcherType;

public class UndertowBoot {
    public static final String JFINAL_FILTER_NAME = "jfinal";
    public static final String JFINAL_INIT_PARAM_NAME = "configClass";
    public static final String CONTEXT_PATH = "/demo";

    public static final int HTTP_PORT = 8080;
    public static final String HOSTNAME = "localhost";

    public static void main(String[] args) throws Exception {
        DeploymentInfo servletBuilder = Servlets.deployment()
                .setClassLoader(UndertowBoot.class.getClassLoader())
                .setContextPath(CONTEXT_PATH)
                .setDeploymentName(CONTEXT_PATH.substring(1) + ".war")
                .addFilters(Servlets.filter(JFINAL_FILTER_NAME, JFinalFilter.class).addInitParam(JFINAL_INIT_PARAM_NAME, DemoConfig.class.getCanonicalName()))
                .addFilterUrlMapping(JFINAL_FILTER_NAME, "/*", DispatcherType.REQUEST);

        DeploymentManager manager = Servlets.defaultContainer().addDeployment(servletBuilder);
        manager.deploy();
        PathHandler path = Handlers.path(Handlers.redirect(CONTEXT_PATH))
                .addPrefixPath(CONTEXT_PATH, manager.start());

        Undertow server = Undertow.builder()
                .addHttpListener(HTTP_PORT, HOSTNAME)
                .setHandler(path)
                .build();
        server.start();
    }
}
