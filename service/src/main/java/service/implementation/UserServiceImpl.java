package service.implementation;

import dao.interfaces.GenericDao;
import dao.interfaces.UserDao;
import dao.interfaces.UserSessionDao;
import exception.UserServiceEmailException;
import exception.UserServiceEmployeeException;
import exception.UserServiceInvalidDataException;
import exception.UserServiceLoginException;
import model.Employee;
import model.User;
import model.UserSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.UserRoleService;
import service.interfaces.UserService;
import util.PasswordCrypt;

import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;

/**
 * User account service implementation.
 */
@Service("userServiceImpl")
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final String ROLE_ADMIN = "ADMIN";

    public static final int SESSION_TIME = 5;

    @Autowired
    private UserDao userDao;

    @Autowired
    private UserSessionDao userSessionDao;

    @Autowired
    private UserRoleService userRoleService;

    /**
     * Get user by login.
     *
     * @param login login.
     * @return User.
     */
    @Override
    @Transactional
    public User getUserByLogin(String login) {
        LOG.info("Loaded user with login: '{}'.", login);
        return userDao.getUserByLogin(login);
    }

    /**
     * Register new user.
     *
     * @param user user.
     */
    @Override
    @Transactional
    public void registerUser(User user) {
        if(user == null
                || user.getUserFirstName() == null
                || user.getUserFirstName().isEmpty()
                || user.getUserLastName() == null
                || user.getUserLastName().isEmpty()
                || user.getEmail() == null
                || user.getEmail().isEmpty()
                || user.getLogin() == null
                || user.getLogin().isEmpty()
                || user.getPassword() == null
                || user.getPassword().isEmpty()){
            LOG.error("Invalid registration data.");
            throw new UserServiceInvalidDataException("Invalid registration data.");

        } else if(getUserByLogin(user.getLogin()) != null){
            LOG.error("Employee with login: {} exist already.", user.getLogin());
            throw new UserServiceLoginException(String
                    .format("Employee with login: %s exist already.", user.getLogin()));

        } else if(getUserByEmail(user.getEmail().toLowerCase()) != null){
            LOG.error("Employee with email: {} exist already.", user.getEmail());
            throw new UserServiceEmailException(String
                    .format("Employee with email: %s exist already.", user.getEmail()));

        } else if(getEmployeeByFirstNameAndLastName(user.getUserFirstName(), user.getUserLastName()) == null){
            LOG.error("Employee {} {} does not exist in company.", user.getUserFirstName(), user.getUserLastName());
            throw new UserServiceEmployeeException(String
                    .format("Employee %s %s does not exist in company.", user.getUserFirstName(), user.getUserLastName()));

        } else {
            try {
                user.setPassword(PasswordCrypt.getPassword(user.getPassword()));
            } catch (NoSuchAlgorithmException e) {
                LOG.error("Can not get crypt password of user: {} {}",
                        user.getUserLastName(), user.getUserFirstName());
            }
            user.setEmail(user.getEmail().toLowerCase());
            user.setRole(userRoleService.getUserRoleByRole(ROLE_ADMIN));
            userDao.create(user);
            LOG.info("Created user: '{}.", user);
        }
    }

    /**
     * Check login and password of users.
     *
     * @param authentication user
     * @return User
     */
    @Override
    @Transactional
    public User authenticate(User authentication) {
        String login = authentication.getLogin();
        String password = null;
        try {
            password = PasswordCrypt.getPassword(authentication.getPassword());
        } catch (NoSuchAlgorithmException e) {
            LOG.error("Can not get crypt password of user: {} {}",
                    authentication.getUserLastName(), authentication.getUserFirstName());
        }

        User user = userDao.getUserByLoginAndPassword(login, password);
        if(user == null) {
            LOG.error("Not valid login: {} or password: {}.", login, password);
            throw new UserServiceInvalidDataException("Not valid login or password.");
        }
        return user;
    }

    /**
     * Get employee by Fist Name and Last Name.
     *
     * @param firstName String
     * @param lastName String
     * @return Employee
     */
    @Transactional
    @Override
    public Employee getEmployeeByFirstNameAndLastName(String firstName, String lastName){
        Employee employee = userDao.getEmployeeByFirstNameAndLastName(firstName, lastName);
        LOG.info("Employee: '{}' '{}' loaded.", employee.getEmployeeFirstName(), employee.getEmployeeLastName());
        return employee;
    }

    /**
     * Gets user by email.
     *
     * @param email String.
     * @return User.
     */
    @Transactional
    @Override
    public User getUserByEmail(String email){
        LOG.info("Loaded user with email: '{}'.", email);
        return userDao.getUserByEmail(email);
    }

    /**
     * Create user session.
     *
     * @param sessionId String
     */
    @Transactional
    @Override
    public void createUserSession(String sessionId) {
        LOG.info("Created session with id: {}", sessionId);
        userSessionDao.create(new UserSession(sessionId, LocalDateTime.now().plusMinutes(SESSION_TIME)));
    }

    /**
     * Gets user session.
     *
     * @param sessionId String.
     * @return UserSession.
     */
    @Transactional
    @Override
    public UserSession getUserSession(String sessionId){
        return userSessionDao.getUserSessionById(sessionId);
    }

    /**
     * Update user session.
     *
     * @param userSession UserSession.
     */
    @Transactional
    @Override
    public void updateUserSession(UserSession userSession) {
        userSessionDao.update(userSession);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    GenericDao<User> getDao() {
        return userDao;
    }
}
