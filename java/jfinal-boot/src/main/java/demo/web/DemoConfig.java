package demo.web;

import com.jfinal.config.*;
import com.jfinal.ext.handler.ContextPathHandler;
import ext.beetl.DemoBeetlRenderFactory;
import ext.beetl.JarResourceLoader;
import org.beetl.core.GroupTemplate;
import org.beetl.ext.jfinal.BeetlRenderFactory;

public class DemoConfig extends JFinalConfig {
    @Override
    public void configConstant(Constants me) {
        me.setDevMode(true);
        BeetlRenderFactory beetlRenderFactory = new DemoBeetlRenderFactory(new JarResourceLoader());
        me.setMainRenderFactory(beetlRenderFactory);

        //获取GroupTemplate模板，可以设置共享变量操作
        GroupTemplate groupTemplate = DemoBeetlRenderFactory.groupTemplate;
        //do something
    }

    @Override
    public void configRoute(Routes me) {
        me.add("/hello", HelloController.class);
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
    }
}
