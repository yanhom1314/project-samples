package util;

import org.thymeleaf.spring4.SpringTemplateEngine;
import org.thymeleaf.templateresolver.TemplateResolver;

import java.io.File;

public class LocalDocRootConfig {

    public static final String LOCAL_DOC_ROOT = "local.doc.root";

    public static final String TEMPLATE_PREFIX = "thymeleaf.templates.dir";

    public static File localDocRootForThymeleaf(SpringTemplateEngine templateEngine) {
        File TEMPLATE_DEV_PATH = null;
        if (System.getProperty(LOCAL_DOC_ROOT) != null) {
            try {
                TEMPLATE_DEV_PATH = new File(System.getProperty("user.dir"), System.getProperty(LOCAL_DOC_ROOT));
                final File base = TEMPLATE_DEV_PATH;

                templateEngine.getTemplateResolvers().forEach(t -> {
                    if (t instanceof TemplateResolver) {
                        System.out.println(">>thymeleaf template resolver setting<<");
                        TemplateResolver resolver = (TemplateResolver) t;
                        resolver.setCacheable(false);
                        resolver.setPrefix(new File(base, System.getProperty(TEMPLATE_PREFIX) != null ? System.getProperty(TEMPLATE_PREFIX) : "templates").toURI().toString());
                    }
                });
                System.out.printf("#local.doc.root:[%s]#\n", TEMPLATE_DEV_PATH.toURI().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return TEMPLATE_DEV_PATH;
    }


    public static Boolean isLocal() {
        return System.getProperty(LOCAL_DOC_ROOT) != null;
    }

    public static String templatePath() {
        if (System.getProperty(LOCAL_DOC_ROOT) != null) return new File(baseDir(), "templates").getAbsolutePath();
        else return "classpath:/templates/";
    }

    public static File baseDir() {
        return new File(System.getProperty("user.dir"), System.getProperty(LOCAL_DOC_ROOT));
    }
}