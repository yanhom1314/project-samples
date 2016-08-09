import com.google.inject.AbstractModule;
import config.SpringDataJpaConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ModuleJava extends AbstractModule {
    @Override
    protected void configure() {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(SpringDataJpaConfig.class);
        System.out.println("conf/application.conf:[play.modules.enabled += \"ModuleJava\".");
        try {
            Enumeration<URL> resources = this.getClass().getClassLoader().getResources("entities/*");
            System.out.println("resources:" + resources.hasMoreElements());
            while (resources.hasMoreElements()) {
                URL u = resources.nextElement();
                System.out.println(u.getFile());
            }

            URL u = this.getClass().getClassLoader().getResource("entities");
            URLConnection conn = u.openConnection();

            if (conn instanceof JarURLConnection) {
                try (JarFile jar = ((JarURLConnection) conn).getJarFile()) {
                    Enumeration<JarEntry> entries = jar.entries();
                    while (entries.hasMoreElements()) {
                        String entry = entries.nextElement().getName();
                        if (entry.indexOf("entities") >= 0) {
                            try {
                                System.out.println("name:" + entry.replaceAll("/", ".").replace(".class", ""));
                                Class t = Class.forName(entry.replaceAll("/", ".").replace(".class", ""));
                                bind(t).toInstance(ctx.getBean(t));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else loop(new File(u.getFile()), ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loop(File dir, ApplicationContext ctx) {
        Arrays.stream(dir.listFiles(f -> f.exists() && (f.isDirectory() || (f.getName().indexOf("Repository") > 0 && f.getName().endsWith(".class"))))).forEach(f -> {
            if (f.isFile()) {
                try {
                    Class t = Class.forName(dir.getName() + "." + f.getName().replace(".class", ""));
                    bind(t).toInstance(ctx.getBean(t));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else loop(f, ctx);
        });
    }
}
