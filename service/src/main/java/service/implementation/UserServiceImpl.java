package service.implementation;

import dao.interfaces.UserDao;
import dao.interfaces.GenericDao;
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
public class UserServiceImpl
        extends GenericServiceImpl<User>
            implements UserService {

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
                || user.getUserEmail() == null
                || user.getUserEmail().isEmpty()
                || user.getUserLogin() == null
                || user.getUserLogin().isEmpty()
                || user.getUserPassword() == null
                || user.getUserPassword().isEmpty()){
            throw new UserServiceException("Null entity.");
        } else if(getUserByLogin(user.getUserLogin()) != null){
            throw new UserServiceException("Login exist already");
        } else {
            userDao.create(user);
        }
    }

    /**
     * Check login and password of users.
     *
     * @param authentication
     * @return User
     */
    @Override
    @Transactional
    public User authenticate(User authentication) throws UserServiceException {
        String login = authentication.getUserLogin();
        String password = authentication.getUserPassword();
        User user = userDao.getUserByLoginAndPassword(login, password);
        if(user == null) {
            LOG.info("Not valid login or password.");
            throw new UserServiceException("Not valid login or password.");
        }
        return user;
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    GenericDao<User> getDao() {
        return userDao;
    }
}
