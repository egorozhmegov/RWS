package filter;

import controller.UserController;
import model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import service.interfaces.UserSessionService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Objects;

public class AuthenticateFilter implements Filter {

    @Autowired
    private UserSessionService userSessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(!Objects.equals(req.getRequestURI(),"/loginEmployee")
                && !Objects.equals(req.getRequestURI(),"/logoutEmployee")
                && !Objects.equals(req.getRequestURI(),"/registerEmployee")
                && !Objects.equals(req.getRequestURI(),"/client/getStations")
                && !Objects.equals(req.getRequestURI(),"/client/getSchedule")
                && !Objects.equals(req.getRequestURI(),"/client/searchTrains")
                && !Objects.equals(req.getRequestURI(),"/client/payment")){

            Cookie[] cookies = req.getCookies();

            boolean foundCookie = false;
            UserSession userSession = null;

            if (cookies != null) {
                for (Cookie ck : cookies) {
                    String sessionId = ck.getValue();
                    if(Objects.equals(UserController.COOKIE, ck.getName())){
                        userSession = userSessionService.getUserSession(sessionId);
                    }

                    if (userSession != null
                            && Objects.equals(sessionId, userSession.getSessionId())
                            && userSession.getExpiredTime().isAfter(LocalDateTime.now())){
                        req.getSession().setAttribute("userSession", userSession);
                        foundCookie = true; break;
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
        //destroy
    }
}
