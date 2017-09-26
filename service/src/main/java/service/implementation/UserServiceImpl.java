package service.implementation;

import dao.interfaces.UserDao;
import dao.interfaces.GenericDao;
import model.User;
import exception.UserExistLoginServiceException;
import exception.UserNullEntityServiceException;
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
            LOG.info(String.format("Invalid user data: %s", user));
            throw new UserNullEntityServiceException("Method: registerUser. Null entity.");
        } else if(getUserByLogin(user.getUserLogin()) != null){
            LOG.info(String.format("Exist login: %s", user.getUserLogin()));
            throw new UserExistLoginServiceException("Method: registerUser. Exist login.");
        } else {
            userDao.create(user);
            LOG.info(String.format("User successfully registered. User detailes: %s", user));
        }
    }

    @Override
    @Transactional
    public User authenticate(User authentication) {
        String login = authentication.getUserLogin();
        String password = authentication.getUserPassword();

        return userDao.getUserByLoginAndPassword(login, password);
    }

    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    GenericDao<User> getDao() {
        return userDao;
    }
}
