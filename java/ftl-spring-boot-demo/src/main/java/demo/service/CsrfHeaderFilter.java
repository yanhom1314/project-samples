package demo.service;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.csrf.CsrfToken;

//@Component
//@Order(Ordered.HIGHEST_PRECEDENCE)
public class CsrfHeaderFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
			throws IOException, ServletException {
		try {
			HttpServletResponse response = (HttpServletResponse) servletResponse;
			HttpServletRequest request = (HttpServletRequest) servletRequest;

			response.setHeader("Access-Control-Allow-Origin", "*");
			response.setHeader("Access-Control-Allow-Methods", "POST, PUT, PATCH, GET, OPTIONS, DELETE");
			response.setHeader("Access-Control-Allow-Headers", "x-requested-with");
			response.setHeader("Access-Control-Max-Age", "3600");
			if (!request.getMethod().equals("OPTIONS")) {
				try {
					CsrfToken csrf = (CsrfToken) request.getAttribute(CsrfToken.class.getName());
					if (csrf != null) {
						Cookie cookie = new Cookie("XSRF-TOKEN", csrf.getToken());
						cookie.setPath("/");
						response.addCookie(cookie);
					}
					filterChain.doFilter(servletRequest, servletResponse);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void destroy() {

	}
}