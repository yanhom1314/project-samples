import com.google.inject.AbstractModule;
import config.SpringDataJpaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class ModuleJava extends AbstractModule {
    @Override
    protected void configure() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);
        System.out.println("conf/application.conf:[play.modules.enabled += \"ModuleJava\".");
        try {
            Enumeration<URL> resource = this.getClass().getClassLoader().getResources("entities");

            while (resource.hasMoreElements()) {
                URL u = resource.nextElement();
                File dir = new File(u.getFile());
                List<String> list = new ArrayList<>();
                loop(dir, list);
                list.forEach(c -> {
                    try {
                        Class t = Class.forName(c);
                        bind(t).toInstance(ctx.getBean(t));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop(File dir, List<String> list) {
        Arrays.stream(dir.listFiles(f -> f.isDirectory() || (f.getName().indexOf("Repository") > 0 && f.getName().endsWith(".class")))).forEach(f -> {
            if (f.isFile()) list.add(dir.getName() + "." + f.getName().replace(".class", ""));
            else loop(f, list);
        });
    }
}
