package service.implementation;

import dao.interfaces.UserDao;
import dao.interfaces.GenericDao;
import model.Employee;
import model.User;
import exception.UserServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import service.interfaces.UserService;

/**
 * User account service implementation.
 */
@Service("userServiceImpl")
public class UserServiceImpl extends GenericServiceImpl<User> implements UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    /**
     * Get user by login.
     *
     * @param login login.
     * @return User.
     */
    @Override
    @Transactional
    public User getUserByLogin(String login) {
        LOG.info(String.format("Loaded user with login: '%s'.", login));
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
                || user.getFirstName() == null
                || user.getFirstName().isEmpty()
                || user.getLastName() == null
                || user.getLastName().isEmpty()
                || user.getEmail() == null
                || user.getEmail().isEmpty()
                || user.getLogin() == null
                || user.getLogin().isEmpty()
                || user.getPassword() == null
                || user.getPassword().isEmpty()){
            LOG.error("Invalid registration data.");
            throw new UserServiceException("Invalid registration data.");
        } else if(getUserByLogin(user.getLogin()) != null
                || getEmployeeByFirstNameAndLastName(user.getFirstName(), user.getLastName()) == null
                || getUserByEmail(user.getEmail().toLowerCase()) != null ){
            LOG.error("Employee exist already.");
            throw new UserServiceException("Employee exist already.");
        } else {
            LOG.info(String.format("Created user: '%s'.", user));
            userDao.create(user);
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
    public User authenticate(User authentication) throws UserServiceException {
        String login = authentication.getLogin();
        String password = authentication.getPassword();

        User user = userDao.getUserByLoginAndPassword(login, password);
        if(user == null) {
            LOG.error("Not valid login or password.");
            throw new UserServiceException("Not valid login or password.");
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
        LOG.info(String.format("Employee: '%s' '%s' loaded.", firstName, lastName));
        return userDao.getEmployeeByFirstNameAndLastName(firstName, lastName);
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
        LOG.info(String.format("Loaded user with email: '%s'.", email));
        return userDao.getUserByEmail(email);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    GenericDao<User> getDao() {
        return userDao;
    }
}
