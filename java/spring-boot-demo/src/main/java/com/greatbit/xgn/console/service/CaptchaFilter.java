package com.greatbit.xgn.console.service;

import com.greatbit.xgn.console.web.CaptchaController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CaptchaFilter implements Filter {
    public static final Logger logger = LoggerFactory.getLogger(CaptchaFilter.class);
    private String captchaParam = "j_captcha";
    private String loginUrl = "/login";

    public CaptchaFilter() {

    }

    public CaptchaFilter(String loginUrl) {
        this.loginUrl = loginUrl;
    }

    public CaptchaFilter(String captchaParam, String loginUrl) {
        this.captchaParam = captchaParam;
        this.loginUrl = loginUrl;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {
        this.captchaParam = null;
        this.loginUrl = null;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) req;
            HttpServletResponse response = (HttpServletResponse) res;
            HttpSession session = request.getSession(false);
            if (session != null && request.getRequestURI().endsWith(loginUrl) && "POST".equalsIgnoreCase(request.getMethod())) {
                if (session.getAttribute(CaptchaController.SESSION_LOGIN_CAPTCHA_KEY) != null && request.getParameterMap().containsKey(captchaParam)) {
                    String sid = (String) session.getAttribute(CaptchaController.SESSION_LOGIN_CAPTCHA_KEY);
                    String cid = request.getParameter(captchaParam);
                    if (!sid.equalsIgnoreCase(cid)) {
                        response.sendRedirect(request.getContextPath() + loginUrl + "?captcha");
                        return;
                    }
                }
            }
            chain.doFilter(req, res);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}