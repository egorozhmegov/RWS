package controller;

import exception.ServiceException;
import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import service.interfaces.UserService;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

@RestController
public class UserController {

    private final static Logger LOG = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @RequestMapping(value="/loginEmployee", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user, HttpServletResponse response) {
        User authEmployee = userService.authenticate(user);
        if(authEmployee == null){
            return new ResponseEntity<>(authEmployee, HttpStatus.UNAUTHORIZED);
        } else {
            Cookie cookie = new Cookie("RWS_COOKIE", "1111");
            response.addCookie(cookie);
            return new ResponseEntity<>(authEmployee, HttpStatus.OK);
        }
    }

    @RequestMapping(value="/logoutEmployee", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for(Cookie cookie: cookies){
                if(Objects.equals(cookie.getName(), "RWS_COOKIE")){
                    cookie.setMaxAge(0);
                    response.setStatus(HttpServletResponse.SC_OK);
                    response.addCookie(cookie);
                }
            }
        }
    }


    @RequestMapping(value="/registerEmployee", method = RequestMethod.POST)
    public boolean registerEmployee(@RequestBody User user, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_OK);
        try{
            userService.registerUser(user);
            return true;
        } catch (ServiceException ex){
            return false;
        }
    }
}
