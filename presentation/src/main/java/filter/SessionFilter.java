package filter;

import controller.UserController;
import model.User;
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

/**
 * Session filter.
 */
public class SessionFilter implements Filter {

    @Autowired
    private UserSessionService userSessionService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(
                this, filterConfig.getServletContext());
    }

    /**
     * Update cookie.
     *
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     * @throws IOException exception
     * @throws ServletException exception
     */
    @Override
    @SuppressWarnings("squid:S2092")
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(res.getStatus() >= 200 && res.getStatus() < 300) {
            User user = (User) req.getSession().getAttribute("user");
            UserSession userSession = (UserSession) req.getSession().getAttribute("userSession");
            if(user != null && userSession != null){
                String sessionId = UserController.generateSessionId(user.getLogin(), user.getPassword());
                userSession.setSessionId(sessionId);
                userSession.setExpiredTime(LocalDateTime.now().plusMinutes(5));
                userSessionService.updateUserSession(userSession);
                Cookie cookie = new Cookie(UserController.COOKIE, sessionId);
                cookie.setMaxAge(300);
                res.addCookie(cookie);
            }
        }

        chain.doFilter(req, res);
    }

    @Override
    public void destroy() {
        //destroy
    }
}
