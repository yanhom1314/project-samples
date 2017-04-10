package demo.service;

import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class FreemarkerObjectFilter implements Filter {
    public static final String NAME_REQUEST = "request";
    public static final String NAME_SESSION = "session";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = ((HttpServletRequest) req);
        HttpSession session = request.getSession(true);

        req.setAttribute(NAME_REQUEST, request);

        Map<String,Object> map = new HashMap<>();

        Enumeration<String> keys  = session.getAttributeNames();
        while(keys.hasMoreElements()){
            String key = keys.nextElement();
            map.put(key,session.getAttribute(key));
        }

        req.setAttribute(NAME_SESSION, map);

        filterChain.doFilter(req, resp);
    }

    @Override
    public void destroy() {

    }
}
