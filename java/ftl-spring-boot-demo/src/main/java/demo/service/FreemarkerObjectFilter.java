package demo.service;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
public class FreemarkerObjectFilter implements Filter {
    public static final String NAME_PAGE_CONTEXT = "pageContext";
    public static final String NAME_SERVLET_CONTEXT = "servletContext";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        ServletContext ctx = servletRequest.getServletContext();
        HttpServletRequest req = ((HttpServletRequest) servletRequest);

        servletRequest.setAttribute(NAME_SERVLET_CONTEXT, ctx);
        servletRequest.setAttribute(NAME_PAGE_CONTEXT, req);
        
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
