package io.demo.example

import org.springframework.boot.web.servlet.ServletContextInitializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import java.util.*
import javax.servlet.SessionTrackingMode

@Configuration
class WebConfig : WebMvcConfigurer {

    @Bean
    open fun servletContextInitializer() = ServletContextInitializer {
        ctx ->
        ctx.setSessionTrackingModes(Collections.singleton(SessionTrackingMode.COOKIE))
    }


    override fun configureContentNegotiation(configurer: ContentNegotiationConfigurer?) {
        configurer?.let { c ->
            c.favorPathExtension(true).ignoreAcceptHeader(true)
                    .useRegisteredExtensionsOnly(true)
                    .defaultContentType(MediaType.APPLICATION_JSON_UTF8)
                    .mediaType("xml", MediaType.APPLICATION_XML)
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry?) {
        registry?.let { r ->
            r.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/")
        }
    }
}