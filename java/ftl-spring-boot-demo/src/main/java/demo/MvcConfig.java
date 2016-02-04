package demo;

import freemarker.template.TemplateException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    public static final String DEMO_FREEMARKER_PATH = "demo.freemarker.path";
    private File TEMPLATE_DEV_PATH;

    @PostConstruct
    public void init() {
        if (System.getProperty(DEMO_FREEMARKER_PATH) != null) {
            try {
                TEMPLATE_DEV_PATH = new File(System.getProperty("user.dir"), System.getProperty(DEMO_FREEMARKER_PATH));
                System.out.println("TEMPLATE_DEV_PATH:" + TEMPLATE_DEV_PATH.toURI().toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Bean
    public ViewResolver viewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setCache(true);
        if (TEMPLATE_DEV_PATH != null) resolver.setCache(false);
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        return resolver;
    }

    @Bean
    public FreeMarkerConfigurer freemarkerConfig() throws IOException, TemplateException {
        FreeMarkerConfigurationFactory factory = new FreeMarkerConfigurationFactory();
        if (TEMPLATE_DEV_PATH != null) factory.setTemplateLoaderPaths(new File(TEMPLATE_DEV_PATH, "templates").toURI().toString());
        else factory.setTemplateLoaderPaths("classpath:/templates/");
        factory.setDefaultEncoding("UTF-8");
        FreeMarkerConfigurer result = new FreeMarkerConfigurer();
        result.setConfiguration(factory.createConfiguration());
        return result;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login");
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.favorPathExtension(true).
                ignoreAcceptHeader(true).
                useJaf(false).
                defaultContentType(MediaType.APPLICATION_JSON).
                mediaType("xml", MediaType.APPLICATION_XML).
                mediaType("json", MediaType.APPLICATION_JSON);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (TEMPLATE_DEV_PATH != null) registry.addResourceHandler("/resources/**").addResourceLocations(new File(TEMPLATE_DEV_PATH, "static/resources").toURI().toString());
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
