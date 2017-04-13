package demo.oauth2.impl;

import demo.oauth2.ClientService;
import demo.oauth2.OAuthService;
import demo.spring.CacheConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class OAuthServiceImpl implements OAuthService {
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private ClientService clientService;

    public Cache cache() {
        return cacheManager.getCache(CacheConfig.CODE_OAUTH2);
    }

    @Override
    public void addAuthCode(String authCode, String username) {
        cache().put(authCode, username);
    }

    @Override
    public void addAccessToken(String accessToken, String username) {
        cache().put(accessToken, username);
    }

    @Override
    public boolean checkAuthCode(String authCode) {
        return cache().get(authCode) != null;
    }

    @Override
    public boolean checkAccessToken(String accessToken) {
        return cache().get(accessToken) != null;
    }

    @Override
    public String getUsernameByAuthCode(String authCode) {
        return (String) cache().get(authCode).get();
    }

    @Override
    public String getUsernameByAccessToken(String accessToken) {
        return (String) cache().get(accessToken).get();
    }

    @Override
    public long getExpireIn() {
        return 30L;
    }

    @Override
    public boolean checkClientId(String clientId) {
        return clientService.findByClientId(clientId) != null;
    }

    @Override
    public boolean checkClientSecret(String clientSecret) {
        return clientService.findByClientSecret(clientSecret) != null;
    }
}
