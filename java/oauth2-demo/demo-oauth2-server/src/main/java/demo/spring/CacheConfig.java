package demo.spring;

import com.google.common.cache.CacheBuilder;
import org.springframework.cache.CacheManager;
import org.springframework.cache.guava.GuavaCache;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {
    public static final String CODE_OAUTH2 = "code-oauth2";
    public static final String CODE_ALL_USER = "code-getAllUser-user";

    @Bean
    public CacheManager cacheManager() {
        SimpleCacheManager simpleCacheManager = new SimpleCacheManager();
        GuavaCache oauth2Cache = new GuavaCache(CODE_OAUTH2,
                CacheBuilder.newBuilder()
                        .maximumSize(60000)
                        .expireAfterWrite(30, TimeUnit.SECONDS).build());
        GuavaCache allUserCache = new GuavaCache(CODE_ALL_USER,
                CacheBuilder.newBuilder()
                        .maximumSize(60000)
                        .expireAfterWrite(30, TimeUnit.MINUTES)
                        .build());
        simpleCacheManager.setCaches(Arrays.asList(oauth2Cache, allUserCache));
        return simpleCacheManager;
    }
}
