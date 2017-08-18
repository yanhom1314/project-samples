package demo.security;

import demo.service.CacheService;
import demo.util.IpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final Logger logger = LoggerFactory.getLogger(CustomUsernamePasswordAuthenticationFilter.class);
    @Autowired
    private MyAuthenticationSuccessHandler successHandler;
    @Autowired
    private MyAuthenticationFailureHandler failureHandler;
    @Autowired
    private CacheService cacheService;
    public static final Integer MAX_LOGIN_COUNT = 5;
    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        this.setAuthenticationSuccessHandler(successHandler);
        this.setAuthenticationFailureHandler(failureHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String ip = IpUtil.factory().remote(request);

        //times
        Integer count = cacheService.count(ip);
        if (count >= MAX_LOGIN_COUNT) {
            throw new AuthenticationFailedTimesException("FailedTimes");
        }
        //captcha
        String id = request.getParameter("captcha");
        HttpSession session = request.getSession();
        Object c_id = session.getAttribute("captcha");
        if (c_id != null && !id.equalsIgnoreCase((String) c_id)) {
            throw new AuthenticationCaptchaException("Please correctly CAPTCHA: " + c_id + ":" + id);
        }
        return super.attemptAuthentication(request, response);
    }
}