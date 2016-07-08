package com.greatbit.xgn.console;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.thymeleaf.spring4.SpringTemplateEngine;
import util.LocalDocRootConfig;

import javax.annotation.PostConstruct;
import java.io.File;

@Configuration
public class MvcConfig extends WebMvcConfigurerAdapter {
    File TEMPLATE_DEV_PATH;
    @Autowired
    private SpringTemplateEngine templateEngine;

    @PostConstruct
    public void init() {
        TEMPLATE_DEV_PATH = LocalDocRootConfig.localDocRootForThymeleaf(templateEngine);
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (LocalDocRootConfig.isLocal()) registry.addResourceHandler("/resources/**").addResourceLocations(new File(LocalDocRootConfig.baseDir(), "static/resources").toURI().toString());
        else registry.addResourceHandler("/resources/**").addResourceLocations("classpath:/static/resources/");

        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/logout").setViewName("login");
    }

    @Bean
    public MessageSource messageSource() {
        final ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("classpath:/messages");
        messageSource.setFallbackToSystemLocale(false);
        messageSource.setCacheSeconds(0);

        return messageSource;
    }
}
