package demo.freemarker;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;


public class FreemarkerObjectFilter  implements Filter {

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
