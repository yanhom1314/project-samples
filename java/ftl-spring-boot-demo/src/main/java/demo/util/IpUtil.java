package demo.util;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {
    private static final ThreadLocal<IpUtil> LOCAL = new ThreadLocal<>();

    private IpUtil() {
    }

    public static IpUtil factory() {
        IpUtil t = LOCAL.get();
        if (t == null) {
            t = new IpUtil();
            LOCAL.set(t);
        }
        return t;
    }

    public String remote(HttpServletRequest request) {
        String a = request.getHeader("X-FORWARD-FOR");
        return a != null ? a : request.getRemoteAddr();
    }
}
