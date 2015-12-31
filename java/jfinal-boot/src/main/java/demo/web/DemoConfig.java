package demo.web;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import com.jfinal.render.FreeMarkerRender;

public class DemoConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/hello", HelloController.class);
        me.add("/index", IndexController.class);
    }

    @Override
    public void configPlugin(Plugins me) {
    }

    @Override
    public void configInterceptor(Interceptors me) {

    }

    @Override
    public void configHandler(Handlers me) {
        me.add(new ContextPathHandler("demo"));
        me.getHandlerList().stream().forEach(a -> System.out.println("handler:" + a.getClass().getName()));
    }

    @Override
    public void afterJFinalStart() {
        super.afterJFinalStart();
        FreeMarkerRender.getConfiguration().setClassLoaderForTemplateLoading(DemoConfig.class.getClassLoader(), "/views");
    }
}
