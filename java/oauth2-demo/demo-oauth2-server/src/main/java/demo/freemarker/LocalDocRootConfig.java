package demo.freemarker;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import java.io.File;

public class LocalDocRootConfig {
    public static final String LOCAL_DOC_ROOT = "local.doc.root";
    public static final String TEMPLATE_PREFIX = "templates.dir";
    private static File DIR_ROOT;
    private static String DIR_TEMPLATE;

    static {
        try {
            DIR_TEMPLATE = System.getProperty(TEMPLATE_PREFIX) != null ? System.getProperty(TEMPLATE_PREFIX) : "templates";
            if (System.getProperty(LOCAL_DOC_ROOT) != null) {
                DIR_ROOT = new File(System.getProperty(LOCAL_DOC_ROOT));
                System.out.printf("#%s:[%s]#\n", LOCAL_DOC_ROOT, DIR_ROOT.toURI().toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//
//    public static void themleafConfiguration(ApplicationContext context) {
//        if (DIR_ROOT != null) {
//            try {
//                SpringTemplateEngine templateEngine = context.getBean(SpringTemplateEngine.class);
//                templateEngine.getTemplateResolvers().forEach(t -> {
//                    if (t instanceof ITemplateResolver) {
//                        SpringResourceTemplateResolver resolver = (SpringResourceTemplateResolver) t;
//                        resolver.setCacheable(false);
//                        resolver.setPrefix(new File(DIR_ROOT, DIR_TEMPLATE).toURI().toString());
//                    }
//                });
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//    }

    public static void freemarkerConfiguration(ApplicationContext context) {
        if (DIR_ROOT != null) {
            try {
                FreeMarkerViewResolver viewResolver = context.getBean(FreeMarkerViewResolver.class);
                FreeMarkerConfigurer configurer = context.getBean(FreeMarkerConfigurer.class);

                viewResolver.setCache(false);
                configurer.getConfiguration().setDirectoryForTemplateLoading(new File(DIR_ROOT, DIR_TEMPLATE));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static Boolean isLocal() {
        return System.getProperty(LOCAL_DOC_ROOT) != null;
    }

    public static String template() {
        return DIR_TEMPLATE;
    }

    public static File root() {
        return DIR_ROOT;
    }
}