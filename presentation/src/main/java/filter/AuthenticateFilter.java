package filter;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

public class AuthenticateFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        final String RWS_COOKIE = "RWS_COOKIE";

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(!Objects.equals(req.getRequestURI(),"/loginEmployee")
                && !Objects.equals(req.getRequestURI(),"/logoutEmployee")
                && !Objects.equals(req.getRequestURI(),"/registerEmployee")
                && !Objects.equals(req.getRequestURI(),"/client/getStations")
                && !Objects.equals(req.getRequestURI(),"/client/getSchedule")){

            Cookie[] cookies = req.getCookies();
            boolean foundCookie = false;

            if (cookies != null) {
                for (Cookie ck : cookies) {
                    if (Objects.equals(ck.getName(), RWS_COOKIE)) {
                        foundCookie = true;
                        break;
                    }
                }
            }

            if(foundCookie) {
                chain.doFilter(req, res);
            } else {
                res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } else {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            chain.doFilter(req, res);
        }
    }

    @Override
    public void destroy() {

    }
}
