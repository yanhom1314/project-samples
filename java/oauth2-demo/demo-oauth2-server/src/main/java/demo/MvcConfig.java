package demo;

import demo.freemarker.LocalDocRootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ApplicationContext context;

    @PostConstruct
    public void init() {
        LocalDocRootConfig.freemarkerConfiguration(context);
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
                mediaType("xml", MediaType.APPLICATION_XML);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (LocalDocRootConfig.isLocal()) registry.addResourceHandler("/resources/**").addResourceLocations(new File(LocalDocRootConfig.root(), "static/resources").toURI().toString());
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
}
