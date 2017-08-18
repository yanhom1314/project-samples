package demo.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MyAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {
    public static final Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);

    @PostConstruct
    public void init() {
        this.setDefaultTargetUrl("/admin/info");
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//        System.out.println("Success:" + operatorRepository);
//        try {
//            Operator operator = operatorRepository.findByUsername(authentication.getName());
//            operator.setLoginIp(IpUtil.factory().remote(request));
//            operatorRepository.save(operator);
//
//            Log log = new Log();
//            log.setFlag(FlagsConstants.LOGIN);
//            log.setContent(operator.getUsername());
//            logRepository.save(log);
//        } catch (Exception e) {
//            e.printStackTrace();
//            logger.error(e.getMessage());
//        }
        System.out.println("refer:" + request.getHeader(HttpHeaders.REFERER));
        super.onAuthenticationSuccess(request, response, authentication);
    }
}
