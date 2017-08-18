package demo.security;

import demo.service.CacheService;
import demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.LocaleResolver;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static demo.security.CustomUsernamePasswordAuthenticationFilter.MAX_LOGIN_COUNT;

@Component
public class MyAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    public static final Logger logger = LoggerFactory.getLogger(MyAuthenticationFailureHandler.class);
    @Autowired
    private MessageSource messageSource;
    @Autowired
    private LocaleResolver localeResolver;
    @Autowired
    private CacheService cacheService;

    @PostConstruct
    public void init() {
        //this.setUseForward(true);
    }

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        if (exception instanceof AuthenticationFailedTimesException) {
            this.setDefaultFailureUrl("/login?over");
        } else if (exception instanceof AuthenticationCaptchaException) {
            this.setDefaultFailureUrl("/login?captcha");
        } else {
            this.setDefaultFailureUrl("/login?error");
        }
        //message
        String ip = IpUtil.factory().remote(request);
        Integer count = cacheService.count(ip);
        cacheService.countPut(ip, count);

        HttpSession session = request.getSession(false);
        session.setAttribute("_s_login_time", messageSource.getMessage("login.failed.count", new Object[]{MAX_LOGIN_COUNT - count}, localeResolver.resolveLocale(request)));
        super.onAuthenticationFailure(request, response, exception);
    }
}
