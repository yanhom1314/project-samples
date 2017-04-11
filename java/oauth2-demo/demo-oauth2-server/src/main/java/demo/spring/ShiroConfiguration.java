package demo.spring;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ShiroConfiguration {
    @Autowired
    private MyShiroRealm shiroRealm;

    @PostConstruct
    public void init() {
        DefaultSecurityManager securityManager = new DefaultSecurityManager(shiroRealm);

        DefaultSessionManager sessionManager = new DefaultSessionManager();
        sessionManager.setGlobalSessionTimeout(30000L);
        securityManager.setSessionManager(sessionManager);
        SecurityUtils.setSecurityManager(securityManager);
    }
}
