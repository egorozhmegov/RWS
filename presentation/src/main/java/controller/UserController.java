package controller;

import exception.ServiceException;
import exception.UserServiceException;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.interfaces.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 *User controller.
 */
@RestController
public class UserController {

    private final static String COOKIE = "RWS_COOKIE";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    private final UserService userService;

    /**
     * Login user.
     *
     * @param user User
     * @param response HttpServletResponse
     * @return ResponseEntity<User>
     */
    @RequestMapping(value="/loginEmployee", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user, HttpServletResponse response) {
        try{
            User authUser = userService.authenticate(user);
            Cookie cookie = new Cookie(COOKIE, "dF6p");
            response.addCookie(cookie);
            return new ResponseEntity<>(authUser, HttpStatus.OK);
        } catch (UserServiceException e){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Logout user.
     *
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value="/logoutEmployee", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for(Cookie cookie: cookies){
            if(Objects.equals(cookie.getName(), COOKIE)){
                cookie.setMaxAge(0);
                response.setStatus(HttpServletResponse.SC_OK);
                response.addCookie(cookie);
            }
        }
    }


    /**
     * Register new employee.
     *
     * @param user User
     * @param response HttpServletResponse
     * @return ResponseEntity<Boolean>
     */
    @RequestMapping(value="/registerEmployee", method = RequestMethod.POST)
    public ResponseEntity<Boolean> registerEmployee(@RequestBody User user, HttpServletResponse response) {
        try{
            userService.registerUser(user);
            return new ResponseEntity<>(true, HttpStatus.OK);
        } catch (ServiceException ex){
            return new ResponseEntity<>(false, HttpStatus.OK);
        }
    }
}
