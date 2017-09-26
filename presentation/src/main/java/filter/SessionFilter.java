package filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(res.getStatus() >= 200 && res.getStatus() < 300) {
            Cookie cookie = new Cookie("RWS_COOKIE", "1111");
            cookie.setMaxAge(Integer.MAX_VALUE);
            res.addCookie(cookie);
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {

    }
}
