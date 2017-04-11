package demo.spring;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.Cache;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class CacheConfig {
    public static final String CODE_CACHE = "code-cache";

    @Bean
    public Cache codeCache() {
        return new GuavaCache(CODE_CACHE, CacheBuilder.newBuilder().maximumSize(3000)
                .expireAfterWrite(30, TimeUnit.MINUTES)
                .build());
    }
}
