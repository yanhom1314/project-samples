import com.google.inject.AbstractModule;
import config.SpringDataJpaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Collections;
import java.util.jar.JarFile;
import java.util.stream.Stream;

public class ModuleJava extends AbstractModule {
    public static final String PKG_PREFIX_NAME = "entities";
    public static final String REPOSITORY_SUFFIX_NAME = "Repository";
    ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);

    @Override
    protected void configure() {
        System.out.println("conf/application.conf:[play.modules.enabled += \"ModuleJava\".");
        Stream.of(ctx.getBeanDefinitionNames()).filter(t -> t.endsWith(REPOSITORY_SUFFIX_NAME)).forEach(n -> {
            try {
                Class c = Class.forName(PKG_PREFIX_NAME + "." + n);
                bind(c).toInstance(ctx.getBean(c));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        //scanJarFile();
    }


    public void scanJarFile() {
        try {
            URL u = this.getClass().getClassLoader().getResource("entities");
            URLConnection conn = u.openConnection();

            if (conn instanceof JarURLConnection) {
                try (JarFile jar = ((JarURLConnection) conn).getJarFile()) {
                    Collections.list(jar.entries()).stream().filter(e -> e.getName().indexOf(PKG_PREFIX_NAME) >= 0).forEach(entry -> {
                        String name = entry.getName();
                        try {
                            Class t = Class.forName(name.replaceAll("/", ".").replace(".class", ""));
                            bind(t).toInstance(ctx.getBean(t));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else loop(new File(u.getFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loop(File dir) {
        Arrays.stream(dir.listFiles(f -> f.isDirectory() || (f.isFile() && f.getName().endsWith(REPOSITORY_SUFFIX_NAME + ".class")))).forEach(f -> {
            if (f.isFile()) {
                try {
                    Class t = Class.forName(dir.getName() + "." + f.getName().replace(".class", ""));
                    bind(t).toInstance(ctx.getBean(t));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else loop(f);
        });
    }
}
