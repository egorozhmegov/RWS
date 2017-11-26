package filter;

import controller.UserController;
import model.User;
import model.UserSession;
import org.springframework.beans.factory.annotation.Autowired;
import service.interfaces.UserService;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class SessionFilter implements Filter {

    @Autowired
    private UserService userService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //init filter config
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        if(res.getStatus() >= 200 && res.getStatus() < 300) {
            User user = (User) req.getAttribute("user");
            UserSession userSession = (UserSession) req.getAttribute("userSession");
            if(user != null && userSession != null){
                String sessionId = UserController.generateSessionId(user.getLogin(), user.getPassword());
                userSession.setSessionId(sessionId);
                userService.updateUserSession(userSession);
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
