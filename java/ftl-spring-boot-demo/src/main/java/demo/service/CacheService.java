package demo.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

@Component
public class CacheService {

    @Cacheable(value = "LoginCount")
    public Integer count(String ip) {
        return 0;
    }

    @CachePut(value = "LoginCount")
    public Integer countPut(String ip, Integer c) {
        return c + 1;
    }
}
