package demo;

import demo.config.MyConfig;
import demo.freemarker.LocalDocRootConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletContextInitializer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.annotation.PostConstruct;
import javax.servlet.ServletContext;
import javax.servlet.SessionTrackingMode;
import java.io.File;
import java.util.Collections;
import java.util.Locale;

@Configuration
@EnableConfigurationProperties(MyConfig.class)
public class MvcConfig extends WebMvcConfigurerAdapter {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private ServletContext sct;
    @Autowired
    private MyConfig mc;

    @PostConstruct
    public void init() {
        LocalDocRootConfig.freemarkerConfiguration(context);
    }

    @Bean
    public ServletContextInitializer servletContextInitializer() {
        return sc -> {
            sc.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE));//remove JSESSIONID from url
            sc.getSessionCookieConfig().setHttpOnly(true); //set Cookie HttpOnly,and use also server.session.cookie.http-only:false
        };
    }

    @Bean
    public LocaleResolver localeResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(Locale.US);
        resolver.setCookieName("locale");
        resolver.setCookiePath(sct.getContextPath());
        resolver.setCookieMaxAge(mc.getExpired());
        return resolver;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());

        super.addInterceptors(registry);
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
