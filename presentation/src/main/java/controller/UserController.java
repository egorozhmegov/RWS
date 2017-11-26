package controller;

import exception.*;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import service.implementation.UserServiceImpl;
import service.interfaces.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * User controller.
 */
@RestController
public class UserController {

    public static final String COOKIE = "RWS_COOKIE";

    private static final ScheduledExecutorService watchdog = Executors.newScheduledThreadPool(1);

    private static final String CHARACTERS = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
        watchdog.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                //userService.removeExpiredSessionIds();
            }
        }, 0, 5, TimeUnit.MINUTES);
    }

    private final UserService userService;

    /**
     * Login user.
     *
     * @param user User
     * @param response HttpServletResponse
     * @return ResponseEntity<User>
     */
    @RequestMapping(value = "/loginEmployee", method = RequestMethod.POST)
    public ResponseEntity<User> login(@RequestBody User user,
                                      HttpServletRequest request,
                                      HttpServletResponse response) {
        try {
            User authUser = userService.authenticate(user);
            String sessionId = generateSessionId(user.getLogin(), user.getPassword());
            userService.createUserSession(sessionId);
            Cookie cookie = new Cookie(COOKIE, sessionId);
            cookie.setMaxAge(300);
            response.addCookie(cookie);
            request.setAttribute("user", authUser);

            return new ResponseEntity<>(authUser, HttpStatus.OK);
        } catch (UserServiceInvalidDataException e) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Generate session id.
     *
     * @param login String
     * @param password String
     * @return String
     */
    public static String generateSessionId(String login, String password) {

        final Random generator = new Random((login + "~" + password).hashCode());

        final StringBuilder sb = new StringBuilder();

        for (int i = 0; i < CHARACTERS.length(); i++)
            sb.append(CHARACTERS.charAt(generator.nextInt(CHARACTERS.length())));

        return sb.toString();
    }

    /**
     * Logout user.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     */
    @RequestMapping(value = "/logoutEmployee", method = RequestMethod.GET)
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        for (Cookie cookie : cookies) {
            if (Objects.equals(cookie.getName(), COOKIE)) {
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
     * @return ResponseEntity<Boolean>
     */
    @RequestMapping(value = "/registerEmployee", method = RequestMethod.POST)
    public ResponseEntity<User> registerEmployee(@RequestBody User user) {
        try {
            userService.registerUser(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        } catch (UserServiceInvalidDataException ex) {
            return new ResponseEntity<>(user, HttpStatus.BAD_REQUEST);
        } catch (UserServiceLoginException ex) {
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        } catch (UserServiceEmailException ex) {
            return new ResponseEntity<>(user, HttpStatus.FORBIDDEN);
        } catch (UserServiceEmployeeException ex) {
            return new ResponseEntity<>(user, HttpStatus.NO_CONTENT);
        }
    }
}
